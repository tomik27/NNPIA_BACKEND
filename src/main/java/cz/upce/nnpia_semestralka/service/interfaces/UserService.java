package cz.upce.nnpia_semestralka.service.interfaces;

import cz.upce.nnpia_semestralka.Entity.User;
import cz.upce.nnpia_semestralka.dto.SignUserDto;
import cz.upce.nnpia_semestralka.dto.UserDto;

public interface UserService {
    User findUserByUsername(String name);

    User saveUser(User user);

    User getLoggedUser();

    UserDto getUserDetail(Long id);

    SignUserDto signUp(SignUserDto signUpUserDto) throws Exception;

}
