package com.barun.tickets.mappers;

import com.barun.tickets.domain.CreateEventRequest;
import com.barun.tickets.domain.CreateTicketTypeRequest;
import com.barun.tickets.domain.dtos.CreateEventRequestDto;
import com.barun.tickets.domain.dtos.CreateEventResponseDto;
import com.barun.tickets.domain.dtos.CreateTicketTypeRequestDto;
import com.barun.tickets.domain.enitities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);
}
