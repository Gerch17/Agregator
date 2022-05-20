package ru.gerch.agregator.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
    private UUID id;

}
