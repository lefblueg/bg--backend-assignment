package com.marscolony.reviews.query.api.handlers;

import com.marscolony.reviews.core.api.events.ReviewCreatedEvent;
import com.marscolony.reviews.core.api.events.ReviewDeletedEvent;
import com.marscolony.reviews.core.api.events.ReviewUpdatedEvent;

public interface ReviewEventHandler {
    void on(ReviewCreatedEvent event);
    void on(ReviewUpdatedEvent event);
    void on(ReviewDeletedEvent event);
}
