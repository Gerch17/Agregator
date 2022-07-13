package ru.gerch.agregator.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gerch.agregator.dto.HistoryDto;
import ru.gerch.agregator.entity.History;
import ru.gerch.agregator.enums.EnumStatus;

import java.util.Set;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD,
        uses = {UserMapper.class, ProductMapper.class},
        imports = {UUID.class, Set.class, EnumStatus.class, Boolean.class}
)
public abstract class HistoryMapper {

    public static final HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);

    @Autowired
    protected ProductMapper productMapper;
    @Autowired
    protected UserMapper userMapper;

    @Mapping(target = "user", expression = "java(userMapper.userToUserDto(history.getUser()))")
    @Mapping(target = "product", expression = "java(productMapper.toDto(history.getProduct()))")
    public abstract HistoryDto toDto(History history);
}