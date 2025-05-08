package org.example.afisha.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Builder

public record ReportDtoResponse(
        @Schema(description = "Имя события", example = "Мстители")
        String eventName,
        @Schema(description = "Имя типа события", example = "Фильм")
        String eventTypeName,
        @Schema(description = "Количество проданных билетов", example = "150")
        BigDecimal countSoldTickets,
        @Schema(description = "Сумма проданных билетов", example = "100")
        BigDecimal sumSoldTickets
) {
}
