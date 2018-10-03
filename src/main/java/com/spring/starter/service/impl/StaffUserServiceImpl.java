package com.spring.starter.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.starter.DTO.AuthToken;
import com.spring.starter.DTO.LoginDTO;
import com.spring.starter.DTO.StaffUserDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.DeactivateLogRepository;
import com.spring.starter.Repository.JwtTokenLogRepository;
import com.spring.starter.Repository.JwtTokensRepository;
import com.spring.starter.Repository.LoginlogsRepository;
import com.spring.starter.Repository.SetActiveLogRepository;
import com.spring.starter.Repository.StaffRoleRepository;
import com.spring.starter.Repository.StaffUserRepository;
import com.spring.starter.Repository.StaffUserSaveLogRepository;
import com.spring.starter.Repository.ViewUsersLogRepository;
import com.spring.starter.configuration.ApiParameters;
import com.spring.starter.configuration.JwtAuthenticationConfig;
import com.spring.starter.jwt.JwtGenerator;
import com.spring.starter.model.DeactivateLog;
import com.spring.starter.model.JwtTokenLog;
import com.spring.starter.model.JwtTokens;
import com.spring.starter.model.Loginlogs;
import com.spring.starter.model.SetActiveLog;
import com.spring.starter.model.StaffRole;
import com.spring.starter.model.StaffUser;
import com.spring.starter.model.StaffUserSaveLog;
import com.spring.starter.model.ViewUsersLog;
import com.spring.starter.service.StaffUserService;

@Service
@Transactional
public class StaffUserServiceImpl implements StaffUserService {

	@Autowired
	private StaffUserRepository staffUserRepository;
	
	@Autowired
	private StaffRoleRepository staffRoleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private LoginlogsRepository loginlogsRepository;
	
	@Autowired
	private JwtTokensRepository jwtTokensRepository;
	
	@Autowired
	private SetActiveLogRepository activeLogRepository;
	
	@Autowired
	private DeactivateLogRepository deactivateLogRepository;
	
    @Autowired
    private JwtAuthenticationConfig config;
    
    @Autowired
    private JwtTokenLogRepository jwtTokenLogRepository;
    
    @Autowired
    private StaffUserSaveLogRepository staffUserSaveLogRepository;
	
    @Autowired
    private ViewUsersLogRepository viewUsersLogRepository; 
    
	public ResponseEntity<?> getAllStaffUsers()
	{
		try {
			List<StaffUser> staffUsers =  staffUserRepository.findAll(); 
			return new ResponseEntity<>(staffUsers,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("{\"Error\":\"Connection failed\",\"Status\":"+false+"}",HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@Override
	public ResponseEntity<?> makeUserDeactivate(int staffId, Principal principal, HttpServletRequest request) {
		if(!checkTokenValidity(principal, request)) 
		{
			return new ResponseEntity<>("{\"Error\":\"Detected As Invalied Login\",\"Status\":"+false+"}",HttpStatus.UNAUTHORIZED);
		}
		DeactivateLog deactivateLog = new DeactivateLog();
		Optional<StaffUser> staffUser = staffUserRepository.findById(staffId);
		Optional<StaffUser> staffAdmin = staffUserRepository.findById(Integer.parseInt(principal.getName()));
		deactivateLog.setDeactivateBy(staffAdmin.get());
		deactivateLog.setDeactivateDate(java.util.Calendar.getInstance().getTime());
		deactivateLog.setDeactivateIp(request.getRemoteAddr());
		if(!staffUser.isPresent()) 
		{
			deactivateLog.setMessage("Tried To Deactivate A Invalied User");
			deactivateLogRepository.save(deactivateLog);
			return new ResponseEntity<>("{\"Error\":\"Invalied User Details\",\"Status\":"+false+"}",HttpStatus.BAD_REQUEST);
		} else if(staffUser.get().getActive() == 0) {
			deactivateLog.setMessage("Tried To Deactivate An Already Deactivate User");
			deactivateLogRepository.save(deactivateLog);
			return new ResponseEntity<>("{\"Error\":\"User IS Not Active Already\",\"Status\":"+false+"}",HttpStatus.BAD_REQUEST);
		} else if(staffUser.get().getStaffId() == staffAdmin.get().getStaffId()) {
			deactivateLog.setMessage("Tried To Deactivate Him/Her Self");
			deactivateLogRepository.save(deactivateLog);
			return new ResponseEntity<>("{\"Error\":\"You Cannot Decativate By Your Self\",\"Status\":"+false+"}",HttpStatus.BAD_REQUEST);
		} else {
			StaffUser user = staffUser.get();
			user.setActive(0);
			deactivateLog.setMessage("User Deactivation Successfull");
			deactivateLog.setDeactivateWho(staffUser.get());
			try {
				staffUserRepository.save(user);
				deactivateLogRepository.save(deactivateLog);
				return new ResponseEntity<>("{\"Message\":\"User Deactivated Successfully\",\"Status\":"+true+"}",HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>("{\"Message\":\"Some Thing went Wrong...\",\"Status\":"+false+"}",HttpStatus.SERVICE_UNAVAILABLE);
			}
		}
	}
	
	public ResponseEntity<?> makeUserActive(int staffId,Principal principal,HttpServletRequest request)
	{
		if(!checkTokenValidity(principal, request)) 
		{
			return new ResponseEntity<>("{\"Error\":\"Detected As Invalied Login\",\"Status\":"+false+"}",HttpStatus.UNAUTHORIZED);
		}
		SetActiveLog activeLog = new SetActiveLog();
		Optional<StaffUser> staffUser = staffUserRepository.findById(staffId);
		Optional<StaffUser> staffAdmin = staffUserRepository.findById(Integer.parseInt(principal.getName()));
		activeLog.setActiveBy(staffAdmin.get());
		activeLog.setActiveDate(java.util.Calendar.getInstance().getTime());
		activeLog.setActiveIp(request.getRemoteAddr());
		if(!staffUser.isPresent()) {
			activeLog.setMessage("Tried To Activate A Invalied User");
			activeLogRepository.save(activeLog);
			return new ResponseEntity<>("{\"Error\":\"Invalied User Details\",\"Status\":"+false+"}",HttpStatus.BAD_REQUEST);
		}else if(staffUser.get().getActive()==1) {
			activeLog.setMessage("Tried To Activate AN Already Activated User");
			activeLogRepository.save(activeLog);
			return new ResponseEntity<>("{\"Error\":\"User Is Already Active\",\"Status\":"+false+"}",HttpStatus.BAD_REQUEST);
		} else if(staffUser.get().getStaffId() == staffAdmin.get().getStaffId()){
			activeLog.setMessage("Tried To Activate Him Self");
			activeLogRepository.save(activeLog);
			return new ResponseEntity<>("{\"Error\":\"You Cannot Activate Your Self\",\"Status\":"+false+"}",HttpStatus.BAD_REQUEST);
		} else {
			StaffUser user = staffUser.get();
			user.setActive(1);
			activeLog.setMessage("User Activated Successfully");
			activeLog.setActiveWho(user);
			try {
			activeLogRepository.save(activeLog);
			staffUserRepository.save(user);
			} catch (Exception e) {
				return new ResponseEntity<>("{\"Error\":\"Something Went Wrong...\",\"Status\":"+false+"}",HttpStatus.SERVICE_UNAVAILABLE);
			}
			return new ResponseEntity<>("{\"Message\":\"User Activated Successfully\",\"Status\":"+true+"}",HttpStatus.OK);
		}
	}
	
	public ResponseEntity<?> saveStaffUser(StaffUserDTO staffUserDTO,HttpServletRequest request,Principal principal)
	{
		StaffUserSaveLog saveLog = new StaffUserSaveLog();
		if(staffUserRepository.getUserByUsernameForSignUp(staffUserDTO.getUsername()).isPresent()) 
		{
			saveLog.setMessage("User Tried To Insert An Already Exists Username");
			saveUserSaveLog(saveLog, request, principal);
			return new ResponseEntity<>("{\"Error\":\"Username Already Exists\",\"Status\":"+false+"}",HttpStatus.BAD_REQUEST);
		}
		if(staffUserRepository.getUserByEmail(staffUserDTO.getEmail()).isPresent()) 
		{
			saveLog.setMessage("User Tried To Insert An Already Exists Email");
			saveUserSaveLog(saveLog, request, principal);
			return new ResponseEntity<>("{\"Error\":\"Email Already Exists\",\"Status\":"+false+"}",HttpStatus.BAD_REQUEST); 
		}
		Optional<StaffRole> staffRole = staffRoleRepository.findById(staffUserDTO.getStaffRole());
		if(!staffRole.isPresent()) 
		{
			saveLog.setMessage("User Entered Invalied Staff Role details");
			saveUserSaveLog(saveLog, request, principal);
			return new ResponseEntity<>("{\"Error\":\"Invalied Staff Role Details\",\"Status\":"+false+"}",HttpStatus.BAD_REQUEST);
		}
		
		String encryptedPassword = bCryptPasswordEncoder.encode(staffUserDTO.getPassword());
		
		StaffUser staffUser = new StaffUser();
		staffUser.setName(staffUserDTO.getName());
		staffUser.setEmail(staffUserDTO.getEmail());
		staffUser.setUsername(staffUserDTO.getUsername());
		staffUser.setPassword(encryptedPassword);
		staffUser.setStaffRole(staffRole.get());
		try {
		staffUserRepository.save(staffUser);
		saveLog.setMessage("User added Successfully");
		saveLog.setSavedWho(staffUser);
		saveUserSaveLog(saveLog, request, principal);
		return new ResponseEntity<>("{\"message\":\"User Added Successfully\",\"Status\":"+true+"}",HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("{\"Error\":\"User Adition Failed\",\"Status\":"+false+"}",HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<?> staffUserLogin(LoginDTO loginDTO,HttpServletRequest request) {
		Loginlogs loginlogs = new Loginlogs();
		Optional<StaffUser> staffUserOpt = staffUserRepository.getUserByUsername(loginDTO.getUsername());
		if(!staffUserOpt.isPresent()) 
		{
			String status = "Invalied Login Credentials OR User Is Not Active";
			loginlogs.setLoginIp(request.getRemoteAddr());
			loginlogs.setStatus(status);
			loginlogs.setLoginTime(java.util.Calendar.getInstance().getTime());
			loginlogs.setLogUsername(loginDTO.getUsername());
			printLogs(loginlogs);
			System.out.println(status);
			return new ResponseEntity<>("{\"Error\":"+status+",\"Status\":"+false+"}", HttpStatus.UNAUTHORIZED);
		}
		if(bCryptPasswordEncoder.matches(loginDTO.getPassword(), staffUserOpt.get().getPassword())) {
		String accessToken = createJWtWithOutPrefix(staffUserOpt.get());
		AuthToken authToken = new AuthToken();
		

		Optional<JwtTokens> jwtTokensOpt =  jwtTokensRepository.getLoggedInTokens(staffUserOpt.get().getStaffId()); 
		JwtTokens jwtTokens;
		
		if(jwtTokensOpt.isPresent()) {
			jwtTokens = jwtTokensOpt.get();
			jwtTokens = updateToken(accessToken, jwtTokens);
		} else {
			jwtTokens = setToken(accessToken,staffUserOpt.get());
		} if(jwtTokens == null) {
			return new ResponseEntity<>("{\"Error\":\"Some Thing Went Wrong\",\"Status\":"+false+"}", HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		String status = "User Logged in successfully";
		loginlogs.setLoginIp(request.getRemoteAddr());
		loginlogs.setStatus(status);
		loginlogs.setLoginTime(java.util.Calendar.getInstance().getTime());
		loginlogs.setLogUsername(loginDTO.getUsername());
		loginlogs.setJwtTokens(jwtTokens);
		Loginlogs loginlogs2 = printLogs(loginlogs);
		jwtTokens.setLoginlogs(loginlogs2);
		jwtTokensRepository.save(jwtTokens);
		authToken.setAccessToken(accessToken);
		return new ResponseEntity<>(authToken,HttpStatus.OK);
		}else 
		{
			String status = "Invalied Password Entered";
			loginlogs.setLoginIp(request.getRemoteAddr());
			loginlogs.setStatus(status);
			loginlogs.setLoginTime(java.util.Calendar.getInstance().getTime());
			loginlogs.setLogUsername(loginDTO.getUsername());
			printLogs(loginlogs);
			return new ResponseEntity<>("{\"Error\":"+status+",\"Status\":"+false+"}", HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Override
	public ResponseEntity<?> getAllActiveUsers(Principal principal, HttpServletRequest request) {
		List<StaffUser> activeUsers = staffUserRepository.getAllActiveUsers();
		if(activeUsers.isEmpty()) 
		{
			printViewLog(request, principal, "View Active Users - But No Content Was In The DataBase");
			return new ResponseEntity<>("{\"Error\":\"No Data Available\",\"Status\":"+false+"}", HttpStatus.NO_CONTENT);
		} else {
			printViewLog(request, principal, "View Active Users");
			return new ResponseEntity<>(activeUsers, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<?> getAllUsers(Principal principal, HttpServletRequest request) {
		List<StaffUser> activeUsers = staffUserRepository.findAll();
		if(activeUsers.isEmpty()) 
		{
			printViewLog(request, principal, "View All Users - But No Content Was In The DataBase");
			return new ResponseEntity<>("{\"Error\":\"No Data Available\",\"Status\":"+false+"}", HttpStatus.NO_CONTENT);
		} else {
			printViewLog(request, principal, "View All Users");
			return new ResponseEntity<>(activeUsers, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<?> getAllDeactivateUsers(Principal principal, HttpServletRequest request) {
		List<StaffUser> activeUsers = staffUserRepository.getAllDeactivatedUsers();
		if(activeUsers.isEmpty()) 
		{
			printViewLog(request, principal, "View Deactivate Users - But No Content Was In The DataBase");
			return new ResponseEntity<>("{\"Error\":\"No Data Available\",\"Status\":"+false+"}", HttpStatus.NO_CONTENT);
		} else {
			printViewLog(request, principal, "View Deactivate Users");
			return new ResponseEntity<>(activeUsers, HttpStatus.OK);
		}
	}
	
	public ResponseEntity<?> saveStaffUserFirstTime(StaffUserDTO staffUserDTO)
	{
		Optional<StaffRole> staffRole = staffRoleRepository.findById(staffUserDTO.getStaffRole());
		System.out.println(staffUserDTO.getStaffRole());
		if(!staffRole.isPresent()) 
		{
			return new ResponseEntity<>("{\"Error\":\"Invalied Staff Role Details\",\"Status\":"+false+"}",HttpStatus.BAD_REQUEST);
		}
		
		String encryptedPassword = bCryptPasswordEncoder.encode(staffUserDTO.getPassword());
		
		StaffUser staffUser = new StaffUser();
		staffUser.setName(staffUserDTO.getName());
		staffUser.setEmail(staffUserDTO.getEmail());
		staffUser.setUsername(staffUserDTO.getUsername());
		staffUser.setPassword(encryptedPassword);
		staffUser.setStaffRole(staffRole.get());
		staffUser.setActive(1);
		try {
		staffUserRepository.save(staffUser);
		return new ResponseEntity<>("{\"message\":\"User Added Successfully\",\"Status\":"+true+"}",HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("{\"Error\":\"User Adition Failed\",\"Status\":"+false+"}",HttpStatus.CREATED);
		}
	}
	
	
	private String createJWtWithOutPrefix(StaffUser staffUser) 
	{
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
		StaffRole staffRole = staffUser.getStaffRole();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+staffRole.getRoleType()));
		String accessToken = JwtGenerator.genarateAccessJWT(Integer.toString(staffUser.getStaffId()), staffUser.getUsername(), grantedAuthorities, ApiParameters.JWT_EXPIRATION, ApiParameters.JWT_SECRET);
		return accessToken;
	}
	
	private Loginlogs printLogs(Loginlogs loginlogs) 
	{
		return loginlogsRepository.save(loginlogs);
	}
	
	private JwtTokens setToken(String token, StaffUser staffUser) 
	{
		JwtTokens jwtTokens = new JwtTokens();
		jwtTokens.setIssuedTime(java.util.Calendar.getInstance().getTime());
		jwtTokens.setIsValied(1);
		jwtTokens.setToken(token);
		jwtTokens.setStaffUser(staffUser);
		return jwtTokensRepository.save(jwtTokens);
	}
	
	private JwtTokens updateToken(String token, JwtTokens jwtTokens) 
	{
		jwtTokens.setIssuedTime(java.util.Calendar.getInstance().getTime());
		jwtTokens.setIsValied(1);
		jwtTokens.setToken(token);
		return jwtTokensRepository.save(jwtTokens);
	}
	
	private boolean checkTokenValidity(Principal principal, HttpServletRequest request) 
	{
		String token = request.getHeader(config.getHeader());
		token = token.replace(config.getPrefix() + " ", "");
		Optional<JwtTokens> optionalToken = jwtTokensRepository.getValiedTokens(token);
		if(!optionalToken.isPresent()) 
		{
			JwtTokenLog jwtTokenLog = new JwtTokenLog();
			jwtTokenLog.setToken(token);
			jwtTokenLog.setUserId(Integer.parseInt(principal.getName()));
			jwtTokenLog.setEnteredDate(java.util.Calendar.getInstance().getTime());
			jwtTokenLog.setIp(request.getRemoteAddr());
			jwtTokenLogRepository.save(jwtTokenLog);
			return false;
		} else 
		{
			return true;
		}
	}
	
	private void saveUserSaveLog(StaffUserSaveLog saveLog,HttpServletRequest request,Principal principal) 
	{
		Optional<StaffUser> staffUserOpt = staffUserRepository.findById(Integer.parseInt(principal.getName()));
		try {
		staffUserOpt = staffUserRepository.findById(Integer.parseInt(principal.getName()));
		} catch (Exception e) {
			throw new CustomException("Some Thing Went Wrong");
		}
		saveLog.setDate(java.util.Calendar.getInstance().getTime());
		saveLog.setSavedIp(request.getRemoteAddr());
		saveLog.setSavedBy(staffUserOpt.get());
		try {
		staffUserSaveLogRepository.save(saveLog);
		} catch (Exception e) {
			throw new CustomException("Some Thing Went Wrong");
		}
	}
	
	private void printViewLog(HttpServletRequest request,Principal principal,String type) {
		
		ViewUsersLog usersLog = new ViewUsersLog();
		usersLog.setViewedIp(request.getRemoteAddr());
		usersLog.setViwedTime(java.util.Calendar.getInstance().getTime());
		usersLog.setViewedItem(type);
		try {
		viewUsersLogRepository.save(usersLog);
		} catch (Exception e) {
			throw new CustomException("Some Thing Went Wrong");
		}
	}
	
	public StaffUser getUser() 
	{
		return staffUserRepository.getUserByUsernameForSignUp("hansiyapa").get();
	}


}
