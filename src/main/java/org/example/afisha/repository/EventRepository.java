package org.example.afisha.repository;

import lombok.RequiredArgsConstructor;
import org.example.afisha.entity.Event;
import org.example.afisha.exception.EntityNotExistException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventRepository implements CustomRepository<Event> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Event event) {
        String sql = "INSERT INTO application.event (name, event_type_id, event_date, place_id) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, event.getName());
            ps.setLong(2, event.getEventTypeId());
            ps.setTimestamp(3, Timestamp.valueOf(event.getEventDate()));
            ps.setLong(4, event.getPlaceId());
        });
    }

    @Override
    public Optional<Event> findById(Long id) {
        try {
            String sql = "SELECT * FROM application.event WHERE id = ?";
            Event event = jdbcTemplate.queryForObject(sql, Event.class, id);
            return Optional.ofNullable(event);
        } catch (RuntimeException e) {
            throw new EntityNotExistException("Entity not exist");
        }
    }

    //Long id;
    //    private String name;
    //    private Long eventTypeId;
    //    private LocalDateTime eventDate;
    //    private Long placeId;
    @Override
    public List<Event> findAll() {
        String sql = "SELECT * FROM application.event;";
        try {
            return jdbcTemplate.query(sql, this::eventRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public Long findId() {
        String sql = "select id from application.event order by id desc limit 1;";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Long findEventIdByEventTypeId(Long eventType) {
        String sql = """
                select id
                from application.event
                where event_type_id = ?
                order by id desc
                limit 1;
                """;
        return jdbcTemplate.queryForObject(sql, Long.class, eventType);
    }


    private Event eventRowMapper(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        long eventTypeId = rs.getLong("event_type_id");
        LocalDateTime eventDate = rs.getTimestamp("event_date").toLocalDateTime();
        long placeId = rs.getLong("place_id");
        return new Event(id, name, eventTypeId, eventDate, placeId);
    }

}

