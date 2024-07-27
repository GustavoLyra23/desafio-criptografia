package com.criptografia.demo.controller;

import com.criptografia.demo.dto.UserInformationDto;
import com.criptografia.demo.services.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/user-information")
public class UserInformationController {

    @Autowired
    private UserInformationService userInformationService;


    @PostMapping
    public ResponseEntity<UserInformationDto> saveUserInformation(@RequestBody UserInformationDto userInformation) {
        UserInformationDto dto = userInformationService.insert(userInformation);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<UserInformationDto> getUserInformation(@PathVariable Long id) {
        UserInformationDto userInformationDto = userInformationService.findById(id);
        return ResponseEntity.ok(userInformationDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUserInformation(@PathVariable Long id) {
        userInformationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
