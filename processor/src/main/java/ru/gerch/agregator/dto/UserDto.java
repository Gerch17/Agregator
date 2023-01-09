package ru.gerch.agregator.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private UUID id;
    private String userName;
    private String password;
    private String email;
    private String role;

}
