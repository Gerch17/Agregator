package ru.gerch.agregator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import ru.gerch.agregator.dto.UserDto;
import ru.gerch.agregator.security.jwt.JwtRequest;
import ru.gerch.agregator.security.jwt.JwtResponse;
import ru.gerch.agregator.security.jwt.RefreshJwtRequest;
import ru.gerch.agregator.service.AuthService;
import ru.gerch.agregator.service.UserService;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("register")
    public ResponseEntity<JwtResponse> register(@RequestBody UserDto userDto) throws AuthException {
        if (userService.getByLogin(userDto.getUserName()).isPresent()) {
            throw new AuthException("User " + userDto.getUserName() + "already exists");
        }
        userService.register(userDto);
        return ResponseEntity.ok(authService.login(new JwtRequest(userDto.getUserName(), userDto.getPassword())));
    }

}