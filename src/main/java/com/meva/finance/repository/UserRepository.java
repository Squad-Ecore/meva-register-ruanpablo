package com.meva.finance.repository;

import com.meva.finance.model.UserMeva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserMeva, String> {
    Optional<UserMeva> findById(String cpf);

    @Query("SELECT u FROM UserMeva u WHERE u.cpf = :cpf")
    UserMeva findCpf(String  cpf);
}
