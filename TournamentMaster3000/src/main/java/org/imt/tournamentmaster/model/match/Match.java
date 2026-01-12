package org.imt.tournamentmaster.model.match;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.imt.tournamentmaster.model.equipe.Equipe;

import java.util.List;
import java.util.Objects;

// Quand cette classe est transformée en entité, il faut spécifier le nom de la table en `match` en incluant les backquotes ` car match est un mot-clé réservé en SQL
@Entity
@Table(name = "`match`")
public class Match {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipea_id")
    private Equipe equipeA;

    @ManyToOne
    @JoinColumn(name = "equipeb_id")
    private Equipe equipeB;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "match_round",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "round_id")
    )
    private List<Round> rounds; // Set est un type de collection, on va éviter les confusions et appeler ça un "round"

    @Enumerated(EnumType.ORDINAL)  // Ajouté car status est un SMALLINT dans la DB
    @Column(name = "status")
    private Status status;

    public Match() {
    }

    public Match(Long id, Equipe equipeA, Equipe equipeB, List<Round> rounds, Status status) {  // long -> Long
        this.id = id;
        this.equipeA = equipeA;
        this.equipeB = equipeB;
        this.rounds = rounds;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Equipe getEquipeA() {
        return equipeA;
    }

    public Equipe getEquipeB() {
        return equipeB;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEquipeA(Equipe equipeA) {
        this.equipeA = equipeA;
    }

    public void setEquipeB(Equipe equipeB) {
        this.equipeB = equipeB;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Equipe determineWinner() {
        int wonByA = 0;
        int wonByB = 0;

        for (Round round : rounds) {
            // on assume qu'une égalité est impossible
            if (round.determineWinner() > 0) {
                wonByA++;
            } else {
                wonByB++;
            }
        }

        if (wonByA > wonByB) {
            return equipeA;
        } else {
            return equipeB;
        }
    }

    @Override
    public String toString() {
        return "Match{" +
                "equipeA=" + equipeA +
                ", equipeB=" + equipeB +
                ", rounds=" + rounds +
                ", status=" + status +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(id, match.id) && Objects.equals(equipeA, match.equipeA) && Objects.equals(equipeB, match.equipeB) && Objects.equals(rounds, match.rounds) && status == match.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, equipeA, equipeB, rounds, status);
    }

    public enum Status {
        NOUVEAU, EN_COURS, TERMINE
    }
}