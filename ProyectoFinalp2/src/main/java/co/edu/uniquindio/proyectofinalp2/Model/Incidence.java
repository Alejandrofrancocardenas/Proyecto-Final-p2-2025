package co.edu.uniquindio.proyectofinalp2.Model;

import java.io.Serializable;
import java.time.LocalDate; // Usamos LocalDate para la fecha (sin hora)

public class Incidence implements Serializable {
    private static final long serialVersionUID = 1L;

    // --- Nuevas Propiedades Requeridas por el Controlador ---
    private String incidenceId; // Identificador único de la incidencia
    private String description;   // Reemplaza a 'report' para mayor claridad
    private LocalDate creationDate; // Fecha de creación (reemplaza a 'dateReported')
    private String type;           // Tipo de incidencia (ej: REPORTADO_ADMIN, DAÑO_PAQUETE)
    private String reporterId;     // ID del usuario/admin que la reportó
    // --- Fin Nuevas Propiedades ---


    // Constructor vacío (necesario para la serialización y para instanciar en el controlador)
    public Incidence() {
    }

    // Constructor de conveniencia (opcional, pero útil)
    public Incidence(String description, LocalDate creationDate, String type, String reporterId) {
        this.description = description;
        this.creationDate = creationDate;
        this.type = type;
        this.reporterId = reporterId;
    }

    // --- Getters y Setters para todas las propiedades ---

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

    // Antiguo método getReport, mantenido por compatibilidad si es necesario,
    // aunque ahora se recomienda usar getDescription()
    public String getReport() {
        return description;
    }
}