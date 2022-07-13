package ru.gerch.agregator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gerch.agregator.dto.PageInfo;
import ru.gerch.agregator.dto.PageResponse;
import ru.gerch.agregator.dto.ProductDto;
import ru.gerch.agregator.enums.SortEnum;
import ru.gerch.agregator.mapper.ProductMapper;
import ru.gerch.agregator.service.ProductService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/")
    public ResponseEntity<PageResponse<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "1") @Min(1) int pageNum,
            @RequestParam(defaultValue = "10") @Min(1) @Max(250) int pageSize,
            @RequestParam(required = false) SortEnum sort
    ) {
        var result = productService.findAllByPageable(pageNum, pageSize, sort)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageResponse<>(
                result,
                new PageInfo(productService.count(), pageNum, pageSize, result.size())));
    }

    @PostMapping("/order/{id}")
    @PreAuthorize("@authService.authInfo.authenticated")
    public ResponseEntity<String> orderProduct(@PathVariable UUID id) {
        productService.orderProduct(id);
        return ResponseEntity.ok("Success");
    }

}
