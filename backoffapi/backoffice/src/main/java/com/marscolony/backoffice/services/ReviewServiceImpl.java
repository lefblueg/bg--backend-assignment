package com.marscolony.backoffice.services;

import com.marscolony.backoffice.dtos.ReviewDto;
import com.marscolony.backoffice.entities.Review;
import com.marscolony.backoffice.entities.Unit;
import com.marscolony.backoffice.exceptions.ResourceNotFoundException;
import com.marscolony.backoffice.exceptions.UnitAPIException;
import com.marscolony.backoffice.repositories.ReviewRepository;
import com.marscolony.backoffice.repositories.UnitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private UnitRepository unitRepository;
    private ModelMapper mapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UnitRepository unitRepository, ModelMapper mapper) {
        this.reviewRepository = reviewRepository;
        this.unitRepository = unitRepository;
        this.mapper = mapper;
    }

    @Override
    public ReviewDto createReview(Long unitId, ReviewDto reviewDto) {

        Review review = mapToEntity(reviewDto);
        Unit unit = unitRepository
                .findById(unitId)
                .orElseThrow(() -> new ResourceNotFoundException("Unit", "id", unitId));

        review.setUnit(unit);
        Review newReview = reviewRepository.save(review);
        return mapToDTO(newReview);

    }

    @Override
    public List<ReviewDto> getReviewsByUnitId(Long unitId) {

        List<Review> reviews = reviewRepository.findByUnitId(unitId);

        return reviews
                .stream()
                .map(review -> mapToDTO(review))
                .collect(Collectors.toList());

    }

    @Override
    public ReviewDto getReviewById(Long unitId, Long reviewId) {
        Unit unit = unitRepository
                .findById(unitId)
                .orElseThrow(() -> new ResourceNotFoundException("Unit", "id", unitId));

        Review review = reviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        if (!review.getUnit().getId().equals(unit.getId())) {
            throw new UnitAPIException(HttpStatus.BAD_REQUEST, "Review does not belong to unit");
        }

        return mapToDTO(review);
    }

    @Override
    public ReviewDto updateReview(Long unitId, Long reviewId, ReviewDto reviewDto) {
        Unit unit = unitRepository
                .findById(unitId)
                .orElseThrow(() -> new ResourceNotFoundException("Unit", "id", unitId));

        Review review = reviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        if (!review.getUnit().getId().equals(unit.getId())) {
            throw new UnitAPIException(HttpStatus.BAD_REQUEST, "Update review client error, Review does not belong to unit");
        }

        if (!reviewDto.getUserId().equals(review.getUserId())) {
            throw new UnitAPIException(HttpStatus.BAD_REQUEST, "Review does not belong to user");
        }

        review.setStars(reviewDto.getStars());
        review.setDescription(reviewDto.getDescription());

        var updatedReview = reviewRepository.save(review);

        return mapToDTO(updatedReview);

    }

    @Override
    public void deleteReview(Long unitId, Long reviewId, String username) {
        Unit unit = unitRepository
                .findById(unitId)
                .orElseThrow(() -> new ResourceNotFoundException("Unit", "id", unitId));

        Review review = reviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        if (!review.getUnit().getId().equals(unit.getId())) {
            throw new UnitAPIException(HttpStatus.BAD_REQUEST, "Delete review client error, Review does not belong to unit");
        }

        if (!review.getUserId().equals(username)) {
            throw new UnitAPIException(HttpStatus.BAD_REQUEST, "Review does not belong to user");
        }

        reviewRepository.delete(review);
    }

    private ReviewDto mapToDTO(Review review) {

        ReviewDto reviewDto = mapper.map(review, ReviewDto.class);

//        ReviewDto reviewDto = new ReviewDto();
//        reviewDto.setId(review.getId());
//        reviewDto.setUserId(review.getUserId());
//        reviewDto.setDescription(reviewDto.getDescription());
//        reviewDto.setStarts(review.getStarts());
        return reviewDto;

    }

    private Review mapToEntity(ReviewDto reviewDto) {

        Review review = mapper.map(reviewDto, Review.class);

//        Review review = new Review();
//        review.setId(reviewDto.getId());
//        review.setUserId(reviewDto.getUserId());
//        review.setDescription(reviewDto.getDescription());
//        review.setStarts(reviewDto.getStarts());
        return review;
    }

}
