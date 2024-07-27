package com.criptografia.demo.dto;

import com.criptografia.demo.entities.UserInformation;

import java.io.Serializable;

public class UserInformationDto implements Serializable {

    private Long id;
    private String userDocument;
    private String creditCardToken;
    private Long value;

    public UserInformationDto() {
    }

    public UserInformationDto(UserInformation entity) {
        id = entity.getId();
        userDocument = entity.getUserDocument();
        creditCardToken = entity.getCreditCardToken();
        value = entity.getUserValue();
    }

    public Long getId() {
        return id;
    }

    public String getUserDocument() {
        return userDocument;
    }

    public String getCreditCardToken() {
        return creditCardToken;
    }

    public Long getValue() {
        return value;
    }
}
