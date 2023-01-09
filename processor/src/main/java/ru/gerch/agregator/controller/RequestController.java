package ru.gerch.agregator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gerch.agregator.dto.ChangeStatusDto;
import ru.gerch.agregator.dto.PageInfo;
import ru.gerch.agregator.dto.PageResponse;
import ru.gerch.agregator.dto.RequestDto;
import ru.gerch.agregator.mapper.RequestMapper;
import ru.gerch.agregator.service.AuthService;
import ru.gerch.agregator.service.RequestService;

import javax.security.auth.message.AuthException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("api/v1/request")
@RestController
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final RequestMapper requestMapper;
    private final AuthService authService;

    @PostMapping("status/change")
    @PreAuthorize("@authService.authInfo.hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestBody ChangeStatusDto statusDto) {
        return requestService.updateRequestStatus(statusDto.getId(), statusDto.getStatus());
    }

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
                .map(requestMapper::toDto).collect(Collectors.toList());

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
                .map(requestMapper::toDto).collect(Collectors.toList());

        return ResponseEntity.ok(new PageResponse<>(
                result,
                new PageInfo(requestService.count(), pageNum, pageSize, result.size())));

    }
}
