package com.marscolony.reviews.core.api.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDeletedEvent {
    private String id;
}
