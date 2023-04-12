package cz.upce.nnpia_semestralka.service;

import cz.upce.nnpia_semestralka.Repository.UserRepository;
import cz.upce.nnpia_semestralka.domain.User;
import cz.upce.nnpia_semestralka.dto.ChangePasswordDto;
import cz.upce.nnpia_semestralka.dto.SignUserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Test
    void changePassword() {
        SignUserDto userDto = new SignUserDto();
        userDto.setPassword("heslo");
        userDto.setUsername("username3");

        User saveUser = userService.addUser(userDto);

        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword("heslo");
        changePasswordDto.setNewPassword("password");
        User newUser = userService.changePassword(saveUser.getId(), changePasswordDto);
        assertTrue(BCrypt.checkpw("password", newUser.getPassword())
        );

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

}