package ru.gerch.agregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gerch.agregator.repository.HistoryRepository;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
}
