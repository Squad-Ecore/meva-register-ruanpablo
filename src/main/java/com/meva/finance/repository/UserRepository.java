package com.meva.finance.repository;

import com.meva.finance.model.UserMeva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserMeva, String> {
    Optional<UserMeva> findById(String cpf);

}
