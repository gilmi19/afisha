package org.example.afisha.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.afisha.dto.PlaceDtoRequest;
import org.example.afisha.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
@Tag(name = "Place", description = "Методы по созданию и просмотру мест проведения")
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "Создать событие")
    @ApiResponse(responseCode = "200", description = "Место проведения успешно создано")
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody PlaceDtoRequest dtoRequest) {
        placeService.createPlace(dtoRequest.name(), dtoRequest.address(), dtoRequest.city());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    @Operation(summary = "Просмотреть все места проведения")
    @ApiResponse(responseCode = "200", description = "Список всех мест проведений")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(placeService.findAll());
    }
}
