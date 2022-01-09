package com.marscolony.bankacc.cmd.api.aggregates;

import com.marscolony.bankacc.cmd.api.commands.CloseAccountCommand;
import com.marscolony.bankacc.cmd.api.commands.DepositFundsCommand;
import com.marscolony.bankacc.cmd.api.commands.OpenAccountCommand;
import com.marscolony.bankacc.cmd.api.commands.WithdrawFundsCommand;
import com.marscolony.bankacc.core.api.events.AccountClosedEvent;
import com.marscolony.bankacc.core.api.events.AccountOpenedEvent;
import com.marscolony.bankacc.core.api.events.FundsDepositedEvent;
import com.marscolony.bankacc.core.api.events.FundsWithdrawnEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {

    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private double balance;

    @CommandHandler
    public AccountAggregate(OpenAccountCommand openAccountCommand) {
        var event = AccountOpenedEvent.builder()
                .id(openAccountCommand.getId())
                .accountHolderId(openAccountCommand.getAccountHolderId())
                .accountType(openAccountCommand.getAccountType())
                .creationDate(new Date())
                .openingBalance(openAccountCommand.getOpeningBalance())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountOpenedEvent event) {
        this.id = event.getId();
        this.accountHolderId = event.getAccountHolderId();
        this.balance = event.getOpeningBalance();
    }

    @CommandHandler
    public void handle(DepositFundsCommand command) {
        var amount = command.getAmount();
        var event = FundsDepositedEvent.builder()
                .id(command.getId())
                .amount(amount)
                .balance(this.balance + amount)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(FundsDepositedEvent event) {
        this.balance += event.getAmount();
    }


    @CommandHandler
    public void handle(CloseAccountCommand command) {

        var event = CloseAccountCommand.builder()
                .id(command.getId())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountClosedEvent event) {
        AggregateLifecycle.markDeleted();
    }


}
