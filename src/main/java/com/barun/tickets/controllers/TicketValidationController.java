package com.barun.tickets.controllers;

import com.barun.tickets.domain.dtos.TicketValidationRequestDto;
import com.barun.tickets.domain.dtos.TicketValidationResponseDto;
import com.barun.tickets.domain.enitities.TicketValidation;
import com.barun.tickets.domain.enitities.TicketValidationMethodEnum;
import com.barun.tickets.mappers.TicketValidationMapper;
import com.barun.tickets.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/ticket-validations")
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validationTicket(
            @RequestBody TicketValidationRequestDto ticketValidationRequestDto
            ) {
        TicketValidationMethodEnum method = ticketValidationRequestDto.getMethod();
        TicketValidation ticketValidation;
        if(TicketValidationMethodEnum.MANUAL.equals(method)){
            ticketValidation = ticketValidationService.validateTicketManually(
                    ticketValidationRequestDto.getId());
        } else {
            ticketValidation = ticketValidationService.validateTicketByQrCode(
                    ticketValidationRequestDto.getId()
            );
        }
        return ResponseEntity.ok(
                ticketValidationMapper.toTicketValidationResponseDto(ticketValidation)
        );
    }

}