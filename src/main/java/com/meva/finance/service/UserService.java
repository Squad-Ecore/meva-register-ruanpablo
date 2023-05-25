package com.meva.finance.service;

import com.meva.finance.dto.UserMevaDto;
import com.meva.finance.model.UserMeva;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import com.meva.finance.validation.ErroDeValidacaoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FamilyRepository familyRepository;

    public ResponseEntity<UserMevaDto> register(UserMevaDto userDto, UriComponentsBuilder uriBuilder) {
        if (userRepository.existsById(userDto.getCpf())) {
            return ResponseEntity.badRequest().build();
        }
        URI uri = uriBuilder.path("/users/{cpf}").buildAndExpand(userDto.getCpf()).toUri();
        userRepository.save(new UserMeva(userDto, familyRepository));
        return ResponseEntity.created(uri).body(userDto);
    }

    public ResponseEntity<UserMevaDto> update(UserMevaDto userDto, String cpf) {
        Optional<UserMeva> optional = userRepository.findById(cpf);
        if (optional.isPresent()) {
            userRepository.save(updateRepository(optional.get(), userDto));
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

    public List<UserMeva> list() {
        return userRepository.findAll();
    }

    public ResponseEntity<UserMeva> searchForCpf(String cpf) {
        return ResponseEntity.ok(em.createQuery("SELECT u FROM UserMeva u WHERE u.cpf = :cpf", UserMeva.class).
                setParameter("cpf", cpf).getSingleResult());
    }

    private UserMeva updateRepository(UserMeva userMeva, UserMevaDto userDto) {
        userMeva.setCpf(userDto.getCpf());
        userMeva.setName(userDto.getName());
        userMeva.setGenre(userDto.getGenre());
        userMeva.setBirth(userDto.getBirth());
        userMeva.setState(userDto.getState());
        userMeva.setCity(userDto.getCity());
        userMeva.getFamily().setDescription(userDto.getFamily());
        return userMeva;
    }
}
