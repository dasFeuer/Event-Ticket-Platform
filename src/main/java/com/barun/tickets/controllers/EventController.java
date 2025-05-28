package com.barun.tickets.controllers;

import com.barun.tickets.domain.CreateEventRequest;
import com.barun.tickets.domain.dtos.CreateEventRequestDto;
import com.barun.tickets.domain.dtos.CreateEventResponseDto;
import com.barun.tickets.domain.dtos.GetEventDetailsResponseDto;
import com.barun.tickets.domain.dtos.ListEventResponseDto;
import com.barun.tickets.domain.enitities.Event;
import com.barun.tickets.mappers.EventMapper;
import com.barun.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    private UUID parseUserId(Jwt jwt){
        return UUID.fromString(jwt.getSubject());
    }

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto
            ){
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID userId = parseUserId(jwt);
        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDto createdEventResponseDto = eventMapper.toDto(createdEvent);
        return new ResponseEntity<>(createdEventResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDto>> listEvents(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
            ){
        UUID userId = parseUserId(jwt);
        Page<Event> events = eventService.listEventsForOrganizer(userId, pageable);
       return ResponseEntity.ok(
               events.map(eventMapper::toListEventResponseDto)
       );

    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        UUID userId = parseUserId(jwt);
        return eventService.getEventForOrganizer(userId, eventId)
                .map(eventMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
