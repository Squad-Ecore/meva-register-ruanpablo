package com.meva.finance.api;

import com.meva.finance.dto.UserMevaDto;
import com.meva.finance.model.UserMeva;
import com.meva.finance.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register") @Transactional
    public ResponseEntity<UserMevaDto> register(@RequestBody @Valid UserMevaDto userDto, UriComponentsBuilder uriBuilder){
        return userService.register(userDto, uriBuilder);
    }

    @PutMapping("/update/{cpf}") @Transactional
    public ResponseEntity<UserMevaDto> update(@PathVariable String cpf, @RequestBody @Valid UserMevaDto userDto){
        return userService.update(userDto, cpf);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserMeva>> list(){
        return ResponseEntity.ok(userService.list());
    }

    @GetMapping("/searchForCpf/{cpf}")
    public ResponseEntity searchCpf(@PathVariable String cpf){
        return userService.searchForCpf(cpf);
    }

    @DeleteMapping("/delete/{cpf}")
    public ResponseEntity delete(@PathVariable String cpf){
        return userService.delete(cpf);
    }
}
