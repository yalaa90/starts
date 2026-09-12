package com.stars.domain.events;

import java.time.Instant;
import java.util.UUID;

public record UserCreatedEvent(
        UUID eventId,
        UUID userId,
        String email,
        String fullName,
        Instant timestamp
        ) {

}
