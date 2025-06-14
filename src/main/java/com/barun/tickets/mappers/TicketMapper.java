package com.barun.tickets.mappers;

import com.barun.tickets.domain.dtos.ListTicketResponseDto;
import com.barun.tickets.domain.dtos.ListTicketTicketTypeResponseDto;
import com.barun.tickets.domain.enitities.Ticket;
import com.barun.tickets.domain.enitities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);

    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);


}
