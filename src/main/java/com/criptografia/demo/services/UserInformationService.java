package com.criptografia.demo.services;

import com.criptografia.demo.dto.UserInformationDto;
import com.criptografia.demo.entities.UserInformation;
import com.criptografia.demo.repositories.UserInformationRepository;
import com.criptografia.demo.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInformationService {

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private SecurityService securityService;

    @Transactional
    public UserInformationDto insert(UserInformationDto userInformationDto) {
        UserInformation userInformation = new UserInformation();
        dtoToEntity(userInformationDto, userInformation);

        userInformation.setUserDocument(securityService.encrypt(userInformationDto.getUserDocument()));
        userInformation.setCreditCardToken(securityService.encrypt(userInformationDto.getCreditCardToken()));

        userInformation = userInformationRepository.save(userInformation);
        return new UserInformationDto(userInformation);
    }

    @Transactional(readOnly = true)
    public UserInformationDto findById(Long id) {
        UserInformation userInformation = userInformationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        userInformation.setUserDocument(securityService.decrypt(userInformation.getUserDocument()));
        userInformation.setCreditCardToken(securityService.decrypt(userInformation.getCreditCardToken()));

        return new UserInformationDto(userInformation);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        try {
            userInformationRepository.getReferenceById(id);
            userInformationRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found");
        }

    }


    private void dtoToEntity(UserInformationDto userInformationDto, UserInformation userInformation) {
        userInformation.setId(userInformationDto.getId());
        userInformation.setUserDocument(userInformationDto.getUserDocument());
        userInformation.setCreditCardToken(userInformationDto.getCreditCardToken());
        userInformation.setUserValue(userInformationDto.getValue());
    }


}
