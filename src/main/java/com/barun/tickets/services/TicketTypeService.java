package com.barun.tickets.services;

import com.barun.tickets.domain.enitities.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
