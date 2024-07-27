package com.criptografia.demo.dto;

import com.criptografia.demo.entities.UserInformation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public class UserInformationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;


    @NotBlank
    private String userDocument;

    @NotBlank
    private String creditCardToken;

    @NotNull
    @Positive
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
