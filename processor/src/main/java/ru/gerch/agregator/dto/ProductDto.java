package ru.gerch.agregator.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;
    private String description;
    private MultipartFile img;
    private int price;
}
