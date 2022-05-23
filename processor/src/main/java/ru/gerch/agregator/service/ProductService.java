package ru.gerch.agregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gerch.agregator.entity.Product;
import ru.gerch.agregator.entity.Request;
import ru.gerch.agregator.entity.User;
import ru.gerch.agregator.enums.SortEnum;
import ru.gerch.agregator.mapper.ProductMapper;
import ru.gerch.agregator.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final AuthService authService;
    private final HistoryService historyService;

    public void createProductFromRequest(Request request) {
        Product product = ProductMapper.INSTANCE.fromRequest(request);
        productRepository.save(product);
    }

    public Page<Product> findAllByPageable(int pageNum, int pageSize, SortEnum sortEnum) {
        Pageable pageable;
        if (SortEnum.ASC.equals(sortEnum)) {
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("price"));
        } else if (SortEnum.DESC.equals(sortEnum)) {
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("price").descending());
        } else {
            pageable = PageRequest.of(pageNum - 1, pageSize);
        }
        return productRepository.findProductsBySoldIsFalse(pageable);
    }

    @Transactional
    public void orderProduct(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            if (!product.get().isSold()) {
                Optional<User> user = userService.getByLogin(authService.getAuthInfo().getUsername());
                Product soldProduct = product.get();
                historyService.historyFromRequest(soldProduct, user.get());
                soldProduct.setSold(true);
                productRepository.save(soldProduct);
                return;
            }
            throw new RuntimeException("product already sold");
        }
        throw new RuntimeException("no such product");
    }

    public long count() {
        return productRepository.count();
    }
}
