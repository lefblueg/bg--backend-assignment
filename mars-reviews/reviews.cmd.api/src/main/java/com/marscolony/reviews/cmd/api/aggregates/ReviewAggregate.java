package com.marscolony.reviews.cmd.api.aggregates;

import com.marscolony.reviews.cmd.api.commands.CreateReviewCommand;
import com.marscolony.reviews.cmd.api.commands.DeleteReviewCommand;
import com.marscolony.reviews.cmd.api.commands.ModifyReviewCommand;
import com.marscolony.reviews.core.api.events.ReviewCreatedEvent;
import com.marscolony.reviews.core.api.events.ReviewDeletedEvent;
import com.marscolony.reviews.core.api.events.ReviewUpdatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class ReviewAggregate {

    @AggregateIdentifier
    private String id;
    private String userId;
    private String description;
    private int stars;

    @CommandHandler
    public ReviewAggregate(CreateReviewCommand command) {

        //create the new event from the elements of the new command
        var event = ReviewCreatedEvent.builder()
                .id(command.getId())
                .userId(command.getUserId())
                .description(command.getDescription())
                .stars(command.getStars())
                .build();

        //publish the event to the event bus
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ReviewCreatedEvent event) {
        this.id = event.getId();
        this.userId = event.getUserId();
        this.description = event.getDescription();
        this.stars = event.getStars();
    }

    @CommandHandler
    public void handle(ModifyReviewCommand command) {

        var event = ReviewUpdatedEvent.builder()
                .id(command.getId())
                .description(command.getDescription())
                .stars(command.getStars())
                .build();

        AggregateLifecycle.apply(event);

    }

    @EventSourcingHandler
    public void on(ReviewUpdatedEvent event) {
        this.description = event.getDescription();
        this.stars = event.getStars();
    }

    @CommandHandler
    public void handle(DeleteReviewCommand command) {

        var event = ReviewDeletedEvent.builder()
                .id(command.getId())
                .build();

        AggregateLifecycle.apply(event);

    }

    @EventSourcingHandler
    public void on(ReviewDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }


}
