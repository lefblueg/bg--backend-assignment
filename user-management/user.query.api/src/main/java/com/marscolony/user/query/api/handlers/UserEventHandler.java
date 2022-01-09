package com.marscolony.user.query.api.handlers;

import com.marscolony.user.core.events.UserRegisteredEvent;
import com.marscolony.user.core.events.UserRemovedEvent;
import com.marscolony.user.core.events.UserUpdatedEvent;

public interface UserEventHandler {

    void on(UserRegisteredEvent event);
    void on(UserUpdatedEvent event);
    void on(UserRemovedEvent event);

}
