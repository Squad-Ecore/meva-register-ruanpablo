package com.meva.finance.controller;

import com.meva.finance.dto.FamilyDto;
import com.meva.finance.dto.UserMevaDto;
import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import com.meva.finance.service.UserService;
import com.meva.finance.validation.ValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListSucess() {
        List<User> list = new ArrayList<>();
        list.add(new User());

        when(userService.list()).thenReturn(list);
        ResponseEntity<List<User>> response = userController.list();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().size(), 1);
    }

    @Test
    void testFindCpfNotFound_ThrowValidException() {
        String cpf = "52437162982";
        when(userService.findCpf(cpf)).thenThrow(ValidException.class);//then throw - retorna exception
        assertThrows(ValidException.class, ()-> userController.searchCpf(cpf));
    }

    @Test
    void testFindUserSucess(){
        User user = new User();
        String cpf = "52437162982";
        when(userService.findCpf(cpf)).thenReturn(user);

        ResponseEntity<UserMevaDto> response = userController.searchCpf(cpf);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), user.converter());
    }

    @Test
    void testRegister_ThrowValidException() {
        UserMevaDto userDto = new UserMevaDto();

        when(userService.register(any(UserMevaDto.class))).thenThrow(ValidException.class);

        assertThrows(ValidException.class, ()-> userController.register(userDto));
    }

    @Test
    void testRegister_Sucess(){
        UserMevaDto userDto = new UserMevaDto("525263726372", "Ruan", "M", LocalDate.of(2002, 04,20),
                "SP", "São Paulo", new FamilyDto(1L, "Souza"));
        User user = userDto.converter();
        when(userService.register(userDto)).thenReturn(user);

        ResponseEntity<UserMevaDto> response = userController.register(userDto);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), user.converter());
    }

    @Test
    void testUpdate_ThrowValidException() {
        String cpf = "52437162982";
        UserMevaDto userDto = new UserMevaDto("525263726372", "Ruan", "M", LocalDate.of(2002, 04,20),
                "SP", "São Paulo", new FamilyDto(1L, "Souza"));

        when(userService.update(userDto, cpf)).thenThrow(ValidException.class);

        assertThrows(ValidException.class, ()-> userController.update(cpf, userDto));
    }

    @Test
    void testUpdate_Sucess() {
        String cpf = "52437162982";
        UserMevaDto userDto = new UserMevaDto("525263726372", "Ruan", "M", LocalDate.of(2002, 04,20),
                "SP", "São Paulo", new FamilyDto(1L, "Souza"));


        when(userService.update(userDto, cpf)).thenReturn(userDto);

        ResponseEntity<UserMevaDto> response = userController.update(cpf, userDto);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), userDto);
    }

    @Test
    void testDelete_HttpOk() {
        String cpf = "52437162982";

        when(userService.delete(cpf)).thenReturn(ResponseEntity.ok("Usuário removido com sucesso"));

        ResponseEntity<String> response = userController.delete(cpf);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), "Usuário removido com sucesso");
    }

    @Test
    void testDelete_HttpNotFound() {
        String cpf = "52437162982";
        ResponseEntity<String> responseOk = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        when(userService.delete(cpf)).thenReturn(responseOk);

        ResponseEntity<String> response = userController.delete(cpf);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}