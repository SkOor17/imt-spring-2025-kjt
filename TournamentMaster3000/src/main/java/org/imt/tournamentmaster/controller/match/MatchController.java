package org.imt.tournamentmaster.controller.match;

import org.imt.tournamentmaster.dto.MatchCreationDTO;
import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.model.reporting.ImportReport;
import org.imt.tournamentmaster.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getById(@PathVariable long id) {
        Optional<Match> match = matchService.getById(id);

        return match.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Match> getAll() {
        return matchService.getAll();
    }

    @PostMapping
    public Match addMatch(@Valid @RequestBody MatchCreationDTO matchDto) { return matchService.addMatch(matchDto); }

    @PostMapping("/bulk")
    public ImportReport bulkAddMatches(@Valid @RequestBody List<MatchCreationDTO> dtos) {
        return matchService.bulkAddMatches(dtos);
    }
}
