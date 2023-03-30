package cz.upce.nnpia_semestralka.Controller;


import cz.upce.nnpia_semestralka.Repository.UserRepository;
import cz.upce.nnpia_semestralka.config.JwtTokenUtil;
import cz.upce.nnpia_semestralka.domain.RoleEnum;
import cz.upce.nnpia_semestralka.domain.User;
import cz.upce.nnpia_semestralka.dto.*;
import cz.upce.nnpia_semestralka.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.JwtRequest;
import model.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Tag(name = "User controller")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService jwtInMemoryUserDetailsService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenUtil jwtTokenUtil, UserDetailsService jwtInMemoryUserDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsService;
    }

    @Operation(summary = "Add user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SignUserDto.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "User not found",
                    content = @Content),})
    @SecurityRequirement(name = "NNPRO_API")
      @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_Okres')")
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody @Valid SignUserDto userDto) {
        return ResponseEntity.ok(userService.addUser(userDto));
    }

    @Operation(summary = "Get user info")
    @SecurityRequirement(name = "NNPRO_API")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }
/*
    @Operation(summary = "Get user info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "User not found",
                    content = @Content),})
    @SecurityRequirement(name = "NNPRO_API")
    @GetMapping("/getSalary/{userId}")
    public ResponseEntity<?> getSalary(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getSalary(userId));
    }



    @Operation(summary = "Edit user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User edited and returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "User not found",
                    content = @Content),})
    @SecurityRequirement(name = "NNPRO_API")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_Okres')")
    @PutMapping("/editUser/{userId}")
    public ResponseEntity<?> editUser(@PathVariable Long userId, @RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.editUser(userId, userDto));
    }

*/

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content)})
    @SecurityRequirement(name = "NNPRO_API")
    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles returned (List<Role>)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleEnum.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "User not found",
                    content = @Content),})
    @SecurityRequirement(name = "NNPRO_API")
    @GetMapping("/getAllRoles")
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(userService.getAllRoles());
    }

    @Operation(summary = "Remove user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted and returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "User not found",
                    content = @Content),})
    @SecurityRequirement(name = "NNPRO_API")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_Okres')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> removeOwner(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.removeUser(userId));
    }



    @Operation(summary = "Change user password ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed and user returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "User not found",
                    content = @Content),})
    @SecurityRequirement(name = "NNPRO_API")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_Okres')")
    @PutMapping("/changeUserPassword/{userId}")
    public ResponseEntity<?> changeUserPassword(@PathVariable Long userId, @RequestBody @Valid ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(userService.changePassword(userId, changePasswordDto));
    }


    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in and jwt token returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))}),

            @ApiResponse(responseCode = "500", description = "User not found",
                    content = @Content),})
    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final User user = userRepository.findByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        ErrorDto errorDto = new ErrorDto();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errorDto.addError(errorMessage);
        });
        return errorDto;
    }

    @PostMapping("/addFilm")
  //  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> addFilmToUser(@RequestBody @Valid AddFilmToUserDto addFilmToUserDto) {
        userService.addFilmToUser(addFilmToUserDto);
            return ResponseEntity.ok().build();
    }
}
