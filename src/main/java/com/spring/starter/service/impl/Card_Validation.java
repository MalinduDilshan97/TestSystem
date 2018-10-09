package com.spring.starter.service.impl;

import com.spring.starter.enums.CardType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class Card_Validation {

    public boolean checkCardValidity(String card){
        CardType cardType;

        cardType= CardType.VISA;
        boolean visa = cardType.isValid(card);
        cardType= CardType.MASTERCARD;
        boolean master = cardType.isValid(card);
        cardType= CardType.AMERICAN_EXPRESS;
        boolean amarican = cardType.isValid(card);
        cardType = CardType.DINERS_CLUB;
        boolean diners = cardType.isValid(card);
        cardType = CardType.DISCOVER;
        boolean discover = cardType.isValid(card);
        cardType = CardType.JCB;
        boolean jcb = cardType.isValid(card);
        cardType = CardType.CHINA_UNION_PAY;
        boolean china = cardType.isValid(card);

        if(!visa && !master && !amarican && !diners && !discover && !jcb && !china){
            return false;
        } else if(visa && master && amarican && diners && discover && jcb && china){
            return false;
        } else {
            return true;
        }
    }
}
