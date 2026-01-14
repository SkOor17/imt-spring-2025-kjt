package org.imt.tournamentmaster.model.reporting;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ImportReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private int successCount;

    private int failureCount;

    // Pour simplifier, on stocke juste le nom de l'équipe gagnante la plus fréquente de ce lot, ou autre stat
    private String topWinner; 

    public ImportReport() {
        this.date = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public String getTopWinner() {
        return topWinner;
    }

    public void setTopWinner(String topWinner) {
        this.topWinner = topWinner;
    }
}
