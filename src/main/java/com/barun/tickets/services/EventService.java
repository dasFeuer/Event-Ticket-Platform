package com.barun.tickets.services;

import com.barun.tickets.domain.CreateEventRequest;
import com.barun.tickets.domain.enitities.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
}
