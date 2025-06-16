package com.barun.tickets.mappers;

import com.barun.tickets.domain.dtos.TicketValidationResponseDto;
import com.barun.tickets.domain.enitities.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(target = "ticketId", source = "ticket.id")
    TicketValidationResponseDto toTicketValidationResponseDto (TicketValidation ticketValidation);

}
