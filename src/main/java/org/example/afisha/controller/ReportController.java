package org.example.afisha.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.afisha.dto.ReportDtoResponse;
import org.example.afisha.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("report")
@RequiredArgsConstructor
@Tag(name = "Report", description = "метод для создания отчета")
public class ReportController {
    private final ReportService reportService;

    @PostMapping()
    @Operation(summary = "Создать отчет по проданным билетам")
    @ApiResponse(responseCode = "201", description = "Отчет успешно создан",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ReportDtoResponse.class)))
    public ResponseEntity<List<ReportDtoResponse>> createReport() {
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.createReport());
    }
}
