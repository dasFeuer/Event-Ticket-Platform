package com.barun.tickets.services.impl;

import com.barun.tickets.domain.enitities.Ticket;
import com.barun.tickets.domain.enitities.TicketStatusEnum;
import com.barun.tickets.domain.enitities.TicketType;
import com.barun.tickets.domain.enitities.User;
import com.barun.tickets.exceptions.TicketSoldOutException;
import com.barun.tickets.exceptions.TicketTypeNotFoundException;
import com.barun.tickets.exceptions.UserNotFoundException;
import com.barun.tickets.repositories.TicketRepository;
import com.barun.tickets.repositories.TicketTypeRepository;
import com.barun.tickets.repositories.UserRepository;
import com.barun.tickets.services.QrCodeService;
import com.barun.tickets.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID %s was not found", userId)
        ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(() -> new TicketTypeNotFoundException(
                String.format("Ticket type with ID %s was not found", ticketTypeId)
        ));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTickets + 1 > totalAvailable){
            throw new TicketSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

       return ticketRepository.save(savedTicket);
    }
}
