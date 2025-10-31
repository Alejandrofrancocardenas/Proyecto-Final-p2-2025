package co.edu.uniquindio.proyectofinalp2.Model;

import java.time.LocalDate;

public class Incidence {
    private String incidenceId;
    private String type;
    private String description;
    private LocalDate date;

    public Incidence(String incidenceId, String type, String description, LocalDate date) {
        this.incidenceId = incidenceId;
        this.type = type;
        this.description = description;
        this.date = date;
    }

    public String getIncidenceId() {
        return incidenceId;
    }

    public void setIncidenceId(String incidenceId) {
        this.incidenceId = incidenceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Incidence{" +
                "incidenceId='" + incidenceId + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
