package org.example.afisha.repository;

import lombok.RequiredArgsConstructor;
import org.example.afisha.entity.EventType;
import org.example.afisha.exception.EntityNotExistException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventTypeRepository implements CustomRepository<EventType> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(EventType eventType) {
        String sql = "INSERT INTO application.event_type (name) values(?)";
        jdbcTemplate.update(sql, ps -> ps.setString(1, eventType.getName()));
    }

    @Override
    public Optional<EventType> findById(Long id) {
        try {
            String sql = "SELECT * FROM application.event_type WHERE id = ?";
            EventType eventType = jdbcTemplate.queryForObject(sql, EventType.class, id);
            return Optional.ofNullable(eventType);
        } catch (RuntimeException e) {
            throw new EntityNotExistException("Entity not exist");
        }
    }

    @Override
    public List<EventType> findAll() {
        String sql = "SELECT * FROM application.event_type";
        return jdbcTemplate.queryForList(sql, EventType.class);
    }
}
