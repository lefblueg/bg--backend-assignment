package com.marscolony.reviews.query.api.handlers;

import com.marscolony.reviews.core.api.events.ReviewCreatedEvent;
import com.marscolony.reviews.core.api.events.ReviewDeletedEvent;
import com.marscolony.reviews.core.api.events.ReviewUpdatedEvent;
import com.marscolony.reviews.core.api.models.Review;
import com.marscolony.reviews.query.api.repositories.ReviewRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("review-group")
public class ReviewEventHandlerImpl implements ReviewEventHandler {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewEventHandlerImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @EventHandler
    @Override
    public void on(ReviewCreatedEvent event) {
        var reviewRecord = Review.builder()
                .id(event.getId())
                .description(event.getDescription())
                .stars(event.getStars())
                .userId(event.getUserId())
                .build();

        reviewRepository.save(reviewRecord);
    }

    @EventHandler
    @Override
    public void on(ReviewUpdatedEvent event) {

        var reviewRecord = reviewRepository.findById(event.getId());
        if (reviewRecord.isEmpty()) {
            return;
        }

        reviewRecord.get().setDescription(event.getDescription());
        reviewRecord.get().setStars(event.getStars());

        reviewRepository.save(reviewRecord.get());

    }

    @EventHandler
    @Override
    public void on(ReviewDeletedEvent event) {
        reviewRepository.deleteById(event.getId());
    }
}
