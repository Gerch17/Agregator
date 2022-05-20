package ru.gerch.agregator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gerch.agregator.dto.ChangeStatusDto;
import ru.gerch.agregator.dto.PageInfo;
import ru.gerch.agregator.dto.PageResponse;
import ru.gerch.agregator.dto.RequestDto;
import ru.gerch.agregator.enums.EnumStatus;
import ru.gerch.agregator.mapper.RequestMapper;
import ru.gerch.agregator.service.AuthService;
import ru.gerch.agregator.service.RequestService;

import javax.security.auth.message.AuthException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("api/v1/request")
@RestController
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final RequestMapper requestMapper;

    private final AuthService authService;

    @PostMapping("create")
    @PreAuthorize("@authService.authInfo.hasRole('PROVIDER')")
    public ResponseEntity<String> createRequest(@RequestBody RequestDto requestDto) {
        requestService.createRequest(requestDto);
        return ResponseEntity.ok("");
    }

    @GetMapping("admin")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<PageResponse<RequestDto>> getAllRequest(
            @RequestParam(defaultValue = "1") @Min(1) int pageNum,
            @RequestParam(defaultValue = "10") @Min(1) @Max(250) int pageSize
    ) {
        var result = requestService.findAllByPageable(pageNum, pageSize)
                .stream()
                .map(requestMapper::toDto).toList();

        return ResponseEntity.ok(new PageResponse<>(
                result,
                new PageInfo(requestService.count(), pageNum, pageSize, result.size())));
    }

    @GetMapping("provider")
    @PreAuthorize("@authService.authInfo.hasRole('PROVIDER')")
    public ResponseEntity<PageResponse<RequestDto>> getAllByUser(
            @RequestParam(defaultValue = "1") @Min(1) int pageNum,
            @RequestParam(defaultValue = "10") @Min(1) @Max(250) int pageSize
    ) throws AuthException {

        var result = requestService.findByUserName(pageNum, pageSize, authService.getAuthInfo().getName())
                .stream()
                .map(requestMapper::toDto).toList();

        return ResponseEntity.ok(new PageResponse<>(
                result,
                new PageInfo(requestService.count(), pageNum, pageSize, result.size())));

    }
}
