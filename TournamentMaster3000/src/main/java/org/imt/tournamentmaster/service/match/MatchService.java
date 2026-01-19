package org.imt.tournamentmaster.service.match;

import org.imt.tournamentmaster.controller.compte.CompteController;
import org.imt.tournamentmaster.dto.MatchCreationDTO;
import org.imt.tournamentmaster.model.equipe.Equipe;
import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.model.reporting.ImportReport;
import org.imt.tournamentmaster.repository.equipe.EquipeRepository;
import org.imt.tournamentmaster.repository.match.MatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final EquipeRepository equipeRepository;
    private final org.imt.tournamentmaster.repository.reporting.ImportReportRepository importReportRepository;
    Logger logger = LoggerFactory.getLogger(CompteController.class);

    @Autowired
    public MatchService(MatchRepository matchRepository, EquipeRepository equipeRepository, org.imt.tournamentmaster.repository.reporting.ImportReportRepository importReportRepository) {

        this.matchRepository = matchRepository;
        this.equipeRepository = equipeRepository;
        this.importReportRepository = importReportRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Match> getById(long id) {
        return matchRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Match> getAll() {
        return StreamSupport.stream(matchRepository.findAll().spliterator(), false)
                .toList();
    }

    @Transactional
    public Match addMatch(MatchCreationDTO matchDto) {
        Match match = new Match();
        match.setStatus(matchDto.status());
        match.setRounds(matchDto.rounds());

        if (matchDto.equipeAId() != null) {
            Equipe equipeA = equipeRepository.findById(matchDto.equipeAId())
                    .orElseThrow(() -> new RuntimeException("Equipe A non trouvée"));
            match.setEquipeA(equipeA);
        }

        if (matchDto.equipeBId() != null) {
            Equipe equipeB = equipeRepository.findById(matchDto.equipeBId())
                    .orElseThrow(() -> new RuntimeException("Equipe B non trouvée"));
            match.setEquipeB(equipeB);
        }

        logger.info(String.format("Match %d : %s vs %s", match.getId(), match.getEquipeA().getNom(), match.getEquipeB().getNom()));
        return matchRepository.save(match);
    }

    @Transactional
    public ImportReport bulkAddMatches(List<MatchCreationDTO> dtos) {
        // 1. Pré-chargement des équipes
        Set<Long> equipeIds = dtos.stream()
                .flatMap(dto -> Stream.of(dto.equipeAId(), dto.equipeBId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<Equipe> equipes = (List<Equipe>) equipeRepository.findAllById(equipeIds);
        Map<Long, Equipe> equipesMap = equipes.stream()
                .collect(Collectors.toMap(Equipe::getId, e -> e));

        // 2. Traitement avec vérification doublon individuelle
        List<Match> matchsToSave = new ArrayList<>();
        List<ImportError> errors = new ArrayList<>();

        for (int i = 0; i < dtos.size(); i++) {
            MatchCreationDTO dto = dtos.get(i);
            try {
                Equipe equipeA = equipesMap.get(dto.equipeAId());
                Equipe equipeB = equipesMap.get(dto.equipeBId());

                // Validation équipes existent
                if (equipeA == null || equipeB == null) {
                    errors.add(new ImportError(i + 1, dto, "Équipe(s) introuvable(s)"));
                    continue;
                }

                // Vérification doublon
                boolean exists = matchRepository.existsByEquipeAAndEquipeB(equipeA, equipeB);

                if (exists) {
                    errors.add(new ImportError(i + 1, dto, "Match déjà existant"));
                    continue;
                }

                // Création du match
                Match match = createMatch(dto, equipesMap);
                matchsToSave.add(match);
                logger.info(String.format("Match %d : %s vs %s préparé pour l'ajout en masse", match.getId(), match.getEquipeA().getNom(), match.getEquipeB().getNom()));

            } catch (Exception e) {
                errors.add(new ImportError(i + 1, dto, e.getMessage()));
            }
        }

        // 3. Sauvegarde
        if (!matchsToSave.isEmpty()) {
            logger.info("Sauvegarde des matchs ajouté en masse");
            matchRepository.saveAll(matchsToSave);
        }

        // 4. Rapport
        ImportReport report = new ImportReport();
        report.setSuccessCount(matchsToSave.size());
        report.setFailureCount(errors.size());
        report.setErrors(serializeErrors(errors));
        report.setImportDate(LocalDateTime.now());
        logger.info(String.format("Résultat  de l'ajout en masse : succès=%d échec=%d", matchsToSave.size(), errors.size()));

        return importReportRepository.save(report);
    }

    private Match createMatch(MatchCreationDTO dto, Map<Long, Equipe> equipesMap) {
        Match match = new Match();
        match.setEquipeA(equipesMap.get(dto.equipeAId()));
        match.setEquipeB(equipesMap.get(dto.equipeBId()));
        match.setStatus(Match.Status.NOUVEAU);
        match.setRounds(dto.rounds());
        return match;
    }

    private String serializeErrors(List<ImportError> errors) {
        if (errors.isEmpty()) return null;

        return errors.stream()
                .map(e -> String.format("Ligne %d [Équipe A: %d, Équipe B: %d] : %s",
                        e.lineNumber(),
                        e.dto().equipeAId(),
                        e.dto().equipeBId(),
                        e.error()
                ))
                .collect(Collectors.joining("\n"));
    }

    private record ImportError(int lineNumber, MatchCreationDTO dto, String error) {}
}
