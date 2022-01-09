package com.marscolony.reviews.core.api.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewUpdatedEvent {

    private String id;
    private String description;
    private int stars;

}
