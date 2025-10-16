package co.edu.uniquindio.proyectofinalp2.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un envío dentro del sistema.
 * Cada envío tiene un remitente (usuario), un repartidor asignado,
 * información sobre la zona, precio, estado, tiempo de entrega y servicios adicionales.
 */
public class Shipment {

    private String shipmentId;             // Identificador único del envío
    private String senderId;               // ID del usuario que realiza el envío
    private Dealer assignedDealer;         // Repartidor asignado
    private String zone;                   // Zona de entrega
    private String status;                 // Estado actual: "Pendiente", "En Camino", "Entregado", etc.
    private double price;                  // Precio total del envío
    private double deliveryTimeHours;      // Tiempo que tardó en entregarse (en horas)
    private String period;                 // Periodo de entrega (por ejemplo "Octubre 2025")
    private String incident;               // Descripción de incidencias
    private List<String> additionalServices; // Servicios adicionales (ej: "Seguro", "Entrega exprés")

    public Shipment(String shipmentId, String senderId, Dealer assignedDealer,
                    String zone, double price, String period) {
        this.shipmentId = shipmentId;
        this.senderId = senderId;
        this.assignedDealer = assignedDealer;
        this.zone = zone;
        this.price = price;
        this.period = period;
        this.status = "Pendiente";
        this.additionalServices = new ArrayList<>();
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Dealer getAssignedDealer() {
        return assignedDealer;
    }

    public void setAssignedDealer(Dealer assignedDealer) {
        this.assignedDealer = assignedDealer;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDeliveryTimeHours() {
        return deliveryTimeHours;
    }

    public void setDeliveryTimeHours(double deliveryTimeHours) {
        this.deliveryTimeHours = deliveryTimeHours;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    public List<String> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<String> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public void addService(String service) {
        this.additionalServices.add(service);
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "ID='" + shipmentId + '\'' +
                ", Dealer=" + (assignedDealer != null ? assignedDealer.getFullname() : "No asignado") +
                ", Zone='" + zone + '\'' +
                ", Status='" + status + '\'' +
                ", Price=" + price +
                ", Time=" + deliveryTimeHours + "h" +
                '}';
    }
}
