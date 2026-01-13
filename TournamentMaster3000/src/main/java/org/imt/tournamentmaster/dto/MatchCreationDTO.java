package org.imt.tournamentmaster.dto;

import org.imt.tournamentmaster.model.match.Match.Status;
import org.imt.tournamentmaster.model.match.Round;

import java.util.List;

public record MatchCreationDTO(Long equipeAId, Long equipeBId, Status status, List<Round> rounds) {
}
