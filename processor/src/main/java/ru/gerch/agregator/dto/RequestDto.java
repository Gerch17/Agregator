package ru.gerch.agregator.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class RequestDto {

  private UUID id;
  private UUID userId;
  private String name;
  private String description;
  private int price;
  private MultipartFile file;
}
