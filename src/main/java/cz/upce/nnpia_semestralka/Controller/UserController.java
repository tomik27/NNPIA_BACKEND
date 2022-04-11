package cz.upce.nnpia_semestralka.Controller;

import cz.upce.nnpia_semestralka.Entity.User;
import cz.upce.nnpia_semestralka.dto.FilmDto;
import cz.upce.nnpia_semestralka.dto.SignUserDto;
import cz.upce.nnpia_semestralka.dto.UserDto;
import cz.upce.nnpia_semestralka.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDetail(@PathVariable Long id) {
        UserDto filmDto = userService.getUserDetail(id);
        return ResponseEntity.ok(filmDto);
    }

    @PostMapping(path = "/signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> signup(@RequestBody SignUserDto userDto) {
        try {
            SignUserDto newUser = userService.signUp(userDto);
            return ResponseEntity.ok(newUser);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/{username}")
 //   @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getUserRole(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user.getRole());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User was not found.");
        }
    }

}

