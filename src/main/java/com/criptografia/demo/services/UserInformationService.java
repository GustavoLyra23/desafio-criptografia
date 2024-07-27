package com.criptografia.demo.services;

import com.criptografia.demo.dto.UserInformationDto;
import com.criptografia.demo.entities.UserInformation;
import com.criptografia.demo.repositories.UserInformationRepository;
import com.criptografia.demo.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInformationService {

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public UserInformationDto insert(UserInformationDto userInformationDto) {
        UserInformation userInformation = new UserInformation();
        dtoToEntity(userInformationDto, userInformation);

        userInformation.setUserDocument(bCryptPasswordEncoder.encode(userInformationDto.getUserDocument()));
        userInformation.setCreditCardToken(bCryptPasswordEncoder.encode(userInformationDto.getCreditCardToken()));

        userInformation = userInformationRepository.save(userInformation);
        return new UserInformationDto(userInformation);
    }

    @Transactional(readOnly = true)
    public UserInformationDto findById(Long id) {
        UserInformation userInformation = userInformationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return new UserInformationDto(userInformation);
    }


    private void dtoToEntity(UserInformationDto userInformationDto, UserInformation userInformation) {
        userInformation.setId(userInformationDto.getId());
        userInformation.setUserDocument(userInformationDto.getUserDocument());
        userInformation.setCreditCardToken(userInformationDto.getCreditCardToken());
        userInformation.setUserValue(userInformationDto.getValue());
    }


}
