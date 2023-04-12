package cz.upce.nnpia_semestralka.service;

import cz.upce.nnpia_semestralka.Repository.FilmRepository;
import cz.upce.nnpia_semestralka.Repository.UserHasFilmRepository;
import cz.upce.nnpia_semestralka.Repository.UserRepository;
import cz.upce.nnpia_semestralka.config.WebSecurityConfig;
import cz.upce.nnpia_semestralka.domain.Film;
import cz.upce.nnpia_semestralka.domain.RoleEnum;
import cz.upce.nnpia_semestralka.domain.User;
import cz.upce.nnpia_semestralka.domain.UserHasFilm;
import cz.upce.nnpia_semestralka.dto.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final WebSecurityConfig webSecurityConfig;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final ModelMapper mapper;
    private final UserHasFilmRepository userHasFilmRepository;



    public User addUser(SignUserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("The username already exists.");
        }
        //User user = ConversionService.convertToUser(userDto);
       // User user = ConversionService.convertToUser(userDto);
        User user = mapper.map(userDto, User.class);

        user.setPassword(webSecurityConfig.getEncoder().encode(user.getPassword()));

        User save = userRepository.save(user);
        return save;
    }

    public User removeUser(Long userId) {
            User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
            userRepository.deleteById(userId);
        return user;
    }

    public User getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
       // UserDetailOutDto detailOutDto = ConversionService.convertToUserDetailOutDto(user);
        return user;
    }



    public User changePassword(Long userId, ChangePasswordDto changePasswordDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        if (bCryptPasswordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
            User save = userRepository.save(user);
            return save;
        }
        throw new IllegalArgumentException("Old password doen't match!");

    }

    public List<String>  getAllRoles() {
        List<String> roles = new ArrayList<>();
        for (RoleEnum role : RoleEnum.values()) {
            roles.add(role.getDisplayValue());
        }
        return roles;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
      /*  List<UserDetailOutDto> userDetailOutDtos = new ArrayList<>();
        for (User user : users) {
            userDetailOutDtos.add(ConversionService.convertToUserDetailOutDto(user));
        }
        return userDetailOutDtos;*/
    }

    public void addFilmToUser(AddFilmToUserDto addFilmToUserDto) {
        User user = userRepository.findById(addFilmToUserDto.getUserId()).orElseThrow(() -> new NoSuchElementException("User not found!"));
        Film film = filmRepository.findById(addFilmToUserDto.getFilmId()).orElseThrow(() -> new NoSuchElementException("Film not found!"));
        UserHasFilm userHasFilm = userHasFilmRepository.findByUser_IdAndFilm_Id(addFilmToUserDto.getUserId(), addFilmToUserDto.getFilmId());

        UserHasFilm newUserHasFilm = new UserHasFilm();
        newUserHasFilm.setFilm(film);
        newUserHasFilm.setUser(user);
        newUserHasFilm.setComment(addFilmToUserDto.getComment());
        newUserHasFilm.setRating(addFilmToUserDto.getRating());
               // =mapper.map(addFilmToUserDto,UserHasFilm.class);

        if(userHasFilm!=null)
            newUserHasFilm.setId(userHasFilm.getId());

        userHasFilmRepository.save(newUserHasFilm);
    }

    @PostConstruct
    public void init() {

        if (!userRepository.existsByUsername("admin")) {
            User userAdmin = new User();
            String username = "admin";
            userAdmin.setUsername(username);
            userAdmin.setPassword("$2a$10$MQuBpeE5CbgERbKN7ecd1ea/Y3XwpfWVOqKFErLjbhT382.Rgviy.");
            userAdmin.setRole(RoleEnum.ROLE_ADMIN);
            userRepository.save(userAdmin);
        }
    }
/*
    public User editUser(Long userId, UserDto userDto) {
        if (userRepository.existsByUsernameAndIdIsNot(userDto.getUsername(), userId)) {
            throw new IllegalArgumentException("The username already exists.");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        User editedUser = ConversionService.convertToUser(userDto, user, role);
        User save = userRepository.save(editedUser);
        return save;
    }



    public List<Role> getAllRoles() {
        List<Role> all = roleRepository.findAll();
        return all;
    }

    public List<UserDetailOutDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDetailOutDto> userDetailOutDtos = new ArrayList<>();
        for (User user : users) {
            userDetailOutDtos.add(ConversionService.convertToUserDetailOutDto(user));
        }
        return userDetailOutDtos;
    }

    public Integer getSalary(Long userId) {
        return  10;
    }
    */

}
