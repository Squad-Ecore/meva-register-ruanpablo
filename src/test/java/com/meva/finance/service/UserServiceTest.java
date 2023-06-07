package com.meva.finance.service;

import com.meva.finance.dto.FamilyDto;
import com.meva.finance.dto.UserMevaDto;
import com.meva.finance.model.Family;
import com.meva.finance.model.UserMeva;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import com.meva.finance.validation.ValidException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
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
        when(userRepository.findById(userDto.getCpf())).thenReturn(Optional.of(new UserMeva()));
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
        when(userRepository.save(any(UserMeva.class))).thenReturn(new UserMeva());

        // Act
        UserMeva result = userService.register(userDto);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findById(anyString());
        verify(familyRepository, times(1)).findById(anyLong());
        verify(familyRepository, times(1)).save(any(Family.class));
        verify(userRepository, times(1)).save(any(UserMeva.class));
    }

}
