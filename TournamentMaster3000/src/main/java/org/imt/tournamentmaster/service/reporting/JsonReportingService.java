package org.imt.tournamentmaster.service.reporting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.model.reporting.ImportReport;
import org.imt.tournamentmaster.repository.equipe.EquipeRepository;
import org.imt.tournamentmaster.repository.match.MatchRepository;
import org.imt.tournamentmaster.repository.reporting.ImportReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class JsonReportingService implements ReportingService {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ImportReportRepository importReportRepository;

    @Autowired
    public JsonReportingService(ImportReportRepository importReportRepository) {

        this.importReportRepository = importReportRepository;
    }

    @Transactional(readOnly = true)
    public List<ImportReport> getAll() {
        return StreamSupport.stream(importReportRepository.findAll().spliterator(), false)
                .toList();
    }

    @Override
    public String report(Match match) throws IOException {
        return objectMapper.writeValueAsString(match);
    }
}
