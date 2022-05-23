package ru.gerch.agregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gerch.agregator.entity.History;
import ru.gerch.agregator.entity.Product;
import ru.gerch.agregator.entity.User;
import ru.gerch.agregator.repository.HistoryRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    public void historyFromRequest(Product product, User user) {
        History history = new History();
        history.setProduct(product);
        history.setUser(user);
        history.setId(UUID.randomUUID());
        history.setDate(LocalDateTime.now());
        historyRepository.save(history);
    }
}
