package ru.gerch.agregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gerch.agregator.entity.History;

import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {
}
