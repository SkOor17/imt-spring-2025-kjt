package org.imt.tournamentmaster.model.match;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Round {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int scoreA;

    private int scoreB;

    private int roundNumber;

    public Round() {
    }

    public Round(Long id, int scoreA, int scoreB, int roundNumber) {
        this.id = id;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.roundNumber = roundNumber;
    }

    public Long getId() {
        return id;
    }

    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }

    public void setScoreB(int scoreB) {
        this.scoreB = scoreB;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int determineWinner() {
        return scoreA - scoreB;
    }

    @Override
    public String toString() {
        return "Round{" +
                ", scoreA=" + scoreA +
                ", scoreB=" + scoreB +
                ", roundNumber=" + roundNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return id == round.id && scoreA == round.scoreA && scoreB == round.scoreB && roundNumber == round.roundNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scoreA, scoreB, roundNumber);
    }
}
