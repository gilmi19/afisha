package org.example.afisha.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.afisha.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
@Tag(name = "Ticket", description = "Методы по созданию билетов")
public class TicketController {
    private final TicketService ticketService;


    @PostMapping("/sell-ticket/{eventTypeId}")
    @Operation(summary = "Продать билет")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Билет был успешно продан")
    , @ApiResponse (responseCode = "406", description = "Не удалось продать билеты, " +
            "не удалось найти нужную категорию событий") })
    public ResponseEntity<Boolean> sellTicket(
            @Parameter(description = "Email пользователя", example = "igor123@gmail.com")
            @RequestParam String email,
            @Parameter(description = "Id типа события", example = "1 - музей")
            @PathVariable() Long eventTypeId) {
        return ticketService.buyTicket(email, eventTypeId) ?
                ResponseEntity.status(OK).build() :
                ResponseEntity.status(NOT_ACCEPTABLE).build();
    }
}


