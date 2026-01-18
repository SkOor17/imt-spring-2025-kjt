package org.imt.tournamentmaster.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.imt.tournamentmaster.model.match.Match.Status;
import org.imt.tournamentmaster.model.match.Round;
import org.imt.tournamentmaster.validation.DifferentTeams;

import java.util.List;

@DifferentTeams
public record MatchCreationDTO(
        @NotNull(message = "L'ID de l'équipe A est obligatoire")
        @Positive(message = "L'ID de l'équipe A doit être positif")
        Long equipeAId,

        @NotNull(message = "L'ID de l'équipe B est obligatoire")
        @Positive(message = "L'ID de l'équipe B doit être positif")
        Long equipeBId,

        @NotNull(message = "Le statut du match est obligatoire")
        Status status,

        @NotEmpty(message = "Le match doit avoir au moins un round")
        List<Round> rounds
) {
}
