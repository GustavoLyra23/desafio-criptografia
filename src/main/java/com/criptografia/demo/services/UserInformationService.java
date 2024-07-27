package com.criptografia.demo.services;

import com.criptografia.demo.dto.UserInformationDto;
import com.criptografia.demo.entities.UserInformation;
import com.criptografia.demo.repositories.UserInformationRepository;
import com.criptografia.demo.services.exception.DatabaseException;
import com.criptografia.demo.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (!userInformationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Entity not found");
        }
        try {
            userInformationRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity error");
        }
    }

    @Transactional()
    public UserInformationDto update(Long id, UserInformationDto userInformationDto) {
        try {
            UserInformation userInformation = userInformationRepository.getReferenceById(id);
            dtoToEntity(userInformationDto, userInformation);
            userInformation.setUserDocument(securityService.encrypt(userInformationDto.getUserDocument()));
            userInformation.setCreditCardToken(securityService.encrypt(userInformation.getCreditCardToken()));
            userInformation = userInformationRepository.save(userInformation);
            return new UserInformationDto(userInformation);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Entity not found");
        }
    }

    @Transactional(readOnly = true)
    public Page<UserInformationDto> findAll(Pageable pageable) {
        Page<UserInformation> userInformations = userInformationRepository.findAll(pageable);
        return userInformations.map(x -> {
            x.setUserDocument(securityService.decrypt(x.getUserDocument()));
            x.setCreditCardToken(securityService.decrypt(x.getCreditCardToken()));
            return new UserInformationDto(x);
        });

    }


    private void dtoToEntity(UserInformationDto userInformationDto, UserInformation userInformation) {
        userInformation.setUserDocument(userInformationDto.getUserDocument());
        userInformation.setCreditCardToken(userInformationDto.getCreditCardToken());
        userInformation.setUserValue(userInformationDto.getValue());
    }

}
