package com.meva.finance.service;

import com.meva.finance.dto.UserMevaDto;
import com.meva.finance.model.Family;
import com.meva.finance.model.UserMeva;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import com.meva.finance.validation.ValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private FamilyRepository familyRepository;

    @Autowired
    public UserService(UserRepository userRepository, FamilyRepository familyRepository) {
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    public UserMeva register(UserMevaDto userDto) throws ValidException {
        if (userRepository.findById(userDto.getCpf()).isPresent()) {
            throw new ValidException("Cpf já cadastrado no sistema.");
        }
        UserMeva user = userDto.converter();
        user.setFamily(validExceptionFamily(userDto));
        return userRepository.save(user);
    }

    public ResponseEntity<UserMevaDto> update(UserMevaDto userDto, String cpf) {
        Optional<UserMeva> optional = userRepository.findById(cpf);
        if (optional.isPresent()) {
            userRepository.save(optional.get());
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

    public Family validExceptionFamily(UserMevaDto userDto) throws ValidException {
        Long idFamily = userDto.getFamilyDto().getId();
        if (idFamily == null) {
            throw new ValidException("O id da família é nulo.");
        } else if (familyRepository.findById(idFamily).isPresent() || idFamily == 0) {
            return familyRepository.save(userDto.getFamilyDto().converter());
        }
        throw new ValidException("O id da família não foi encontrado.");
    }

    public List<UserMeva> list() {
        return userRepository.findAll();
    }

    public ResponseEntity<UserMeva> searchForCpf(String cpf) {
        return ResponseEntity.ok(userRepository.findCpf(cpf));
    }

    public ResponseEntity delete(String cpf) {
        if (userRepository.findById(cpf).isPresent()) {
            userRepository.deleteById(cpf);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
