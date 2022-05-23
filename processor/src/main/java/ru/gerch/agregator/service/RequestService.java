package ru.gerch.agregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gerch.agregator.dto.RequestDto;
import ru.gerch.agregator.entity.Request;
import ru.gerch.agregator.entity.User;
import ru.gerch.agregator.enums.EnumStatus;
import ru.gerch.agregator.mapper.RequestMapper;
import ru.gerch.agregator.repository.RequestRepository;

import javax.security.auth.message.AuthException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;
    private final AuthService authService;
    private final ProductService productService;

    public void createRequest(RequestDto requestDto) {
        Request request = RequestMapper.INSTANCE.create(requestDto);
        request.setUser(userService.getByLogin(authService.getAuthInfo().getUsername()).get());
        requestRepository.save(request);
    }

    public Page<Request> findAllByPageable(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return requestRepository.findAll(pageable);
    }

    public long count() {
        return requestRepository.count();
    }

    public Page<Request> findByUserName(int pageNum, int pageSize, String userName) throws AuthException {
        Optional<User> user = userService.getByLogin(userName);
        if (user.isPresent()) {
            Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
            return requestRepository.findRequestsByUser(user.get(), pageable);
        }
        throw new AuthException("Cannot find user " + userName);
    }

    @Transactional
    public ResponseEntity<String> updateRequestStatus(UUID id, EnumStatus status) {
        Optional<Request> request = requestRepository.findById(id);
        if (request.isPresent()) {
            Request newRequest = request.get();
            if (newRequest.getStatus().equals(EnumStatus.REVIEW)) {
                newRequest.setStatus(status);
                requestRepository.save(newRequest);
                if (newRequest.getStatus().equals(EnumStatus.APPROVED)) {
                    productService.createProductFromRequest(newRequest);
                }
                return ResponseEntity.ok("Status has been changed");
            }
            return ResponseEntity.ok("You cant change status on this request");
        }
        throw new RuntimeException("No such request");
    }
}
