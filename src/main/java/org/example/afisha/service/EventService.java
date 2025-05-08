package org.example.afisha.service;

import lombok.RequiredArgsConstructor;
import org.example.afisha.dto.EventDtoRequest;
import org.example.afisha.entity.Event;
import org.example.afisha.entity.Ticket;
import org.example.afisha.repository.EventRepository;
import org.example.afisha.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final TransactionTemplate transactionTemplate;

    //3.2. Создать сервис + контроллер, позволяющий создавать события и выдывать существующие события.
    // При создании события (передается цена, дата, имя события и место, тип события), должны генерироваться 100 билетов для кино,
    // 50 билетов для театра (email не заполнен, билет не продан).
    // Для музея билеты не генерятся. События + билеты должны создаваться одной транзакцией.
    public void createEvent(EventDtoRequest request) {
        Event event = Event.builder()
                .name(request.eventName())
                .eventTypeId(request.eventTypeId())
                .eventDate(request.eventDateTime())
                .placeId(request.placeId())
                .build();
        eventRepository.save(event);
        Long id = eventRepository.findId();

        transactionTemplate.executeWithoutResult((transactionStatus) -> {
            List<Ticket> theaterTickets = createSomeTickets(50, id, request.price());
            List<Ticket> cinemaTickets = createSomeTickets(100, id, request.price());
            eventRepository.save(event);
            theaterTickets.forEach(ticketRepository::save);
            cinemaTickets.forEach(ticketRepository::save);
        });
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    //event_id, client_email, price, is_sold
    private List<Ticket> createSomeTickets(int countTickets, Long eventId, BigDecimal price) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < countTickets; i++) {
            Ticket ticket = Ticket.builder()
                    .eventId(eventId)
                    .price(price)
                    .isSold(false)
                    .build();
            tickets.add(ticket);
        }
        return tickets;
    }
}
