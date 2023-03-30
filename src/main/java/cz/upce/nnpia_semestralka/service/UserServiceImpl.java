package cz.upce.nnpia_semestralka.service;

import cz.upce.nnpia_semestralka.domain.User;
import cz.upce.nnpia_semestralka.domain.UserHasFilm;
import cz.upce.nnpia_semestralka.Repository.UserHasFilmRepository;
import cz.upce.nnpia_semestralka.Repository.UserRepository;
import cz.upce.nnpia_semestralka.dto.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl  {

    private final UserRepository userRepository;
    private final UserHasFilmRepository userHasFilmRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, UserHasFilmRepository userHasFilmRepository, ModelMapper mapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userHasFilmRepository = userHasFilmRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public User saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return     userRepository.save(user);
    }

   /* public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByUsername(authentication.getName());

    }*/

    public UserDto getUserDetail(Long id) {
      UserDto userDto = null;
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            //todo add person
            Set<UserHasFilm> userHasFilmSet = userHasFilmRepository.findByUserId(user.getId());

            List<UserHasFilmDto> userDtoList = mapper.map(userHasFilmSet,
                    new TypeToken<List<UserHasFilmDto>>() {
                    }.getType());

            userDto = mapper.map(user, UserDto.class);

            userDto.setUserRatingFilms(userDtoList);
          /*  filmDto.setPersonsInFilms(filmDtoList);
            filmDto.setGenre(film.getGenre());
            filmDto.setReleaseDate(film.getReleaseDate());
            filmDto.setName(film.getName());
            filmDto.setPath_to_image(film.getPath_to_image());*/
            return userDto;
        }else
            throw new NoSuchElementException("User not found.");
    }

    public SignUserDto signUp(SignUserDto signUpUserDto) throws Exception {
        if (userRepository.findUserByUsername(signUpUserDto.getUsername()) == null) {
            if (!signUpUserDto.getPassword().equals(signUpUserDto.getRepeatPassword())) {
                throw new SecurityException("Passwords does not match.");
            }

            User user = new User();
            user.setUsername(signUpUserDto.getUsername());
            user.setPassword(signUpUserDto.getPassword());
            user.setEmail(signUpUserDto.getEmailAddress());
            User newUser = saveUser(user);
            return mapper.map(newUser, SignUserDto.class);
        }
        else {
            throw new EntityExistsException("User with username " + signUpUserDto.getUsername() + " already exists.");
        }
    }




}
