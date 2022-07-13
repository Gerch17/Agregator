package ru.gerch.agregator.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class HistoryDto {
    private UUID id;
    private UserDto user;
    private ProductDto product;
    private LocalDateTime date;
}
