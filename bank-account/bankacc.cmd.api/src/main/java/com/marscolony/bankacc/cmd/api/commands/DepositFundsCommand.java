package com.marscolony.bankacc.cmd.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;

@Data
@Builder
public class DepositFundsCommand {
    @TargetAggregateIdentifier
    private String id;
    @Min(value = 1, message = "the deposit amount must be greater than zero")
    private double amount;
}
