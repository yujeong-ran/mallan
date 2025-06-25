package com.mallan.yujeongran.common.model;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PagedResponse<T> {

    private final List<T> content;
    private final int page;
    private final int size;
    private final int totalPages;
    private final Long totalElements;
    private final boolean isFirst;
    private final boolean isLast;

    public PagedResponse(Page<T> pageData) {
        this.content = pageData.getContent();
        this.page = pageData.getNumber();
        this.size = pageData.getSize();
        this.totalPages = pageData.getTotalPages();
        this.totalElements = pageData.getTotalElements();
        this.isFirst = pageData.isFirst();
        this.isLast = pageData.isLast();
    }

}
