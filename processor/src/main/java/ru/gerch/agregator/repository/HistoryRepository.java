package ru.gerch.agregator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gerch.agregator.entity.History;
import ru.gerch.agregator.entity.User;

import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {
    Page<History> findHistoriesByUser(User user, Pageable pageable);
}
