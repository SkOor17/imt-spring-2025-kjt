package org.imt.tournamentmaster.model.equipe;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "equipe_joueur",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "joueur_id")
    )
    private List<Joueur> joueurs;

    public Equipe() {
    }

    public Equipe(Long id, String nom, List<Joueur> joueurs) {
        this.id = id;
        this.nom = nom;
        this.joueurs = joueurs;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "nom='" + nom + '\'' +
                ", joueurs=" + joueurs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Equipe equipe = (Equipe) o;
        return id == equipe.id && Objects.equals(nom, equipe.nom) && Objects.equals(joueurs, equipe.joueurs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, joueurs);
    }
}
