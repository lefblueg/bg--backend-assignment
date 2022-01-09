package com.marscolony.reviews.core.api.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewCreatedEvent {
    private String id;
    private String userId;
    private String description;
    private int stars;
}
