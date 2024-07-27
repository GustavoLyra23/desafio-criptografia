package com.criptografia.demo.services;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private BasicTextEncryptor basicTextEncryptor;

    public String encrypt(String password) {
        return basicTextEncryptor.encrypt(password);
    }

    public String decrypt(String password) {
        return basicTextEncryptor.decrypt(password);
    }


}
