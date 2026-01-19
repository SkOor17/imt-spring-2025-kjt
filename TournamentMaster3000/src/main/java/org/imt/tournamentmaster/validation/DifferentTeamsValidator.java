package org.imt.tournamentmaster.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.imt.tournamentmaster.dto.MatchCreationDTO;

public class DifferentTeamsValidator implements ConstraintValidator<DifferentTeams, MatchCreationDTO> {

    @Override
    public boolean isValid(MatchCreationDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        if (dto.equipeAId() == null || dto.equipeBId() == null) {
            return true;
        }

        boolean isValid = !dto.equipeAId().equals(dto.equipeBId());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("equipeBId")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
