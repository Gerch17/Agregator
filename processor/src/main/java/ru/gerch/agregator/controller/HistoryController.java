package ru.gerch.agregator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gerch.agregator.dto.HistoryDto;
import ru.gerch.agregator.dto.PageInfo;
import ru.gerch.agregator.dto.PageResponse;
import ru.gerch.agregator.enums.SortEnum;
import ru.gerch.agregator.mapper.HistoryMapper;
import ru.gerch.agregator.service.AuthService;
import ru.gerch.agregator.service.HistoryService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    private final HistoryMapper historyMapper;

    private final AuthService authService;

    @GetMapping("/")
    @PreAuthorize("@authService.authInfo.authenticated")
    public ResponseEntity<PageResponse<HistoryDto>> getHistory(
            @RequestParam(defaultValue = "1") @Min(1) int pageNum,
            @RequestParam(defaultValue = "10") @Min(1) @Max(250) int pageSize,
            @RequestParam(required = false) SortEnum sort) {
        var result = historyService.findAllByPageable(pageNum, pageSize, sort)
                .stream()
                .map(historyMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageResponse<>(
                result,
                new PageInfo(historyService.count(), pageNum, pageSize, result.size())));
    }

}
