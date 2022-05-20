package ru.gerch.agregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

    public void createRequest(RequestDto requestDto) {
        Request request = RequestMapper.INSTANCE.create(requestDto, userService);
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

    public void updateRequestStatus(UUID id, EnumStatus status) {
        Optional<Request> request = requestRepository.findById(id);
        if (request.isPresent()) {
            Request newRequest = request.get();
            newRequest.setStatus(status);
            requestRepository.save(newRequest);
        }
        throw new RuntimeException("No such request");
    }
}
