package ru.gerch.agregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gerch.agregator.entity.Role;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findRoleByRoleName(String name);
}
