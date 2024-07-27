package com.criptografia.demo.dto;

import com.criptografia.demo.entities.UserInformation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class UserInformationDto implements Serializable {

    private Long id;

    @Size(min = 2, max = 255)
    @NotBlank
    private String userDocument;

    @Size(min = 2, max = 255)
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
