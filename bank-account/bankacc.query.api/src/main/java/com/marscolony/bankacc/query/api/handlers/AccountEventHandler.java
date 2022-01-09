package com.marscolony.bankacc.query.api.handlers;


import com.marscolony.bankacc.core.api.events.AccountClosedEvent;
import com.marscolony.bankacc.core.api.events.AccountOpenedEvent;
import com.marscolony.bankacc.core.api.events.FundsDepositedEvent;
import com.marscolony.bankacc.core.api.events.FundsWithdrawnEvent;

public interface AccountEventHandler {

    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);

}
