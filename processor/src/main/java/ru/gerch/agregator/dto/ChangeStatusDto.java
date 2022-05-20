package ru.gerch.agregator.dto;

import lombok.Data;
import ru.gerch.agregator.enums.EnumStatus;

import java.util.UUID;

@Data
public class ChangeStatusDto {
   private UUID id;
   private EnumStatus status;
}
