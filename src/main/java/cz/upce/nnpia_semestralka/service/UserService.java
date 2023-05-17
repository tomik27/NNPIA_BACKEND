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
import cz.upce.nnpia_semestralka.payload.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final WebSecurityConfig webSecurityConfig;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final ModelMapper mapper;
    private final UserHasFilmRepository userHasFilmRepository;
    private final PasswordEncoder encoder;

    public User register(SignUserDto signUpRequest){

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new IllegalArgumentException("The username already exists.");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("Email is already exists.");
        }

        RoleEnum roleEnum = RoleEnum.ROLE_USER;

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail(),roleEnum);

        User save = userRepository.save(user);
        return save;
    }

    public User addUser(AddUserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("The username already exists.");
        }
        User user = mapper.map(userDto, User.class);

        user.setPassword(encoder.encode(user.getPassword()));

        RoleEnum roleEnum = RoleEnum.ROLE_USER;
        if(RoleEnum.ROLE_ADMIN.getDisplayValue().equals(userDto.getRole()))
            roleEnum=RoleEnum.ROLE_ADMIN;

        user.setRole(roleEnum);
        User save = userRepository.save(user);
        return save;
    }

    public User updateUser(Long id, UpdateUserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found."));
        String oldPassword = user.getPassword();

        if (userDto.getUsername() != null) {
            if (userRepository.existsByUsername(userDto.getUsername()) && !userDto.getUsername().equals(user.getUsername())) {
                throw new IllegalArgumentException("The username already exists.");
            }
            user.setUsername(userDto.getUsername());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getPassword() != null && !userDto.getPassword().equals(oldPassword)) {
            user.setPassword(encoder.encode(userDto.getPassword()));
        }

        if (userDto.getRole() != null) {
            RoleEnum roleEnum = RoleEnum.ROLE_USER;
            if(RoleEnum.ROLE_ADMIN.getDisplayValue().equals(userDto.getRole()))
                roleEnum=RoleEnum.ROLE_ADMIN;

            user.setRole(roleEnum);
        }

        return userRepository.save(user);
    }



    public User removeUser(Long userId) {
            User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
            userRepository.deleteById(userId);
        return user;
    }

    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        UserDto userDto = convertToUserDto(user);
        return userDto;
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

    public UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().ordinal());

        List<UserHasFilmDto> userHasFilmDtos = user.getUserRatingFilms().stream()
                .map(this::convertUserHasFilmToDto)
                .collect(Collectors.toList());
        userDto.setUserRatingFilms(userHasFilmDtos);

        return userDto;
    }

    private UserHasFilmDto convertUserHasFilmToDto(UserHasFilm userHasFilm) {
        UserHasFilmDto userHasFilmDto = new UserHasFilmDto();
        userHasFilmDto.setId(userHasFilm.getId());
        userHasFilmDto.setRating(userHasFilm.getRating());
        userHasFilmDto.setComment(userHasFilm.getComment());
        userHasFilmDto.setUserId(userHasFilm.getUser().getId());
        userHasFilmDto.setFilmId(userHasFilm.getFilm().getId());
        userHasFilmDto.setFilmName(userHasFilm.getFilm().getName());

        return userHasFilmDto;
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
}
