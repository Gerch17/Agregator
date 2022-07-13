package ru.gerch.agregator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.gerch.agregator.dto.RequestDto;
import ru.gerch.agregator.entity.Request;
import ru.gerch.agregator.enums.EnumStatus;
import ru.gerch.agregator.service.UserService;

import java.util.Set;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD,
        uses = {UserService.class},
        imports = {UUID.class, Set.class, EnumStatus.class}
)
public abstract class RequestMapper {

    public static final RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "status", expression = "java(EnumStatus.REVIEW)")
    public abstract Request create(RequestDto requestDto);

    @InheritInverseConfiguration
    @Mapping(target = "userId", source = "user.id")
    public abstract RequestDto toDto(Request request);
}
