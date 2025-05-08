package org.example.afisha.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PlaceDtoRequest(
        @Schema(description = "Имя места", example = "Большой зал")
        String name,
        @Schema(description = "Адрес места ", example = "Чертанова, 13")
        String address,
        @Schema(description = "Город места ", example = "Москва")
        String city) {
}
