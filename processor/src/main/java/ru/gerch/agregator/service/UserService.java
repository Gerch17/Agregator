package ru.gerch.agregator.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gerch.agregator.dto.UserDto;
import ru.gerch.agregator.entity.User;
import ru.gerch.agregator.mapper.UserMapper;
import ru.gerch.agregator.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public Optional<User> getByLogin(@NonNull String login) {
        return userRepository.findUserByUserName(login);
    }

    public void register(UserDto userDto) {
        User user = UserMapper.INSTANCE.register(userDto, roleService);
        userRepository.save(user);
    }

    public User getById(UUID id) {
        return userRepository.getById(id);
    }
}
