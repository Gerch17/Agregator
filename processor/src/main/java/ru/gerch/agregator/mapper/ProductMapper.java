package ru.gerch.agregator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.gerch.agregator.dto.ProductDto;
import ru.gerch.agregator.dto.RequestDto;
import ru.gerch.agregator.entity.Product;
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
public abstract class ProductMapper {

    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "img", ignore = true)
    public abstract Product fromRequest(Request request);

    @InheritInverseConfiguration
    @Mapping(target = "img", ignore = true)
    public abstract ProductDto toDto(Product product);
}