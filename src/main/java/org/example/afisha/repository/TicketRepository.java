package org.example.afisha.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.afisha.entity.Ticket;
import org.example.afisha.exception.EntityNotExistException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TicketRepository implements CustomRepository<Ticket> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Ticket ticket) {
        String sql = "INSERT INTO application.ticket (event_id, price, is_sold) values (?, ?, ?);";
        jdbcTemplate.update(sql, ps -> {
            ps.setLong(1, ticket.getEventId());
            ps.setBigDecimal(2, ticket.getPrice());
            ps.setBoolean(3, ticket.getIsSold());
        });
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        try {
            String sql = "SELECT * FROM application.ticket WHERE id = ?";
            Ticket ticket = jdbcTemplate.queryForObject(sql, Ticket.class, id);
            return Optional.ofNullable(ticket);
        } catch (RuntimeException e) {
            throw new EntityNotExistException("Entity not exist");
        }
    }

    @Override
    public List<Ticket> findAll() {
        String sql = "SELECT * FROM application.ticket;";
        return jdbcTemplate.queryForList(sql, Ticket.class);
    }


    public Optional<Ticket> findUnSoldTicket() {
        String sql = """ 
                SELECT t.id, event_id, client_email, price, is_sold
                from application.ticket t
                         join application.event e on t.event_id = e.id
                where t.is_sold = false
                  and e.event_type_id in (2, 3)
                order by t.id desc
                limit 1;
                """;

        Ticket ticket = jdbcTemplate.query(sql, this::rsMapper).getFirst();
        log.info("ticket found {} ", ticket);
        return Optional.ofNullable(ticket);
    }

    public void updateColumnByName(String columnName, String value, Long ticketId) {
        String sql = "UPDATE application.ticket SET %s = ? WHERE id = ?".formatted(columnName);
        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, value);
            ps.setLong(2, ticketId);
        });
    }

    public void updateColumnByName(String columnName, boolean value, Long ticketId) {
        String sql = "UPDATE application.ticket SET %s = ? WHERE id = ?".formatted(columnName);
        jdbcTemplate.update(sql, ps -> {
            ps.setBoolean(1, value);
            ps.setLong(2, ticketId);
        });
    }

    public Long findId() {
        String sql = "select id from application.ticket order by id desc limit 1;";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Ticket rsMapper(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        long eventId = rs.getLong("event_id");
        String clientEmail = rs.getString("client_email");
        BigDecimal price = rs.getBigDecimal("price");
        boolean isSold = rs.getBoolean("is_sold");
        return new Ticket(id, eventId, clientEmail, price, isSold);
    }
}
