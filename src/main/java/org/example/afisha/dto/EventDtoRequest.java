package org.example.afisha.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventDtoRequest(
        @Schema(description = "Стоимость билета", example = "900")
        BigDecimal price,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        @Schema(description = "Дата события", example = "2019-11-04 18:00")
        LocalDateTime eventDateTime,

        @Schema(description = "Имя события", example = "Eseninsky concert")
        String eventName,

        @Schema(description = "ID места проведения", example = "1")
        Long placeId,
        @Schema(description = "ID типа события", example = "2")
        Long eventTypeId) {
}
