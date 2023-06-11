package com.meva.finance.service;

import com.meva.finance.dto.FamilyDto;
import com.meva.finance.dto.UserMevaDto;
import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import com.meva.finance.validation.ValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private FamilyRepository familyRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testValidExceptionFamily_FamilyIdEmpty_ThrowsValidException() {
        UserMevaDto userDto = new UserMevaDto();
        userDto.setFamilyDto(new FamilyDto());
        userDto.getFamilyDto().setId(null);
        assertThrows(ValidException.class, () -> userService.validExceptionFamily(userDto));
    }

    @Test
    void testValidExceptionFamily_FamilyIdNotFound_ThrowsValidException() {
        UserMevaDto userDto = new UserMevaDto();
        userDto.setFamilyDto(new FamilyDto(50L, "Santos"));
        Mockito.when(familyRepository.findById(userDto.getFamilyDto().getId())).thenReturn(Optional.empty());
        try {
            userService.validExceptionFamily(userDto);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "O id da família não foi encontrado.");
        }
    }

    @Test
    void testRegisterUsers_CpfFound_ThrowsValidException() {
        UserMevaDto userDto = new UserMevaDto();
        userDto.setCpf("12345678900");
        when(userRepository.findById(userDto.getCpf())).thenReturn(Optional.of(new User()));
//        assertThrows(ValidException.class, () -> userService.register(userDto));
        try {
            userService.register(userDto);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Cpf já cadastrado no sistema.");
        }
    }

    @Test
    void testRegisterUsers_NewFamily_Sucess() throws ValidException {
        UserMevaDto userDto = new UserMevaDto();
        userDto.setCpf("123456789");

        FamilyDto familyDto = new FamilyDto();
        familyDto.setId(0L);
        userDto.setFamilyDto(familyDto);

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        when(familyRepository.findById(userDto.getFamilyDto().getId())).thenReturn(Optional.of(new Family()));
        when(familyRepository.save(any(Family.class))).thenReturn(new Family());
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Act
        User result = userService.register(userDto);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findById(anyString());
        verify(familyRepository, times(1)).findById(anyLong());
        verify(familyRepository, times(1)).save(any(Family.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUsers_ExistingFamily_Success() throws ValidException {
        UserMevaDto userDto = new UserMevaDto();
        userDto.setCpf("123456789");

        Long existingFamilyId = 1L;
        Family existingFamily = new Family();
        existingFamily.setId(1L);

        FamilyDto familyDto = new FamilyDto();
        familyDto.setId(existingFamilyId);
        userDto.setFamilyDto(familyDto);

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        when(familyRepository.findById(existingFamilyId)).thenReturn(Optional.of(existingFamily));
        when(userRepository.save(any(User.class))).thenReturn(userDto.converter());

        // Act
        User result = userService.register(userDto);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findById(anyString());
        verify(familyRepository, times(1)).findById(existingFamilyId);
        verify(userRepository, times(1)).save(userDto.converter());
    }

    @Test
    void testUpdateUsers_CpfNotFound_ThrowValidException() throws ValidException {
        String cpf = "67352651427";
        UserMevaDto userDto = new UserMevaDto();

        when(userRepository.findById((cpf))).thenReturn(Optional.empty());

        // Assert
        assertThrows(ValidException.class, () -> userService.update(userDto, cpf));
        verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    void testUpdateUsers_Sucess() throws ValidException {
        String cpf = "67352651427";
        UserMevaDto userDto = new UserMevaDto();
        User userExisting = new User();
        User userUpdate = userDto.converter();

        when(userRepository.findById((cpf))).thenReturn(Optional.of(userExisting));
        when(userRepository.save(userUpdate)).thenReturn(userUpdate);

        userService.update(userDto, cpf);

        // Assert
        assertNotNull(userUpdate);
        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(1)).save(userUpdate);
    }

    @Test
    void testDeleteUsersNotFound() {
        String cpf = "67352651427";
        when(userRepository.findById(cpf)).thenReturn(Optional.empty());

        //act
        ResponseEntity<String> response = userService.delete(cpf);

        //aserts
        verify(userRepository, never()).deleteById(anyString());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteUsersExists() {
        String cpf = "67352651427";
        User user = new User();
        when(userRepository.findById(cpf)).thenReturn(Optional.of(user));

        //act
        ResponseEntity<String> response = userService.delete(cpf);

        //aserts
        verify(userRepository, times(1)).deleteById(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), "Usuário removido com sucesso");
    }

    @Test
    void testListUsers(){
        //dados test
        User user1 = new User();
        User user2 = new User();
        List<User> list = Arrays.asList(user1, user2);

        //mock
        when(userRepository.findAll()).thenReturn(list);

        //act
        List<User> result = userService.list();

        assertEquals(2, result.size());
        assertEquals(user1, result.get(0));
        assertEquals(user2, result.get(1));
    }

    @Test
    void testFindUser(){
        //dados test
        String cpf = "67352651427";
        User user = new User();
        user.setName("Ruan");

        //mock
        when(userRepository.findById(cpf)).thenReturn(Optional.of(user));

        //act
        User result = userService.findCpf(cpf);

        assertEquals(result.getName(), "Ruan");
        assertNotNull(result);
    }

    @Test
    void testFindUserNotFound(){
        //dados test
        String cpf = "67352651427";

        //mock
        when(userRepository.findById(cpf)).thenReturn(Optional.empty());

        //act + assert
        assertThrows(ValidException.class, ()-> userService.findCpf(cpf));
    }

}
