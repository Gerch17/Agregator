package ru.gerch.agregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gerch.agregator.entity.Role;
import ru.gerch.agregator.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        //Remove later
        return roleRepository.findRoleByRoleName(name);
    }
}
