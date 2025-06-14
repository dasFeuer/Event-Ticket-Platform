package com.barun.tickets.controllers;

import com.barun.tickets.domain.dtos.GetTicketResponseDto;
import com.barun.tickets.domain.dtos.ListTicketResponseDto;
import com.barun.tickets.mappers.TicketMapper;
import com.barun.tickets.services.QrCodeService;
import com.barun.tickets.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.barun.tickets.util.JwtUtil.parseUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final QrCodeService qrCodeService;

    @GetMapping
    public Page<ListTicketResponseDto> listTickets(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ){
        return ticketService.listTicketsForUser(
                parseUserId(jwt),
                pageable
        ).map(ticketMapper::toListTicketResponseDto);
    }

    @GetMapping(path = "/{ticketId}")
    public ResponseEntity<GetTicketResponseDto> getTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
            ){
        return ticketService
                .getTicketForUser(parseUserId(jwt), ticketId)
                .map(ticketMapper::toGetTicketResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQrCode(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
            ){
        byte[] qrCodeImage = qrCodeService.getQrCodeImageForUserAndTicket(
                parseUserId(jwt),
                ticketId
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(qrCodeImage.length);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(qrCodeImage);
    }



}
