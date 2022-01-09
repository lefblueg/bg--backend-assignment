package com.marscolony.reviews.cmd.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DeleteReviewCommand {
    @TargetAggregateIdentifier
    private String id;
}
