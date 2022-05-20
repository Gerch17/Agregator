package ru.gerch.agregator.dto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private UUID uuid;
    private String userName;
    private String password;
    private String email;
    private String role;

}
