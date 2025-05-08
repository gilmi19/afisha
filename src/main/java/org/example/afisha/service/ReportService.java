package org.example.afisha.service;

import lombok.RequiredArgsConstructor;
import org.example.afisha.dto.ReportDtoResponse;
import org.example.afisha.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public List<ReportDtoResponse> createReport() {
        return reportRepository.createReport();
    }
}
