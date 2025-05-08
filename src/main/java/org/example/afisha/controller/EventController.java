package org.example.afisha.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.afisha.dto.EventDtoRequest;
import org.example.afisha.entity.Event;
import org.example.afisha.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/event")
@RequiredArgsConstructor
@Tag(name = "Event", description = "Методы для создания событий")
public class EventController {
    private final EventService eventService;

    @PostMapping()
    @Operation(summary = "Создать событие и 150 билетов")
    @ApiResponse(responseCode = "201", description = "Событие успешно создано")
    public ResponseEntity<Void> createEvent(@RequestBody EventDtoRequest request) {
        eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Просмотреть все события")
    @ApiResponse(responseCode = "200", description = "Список всех событий")
    public ResponseEntity<List<Event>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findAll());
    }
}
