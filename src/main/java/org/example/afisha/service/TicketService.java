package org.example.afisha.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.afisha.entity.Ticket;
import org.example.afisha.repository.EventRepository;
import org.example.afisha.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private static final long MUSEUM_ID = 1L;
    private static final long CINEMA_ID = 2L;
    private static final long THEATRE_ID = 3L;
    private final EventRepository eventRepository;

    public boolean buyTicket(String email, Long eventCategoryId) {
        if (eventCategoryId.equals(MUSEUM_ID)) {
            return sellMuseumTicket(email, eventCategoryId);
        } else if (eventCategoryId.equals(2L) || eventCategoryId.equals(3L)) {
            return sellCinemaOrTheatreTicket(email);
        }
        log.warn("not found eventCategoryId like in signature");
        return false;
    }

    private boolean sellMuseumTicket(String email, Long eventCategoryId) {
        Ticket ticket = createTicket(email, eventCategoryId);//создание билета
        ticketRepository.save(ticket);  //добавление его в базу данных
        Long ticketId = ticketRepository.findId();
        sellTicket(ticketId, email);
        log.info("Ticket has been purchased, event category museum");
        return true;
    }


    public Ticket createTicket(String email, Long eventCategoryId) {
        Long eventId = eventRepository.findEventIdByEventTypeId(eventCategoryId);
        return new Ticket(null, eventId, email, BigDecimal.valueOf(500), false);
    }

    private boolean sellCinemaOrTheatreTicket(String email) {
        return ticketRepository.findUnSoldTicket().map(unSoldTicket -> {
            sellTicket(unSoldTicket.getId(), email);
            log.info("Ticket {} has been purchased", unSoldTicket);
            return true;
        }).orElse(false);
    }

    private void sellTicket(Long ticketId, String email) {
        ticketRepository.updateColumnByName("is_sold", true, ticketId);
        ticketRepository.updateColumnByName("client_email", email, ticketId);
    }

    //создать событие с музеем
    //findEventIdByEventTypeId(Long eventType)
    //select id from application
}
