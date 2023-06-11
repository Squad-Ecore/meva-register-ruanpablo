package com.meva.finance.service;

import com.meva.finance.dto.UserMevaDto;
import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import com.meva.finance.validation.ValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<User> list() {
        return userRepository.findAll();
    }

    public User findCpf(String cpf) throws ValidException {
        Optional<User> optional = userRepository.findById(cpf);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ValidException("Usuário não encontrado.");
    }

    public User register(UserMevaDto userDto) throws ValidException {
        if (userRepository.findById(userDto.getCpf()).isPresent()) {
            throw new ValidException("Cpf já cadastrado no sistema.");
        }
        User user = userDto.converter();
        user.setFamily(validExceptionFamily(userDto));
        return userRepository.save(user);
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

    public UserMevaDto update(UserMevaDto userDto, String cpf) throws ValidException {
        Optional<User> optional = userRepository.findById(cpf);
        if (optional.isPresent()) {
            userRepository.save(userDto.converter());
            return userDto;
        }
        throw new ValidException("Cpf não encontrado no nosso sistema!");
    }

    public ResponseEntity<String> delete(String cpf) {
        Optional<User> optionalUser = userRepository.findById(cpf);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(cpf);
            return ResponseEntity.ok("Usuário removido com sucesso");
        }
        return ResponseEntity.notFound().build();
    }
}
