package com.marscolony.bankacc.core.api.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountClosedEvent {
    private String id;
}
