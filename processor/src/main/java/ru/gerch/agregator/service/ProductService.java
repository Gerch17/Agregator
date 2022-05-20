package ru.gerch.agregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gerch.agregator.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
}
