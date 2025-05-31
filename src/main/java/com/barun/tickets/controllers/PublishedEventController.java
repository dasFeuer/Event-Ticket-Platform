package com.barun.tickets.controllers;

import com.barun.tickets.domain.dtos.GetPublishedEventDetailsResponseDto;
import com.barun.tickets.domain.dtos.ListPublishedEventResponseDto;
import com.barun.tickets.domain.enitities.Event;
import com.barun.tickets.mappers.EventMapper;
import com.barun.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            @RequestParam(required = false) String q,
            Pageable pageable){

        Page<Event> events;
        if(null != q && !q.trim().isEmpty()){
            events = eventService.searchPublishedEvent(q, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);
        }

       return ResponseEntity.ok(
                events.map(eventMapper::toListPublishedEventResponseDto)
       );
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEvents(
            @PathVariable UUID eventId
            ) {
        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
