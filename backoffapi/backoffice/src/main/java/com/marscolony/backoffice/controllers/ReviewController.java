package com.marscolony.backoffice.controllers;

import com.marscolony.backoffice.dtos.ReviewDto;
import com.marscolony.backoffice.security.JwtTokenProvider;
import com.marscolony.backoffice.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviewController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/units/{unitId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "unitId") Long unitId,
                                                  @Valid @RequestBody ReviewDto reviewDto,
                                                  @RequestHeader (name="Authorization") String token) {


        String username = null;

        if (token != null && token.startsWith("Bearer")) {
            var jwt = token.substring(7);
            username = tokenProvider.getUserNameFromJwt(jwt);
            reviewDto.setUserId(username);
        }

        return new ResponseEntity<>(reviewService.createReview(unitId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/units/{unitId}/reviews")
    public List<ReviewDto> getReviewsByUnitId(@PathVariable(value = "unitId") Long unitId) {
        return reviewService.getReviewsByUnitId(unitId);
    }

    @GetMapping("/units/{unitId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "unitId") Long unitId,
                                                    @PathVariable(value = "reviewId") Long reviewId) {

        ReviewDto reviewDto = reviewService.getReviewById(unitId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/units/{unitId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> updateComment(@PathVariable(value = "unitId") Long unitId,
                                                   @PathVariable(value = "reviewId") Long reviewId,
                                                   @Valid @RequestBody ReviewDto reviewDto,
                                                   @RequestHeader (name="Authorization") String token) {

        String username = null;

        if (token != null && token.startsWith("Bearer")) {
            var jwt = token.substring(7);
            username = tokenProvider.getUserNameFromJwt(jwt);
            reviewDto.setUserId(username);
        }

        var updatedReviewDto = reviewService.updateReview(unitId, reviewId, reviewDto);
        return new ResponseEntity<>(updatedReviewDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/units/{unitId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "unitId") Long unitId,
                                               @PathVariable(value = "reviewId") Long reviewId,
                                               @RequestHeader (name="Authorization") String token) {

        String username = null;

        if (token != null && token.startsWith("Bearer")) {
            var jwt = token.substring(7);
            username = tokenProvider.getUserNameFromJwt(jwt);
        }

        reviewService.deleteReview(unitId, reviewId, username);
        return new ResponseEntity<>("Review deleted!", HttpStatus.OK);
    }
}
