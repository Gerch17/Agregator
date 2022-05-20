package ru.gerch.agregator.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PageInfo {
    private long totalValues;
    private long totalPages;
    private long pageNumber;
    private long pageSize;
    private long currentSize;

    public PageInfo(
            @Min(0) long totalValues,
            @Min(1) long pageNumber,
            @Min(1) long pageSize,
            long currentSize) {
        this.totalValues = totalValues;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.currentSize = currentSize;
        this.totalPages = totalValues / pageNumber;
        if (totalValues % pageSize != 0) {
            this.totalPages += 1;
        }
        this.currentSize = currentSize;
    }
}
