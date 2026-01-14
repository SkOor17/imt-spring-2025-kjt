package org.imt.tournamentmaster.repository.match;

import org.imt.tournamentmaster.model.equipe.Equipe;
import org.imt.tournamentmaster.model.match.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {
    
    // Vérifie si un match existe déjà entre ces deux équipes avec ce statut spécifique (ou on pourrait chercher statut != TERMINE)
    boolean existsByEquipeAAndEquipeBAndStatusNot(Equipe equipeA, Equipe equipeB, Match.Status status);
}
