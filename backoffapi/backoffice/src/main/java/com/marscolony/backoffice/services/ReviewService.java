package com.marscolony.backoffice.services;

import com.marscolony.backoffice.dtos.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto createReview(Long unitId, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByUnitId(Long unitId);
    ReviewDto getReviewById(Long unitId, Long reviewId);
    ReviewDto updateReview(Long unitId, Long reviewId, ReviewDto reviewDto);
    void deleteReview(Long unitId, Long reviewId, String user);

}
