package ru.gerch.agregator.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.gerch.agregator.dto.UserDto;
import ru.gerch.agregator.entity.Role;
import ru.gerch.agregator.entity.User;
import ru.gerch.agregator.service.RoleService;

import java.util.Set;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD,
        uses = {RoleService.class},
        imports = {UUID.class, Set.class, Role.class}
)
public abstract class UserMapper {


    protected static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "password", expression = "java(encoder.encode(userDto.getPassword()))")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "roles", expression = "java(Set.of(roleService.findRoleByName(userDto.getRole())))")
    public abstract User register(UserDto userDto, @Context RoleService roleService);

    @InheritInverseConfiguration
    @Mapping(target = "password", ignore = true)
    public abstract UserDto userToUserDto(User user);


}
