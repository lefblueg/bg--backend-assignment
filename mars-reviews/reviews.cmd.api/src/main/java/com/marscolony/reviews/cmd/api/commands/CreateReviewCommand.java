package com.marscolony.reviews.cmd.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateReviewCommand {

    @TargetAggregateIdentifier
    private String id;
    @NotNull(message = "No userId was supplied")
    private String userId;
    private String description;
    @Min(value = 1, message = "Review must be at least 1 star")
    @Max(value = 5, message = "Review must be at most 5 stars")
    private int stars;

}
