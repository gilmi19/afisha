package org.example.afisha.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * id, name, event_type_id, event_date (дата + время), place_id
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    //id, name, event_type_id, event_date (дата + время), place_id+
    private Long id;
    private String name;
    private Long eventTypeId;
    private LocalDateTime eventDate;
    private Long placeId;
}
