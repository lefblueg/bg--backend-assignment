package com.marscolony.reviews.cmd.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ModifyReviewCommand {

    @TargetAggregateIdentifier
    private String id;
    private String description;
    private int stars;
}
