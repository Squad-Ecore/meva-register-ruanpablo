package com.meva.finance.service;

import com.meva.finance.dto.UserMevaDto;
import com.meva.finance.model.Family;
import com.meva.finance.model.UserMeva;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import com.meva.finance.validation.ValidExceptionFamily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FamilyRepository familyRepository;

    public ResponseEntity<String> register(UserMevaDto userDto) throws ValidExceptionFamily {
        if (userRepository.findById(userDto.getCpf()).isPresent()) {
            throw new ValidExceptionFamily("Cpf já cadastrado no sistema.");
        }
        UserMeva userMeva = userDto.converter();
        userMeva.setFamily(validExceptionFamily(userDto));
        userRepository.save(userMeva);
        return ResponseEntity.ok(userDto.getCpf());
    }

    public ResponseEntity<UserMevaDto> update(UserMevaDto userDto, String cpf) {
        Optional<UserMeva> optional = userRepository.findById(cpf);
        if (optional.isPresent()) {
            userRepository.save(optional.get());
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

    public Family validExceptionFamily(UserMevaDto userDto) throws ValidExceptionFamily {
        Long idFamily = userDto.getFamilyDto().getId();
        if (idFamily == 0) {
            return familyRepository.save(userDto.getFamilyDto().converter());
        } else if ((Objects.isNull(idFamily)) || !(familyRepository.findById(idFamily).isPresent())){
            throw new ValidExceptionFamily("O id da família não foi encontrado.");
        }
        return familyRepository.save(userDto.getFamilyDto().converter());

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
