package com.criptografia.demo.repositories;

import com.criptografia.demo.entities.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformationRepository extends JpaRepository<UserInformation, Long> {
}
