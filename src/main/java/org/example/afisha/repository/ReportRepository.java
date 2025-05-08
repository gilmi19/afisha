package org.example.afisha.repository;

import lombok.RequiredArgsConstructor;
import org.example.afisha.dto.ReportDtoResponse;
import org.example.afisha.exception.NoDataFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepository {
    private final JdbcTemplate jdbcTemplate;

    //3.4. Создать сервис + контроллер, позволяющий получать отчет в разрезе: имя мероприятия, тип мероприятия,
// количество проданн   ых билетов, сумма проданных билетов. Если по мероприятию не было продаж,
    // то отображать для этого мероприятия - нулевое количество и сумму (ни одно мероприятие не должно пропасть).
    public List<ReportDtoResponse> createReport() {
        String sql = """
                SELECT ev.name          as event_name,
                       et.name          as event_type_name,
                       count(t.is_sold) as count_sold_tickets,
                       sum(t.price)     as sum_sold_tickets
                from application.event ev
                         join application.ticket t on ev.id = t.event_id
                         join application.event_type et on et.id = ev.event_type_id
                group by ev.name, et.name;
                """;
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                return new ReportDtoResponse(
                        (rs.getString("event_name")),
                        (rs.getString("event_type_name")),
                        (rs.getBigDecimal("count_sold_tickets")),
                        (rs.getBigDecimal("sum_sold_tickets")));
            });
        } catch (NoDataFoundException e) {
            return List.of(new ReportDtoResponse(
                    null,
                    null,
                    null,
                    null));
        }
    }


}
