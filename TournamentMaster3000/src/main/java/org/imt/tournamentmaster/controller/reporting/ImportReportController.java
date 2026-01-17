package org.imt.tournamentmaster.controller.reporting;

import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.model.reporting.ImportReport;
import org.imt.tournamentmaster.service.match.MatchService;
import org.imt.tournamentmaster.service.reporting.JsonReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/report")
public class ImportReportController {
    private final JsonReportingService reportService;

    @Autowired
    public ImportReportController(JsonReportingService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<ImportReport> getAll() {
        return reportService.getAll();
    }
}
