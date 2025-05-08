package org.example.afisha.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private Long id;
    private Long eventId;
    private String clientEmail;
    private BigDecimal price;
    private Boolean isSold;
}
