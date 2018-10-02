package com.spring.starter.configuration;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.starter.jwt.JwtTokenAuthenticationFilter;

@EnableWebSecurity
@Configuration
@ComponentScan("com.spring.starter")
@Order(3)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationConfig config;

    private String prfix = "api/ndb";

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }

    @Autowired
    DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        return jdbcUserDetailsManager;
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().disable()
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(config),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(config.getUrl()).permitAll()
                .antMatchers("/staffUsers/addNewUser").hasRole("ADMIN")
                .antMatchers("/staffUsers/addStaffUserFirstTime").permitAll()
                .antMatchers("/staffUsers/login/**").permitAll()
                .antMatchers("/staffRoles").permitAll()
                .antMatchers("/CustomerServiceRequest/**").permitAll()
                .antMatchers("/serviceRequest/atmOrDebit/request").permitAll()
                .antMatchers("/serviceRequest/atmOrDebit/reIssuePin").permitAll()
                .antMatchers("/serviceRequest/atmOrDebit/smsSubscription").permitAll()
                .antMatchers("/serviceRequest/atmOrDebit/LinkedAccount").permitAll()
                .antMatchers("/serviceRequest/atmOrDebit/changePrimaryAccount").permitAll()
                .antMatchers("/bankStatementPassBook/duplicatePassbook").permitAll()
                .antMatchers("/bankStatementPassBook/AccountStatement").permitAll()
                .antMatchers("/bankStatementPassBook/e-statement").permitAll()
                .antMatchers("/bankStatementPassBook/statementFrequency").permitAll()
                .antMatchers("/branch/addNewBranch").permitAll()
                .antMatchers("/branch/updateBranch/*").permitAll()
                .antMatchers("/serviceRequest/changePermanentAddress").permitAll()
                .antMatchers("/serviceRequest/CustomerServiceRequest").permitAll()
                .antMatchers("/serviceRequest/addRelatedRequest").permitAll()
                .antMatchers("/serviceRequest/addDuplicateFdCdCert").permitAll()
                .antMatchers("/serviceRequest/internet-banking/reissue-password").permitAll()
                .antMatchers("/serviceRequest/link-JointAccounts").permitAll()
                .antMatchers("/serviceRequest/exclude-accounts").permitAll()
                .antMatchers("/serviceRequest/other-service").permitAll()
                .antMatchers("/serviceRequest/address/change-permenenet/").permitAll()
                .antMatchers("/serviceRequest/otherRequest").permitAll()
                .antMatchers("/serviceRequest/addNewBankService").permitAll()
                .antMatchers("/serviceRequest/getBankServices").permitAll()
                .antMatchers("/serviceRequest/addNewCustomer").permitAll()
                .antMatchers("/serviceRequest/addNewServiceToACustomer").permitAll()
                .antMatchers("/serviceRequest/getcustomer").permitAll()
                .antMatchers("/serviceRequest/getServicesOfACustomerByDate").permitAll()
                .antMatchers("/serviceRequestgetAllCustomerRequests").permitAll()
                .antMatchers("/serviceRequestgetAllCustomerDataWithRequests").permitAll()
                .antMatchers("/serviceRequestcompleteACustomerRequest").permitAll()
                .antMatchers("/serviceRequestaddAStaffHandled").permitAll()
                .antMatchers("/serviceRequest/getServiceRequestForm").permitAll()
                .antMatchers("/serviceRequest/addSignature").permitAll()
                .antMatchers("/serviceRequest/SMSAlertsforCreditCard").permitAll()
                .anyRequest().authenticated();


    }
}
