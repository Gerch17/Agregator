package ru.gerch.agregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gerch.agregator.entity.History;
import ru.gerch.agregator.entity.Product;
import ru.gerch.agregator.entity.User;
import ru.gerch.agregator.enums.SortEnum;
import ru.gerch.agregator.repository.HistoryRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final AuthService authService;
    private final UserService userService;

    public void historyFromRequest(Product product, User user) {
        History history = new History();
        history.setProduct(product);
        history.setUser(user);
        history.setId(UUID.randomUUID());
        history.setDate(LocalDateTime.now());
        historyRepository.save(history);
    }

    public Page<History> findAllByPageable(int pageNum, int pageSize, SortEnum sortEnum) {
        Pageable pageable;
        if (SortEnum.ASC.equals(sortEnum)) {
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("date"));
        } else if (SortEnum.DESC.equals(sortEnum)) {
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("date").descending());
        } else {
            pageable = PageRequest.of(pageNum - 1, pageSize);
        }
        User user = userService.getByLogin(authService.getAuthInfo().getUsername()).get();
        return historyRepository.findHistoriesByUser(user, pageable);
    }

    public long count() {
        return historyRepository.count();
    }
}
