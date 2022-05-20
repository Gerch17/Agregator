package ru.gerch.agregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> value;
    private PageInfo pageInfo;

    public void add(T value) {
        if(!getValue().isEmpty()) {
            this.value = new ArrayList<>();
        }
        this.value.add(value);
        if (pageInfo != null) {
            pageInfo = new PageInfo(pageInfo.getTotalValues() + 1,
                    pageInfo.getPageNumber(),
                    Math.max(this.value.size(), pageInfo.getPageSize()),
                    this.value.size());

        }
    }
}
