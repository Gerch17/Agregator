package ru.gerch.agregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gerch.agregator.entity.Product;
import ru.gerch.agregator.entity.Request;
import ru.gerch.agregator.enums.SortEnum;
import ru.gerch.agregator.mapper.ProductMapper;
import ru.gerch.agregator.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void createProductFromRequest(Request request) {
        Product product = ProductMapper.INSTANCE.fromRequest(request);
        productRepository.save(product);
    }

    public Page<Product> findAllByPageable(int pageNum, int pageSize, SortEnum sortEnum) {
        Pageable pageable;
        if (SortEnum.ASC.equals(sortEnum)) {
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("price"));
        } else if (SortEnum.DESC.equals(sortEnum)) {
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("price"));
        } else {
            pageable = PageRequest.of(pageNum - 1, pageSize);
        }
        return productRepository.findAll(pageable);
    }

    public long count() {
        return productRepository.count();
    }
}
