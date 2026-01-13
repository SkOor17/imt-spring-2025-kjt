package org.imt.tournamentmaster.service.match;

import org.imt.tournamentmaster.dto.MatchCreationDTO;
import org.imt.tournamentmaster.model.equipe.Equipe;
import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.repository.equipe.EquipeRepository;
import org.imt.tournamentmaster.repository.match.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final EquipeRepository equipeRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, EquipeRepository equipeRepository) {

        this.matchRepository = matchRepository;
        this.equipeRepository = equipeRepository;
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

        return matchRepository.save(match);
    }
}
