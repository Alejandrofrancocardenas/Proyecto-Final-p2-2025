package co.edu.uniquindio.proyectofinalp2.Model;

import java.io.Serializable;
import java.time.LocalDate; // Usamos LocalDate para la fecha (sin hora)

public class Incidence implements Serializable {
    private static final long serialVersionUID = 1L;

    private String incidenceId;
    private String description;
    private LocalDate creationDate;
    private String type;
    private String reporterId;



    public Incidence() {
    }

    public Incidence(String description, LocalDate creationDate, String type, String reporterId) {
        this.description = description;
        this.creationDate = creationDate;
        this.type = type;
        this.reporterId = reporterId;
    }


    public String getIncidenceId() {
        return incidenceId;
    }

    public void setIncidenceId(String incidenceId) {
        this.incidenceId = incidenceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getReport() {
        return description;
    }
}