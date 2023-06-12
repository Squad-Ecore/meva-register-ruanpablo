package com.meva.finance.controller;

import com.meva.finance.dto.UserMevaDto;
import com.meva.finance.model.User;
import com.meva.finance.service.UserService;
import com.meva.finance.validation.ValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> list(){
        return ResponseEntity.ok(userService.list());
    }

    @GetMapping("/searchForCpf/{cpf}")
    public ResponseEntity<UserMevaDto> searchCpf(@PathVariable String cpf) throws ValidException{
        return ResponseEntity.ok(userService.findCpf(cpf).converter());
    }

    @PostMapping("/register") @Transactional
    public ResponseEntity<UserMevaDto> register(@RequestBody UserMevaDto userDto) throws ValidException {
        return ResponseEntity.ok(userService.register(userDto).converter());
    }

    @PutMapping("/update/{cpf}") @Transactional
    public ResponseEntity<UserMevaDto> update(String cpf, @RequestBody UserMevaDto userDto){
        return ResponseEntity.ok(userService.update(userDto, cpf));
    }

    @DeleteMapping("/delete/{cpf}")
    public ResponseEntity<String> delete(@PathVariable String cpf) {
        return userService.delete(cpf);
    }
}
