package com.devcv.review.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedReviewResponse {

    private List<ReviewDto> content;
    private Long totalElements;
    private int numberOfElements;
    private int currentPage;
    private int totalPages;
    private int size;
    private int startPage;
    private int endPage;

    private long totalReviews;
    private Double averageRating;

}