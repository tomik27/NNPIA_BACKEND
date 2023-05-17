package cz.upce.nnpia_semestralka.Controller;


import cz.upce.nnpia_semestralka.Repository.UserRepository;
import cz.upce.nnpia_semestralka.config.jwt.JwtUtils;
import cz.upce.nnpia_semestralka.config.services.UserDetailsImpl;
import cz.upce.nnpia_semestralka.domain.RoleEnum;
import cz.upce.nnpia_semestralka.domain.User;
import cz.upce.nnpia_semestralka.dto.*;
import cz.upce.nnpia_semestralka.payload.request.LoginRequest;
import cz.upce.nnpia_semestralka.payload.response.JwtResponse;
import cz.upce.nnpia_semestralka.payload.response.MessageResponse;
import cz.upce.nnpia_semestralka.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@CrossOrigin
@Tag(name = "User controller")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final   JwtUtils jwtUtils;
    private final PasswordEncoder encoder;


    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder encoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
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
    @SecurityRequirement(name = "NNPIA_API")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> addUser(@RequestBody @Valid AddUserDto userDto) {
        return ResponseEntity.ok(userService.addUser(userDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUserDto signUpRequest) {
        User register = userService.register(signUpRequest);
        return ResponseEntity.ok(register);
    }

    @Operation(summary = "Get user info")
    @SecurityRequirement(name = "NNPIA_API")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content)})
    @SecurityRequirement(name = "NNPIA_API")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @SecurityRequirement(name = "NNPIA_API")
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
    @SecurityRequirement(name = "NNPIA_API")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @SecurityRequirement(name = "NNPIA_API")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .findFirst()
                .orElse("defaultAuthorityValue");

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role));
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> addFilmToUser(@RequestBody @Valid AddFilmToUserDto addFilmToUserDto) {
        userService.addFilmToUser(addFilmToUserDto);
            return ResponseEntity.ok().build();
    }
}
