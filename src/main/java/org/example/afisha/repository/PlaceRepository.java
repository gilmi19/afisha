package org.example.afisha.repository;

import lombok.RequiredArgsConstructor;
import org.example.afisha.dto.PlaceDtoRequest;
import org.example.afisha.entity.Place;
import org.example.afisha.exception.EntityNotExistException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PlaceRepository implements CustomRepository<Place> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Place place) {
        String sql = "INSERT INTO application.place (name, address, city) values(?, ?, ?);";
        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, place.getName());
            ps.setString(2, place.getAddress());
            ps.setString(3, place.getCity());
        });
    }

    @Override
    public Optional<Place> findById(Long id) {
        try {
            String sql = "SELECT * FROM application.place WHERE id = ?";
            Place place = jdbcTemplate.queryForObject(sql, Place.class, id);
            return Optional.ofNullable(place);
        } catch (RuntimeException e) {
            throw new EntityNotExistException("Entity not exist");
        }
    }

    @Override
    public List<Place> findAll() {
        String sql = "SELECT * FROM application.place;";
        return jdbcTemplate.query(sql, ((rs, rowNum) -> queryMapper(rs)));
    }

    private Place queryMapper(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        String city = rs.getString("city");
        return new Place(id, name, address, city);
    }
}
