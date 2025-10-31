package co.edu.uniquindio.proyectofinalp2.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un envío dentro del sistema.
 * Cada envío tiene un remitente (usuario), un repartidor asignado,
 * información sobre la zona, precio, estado, tiempo de entrega y servicios adicionales.
 */
public class Shipment implements ITracker {

    private String shipmentId;               // Identificador único del envío
    private Rate rate;                       // la tarifa
    private Incidence incidence;             // piuede tener incidencias
    private Payment payment;                 // el pago que nesecita el envio para ser enviado
    private Address address;                 // aqui va el origen y el destino
    private PackageModel packageModel;       // el envio nesecita tener un paqwuete para enviar
    private double price;                    // Precio total del envío
    private ShippingStatus status;           // Estado actual: "Pendiente", "En Camino", "Entregado", etc.
    private LocalDateTime crationDate;       // fecha cuando se crea
    private double estimatedDeliveryDate;    // Tiempo que tardó en entregarse (en horas)
    private User user;                       // el usuario que realiza el envío
    private Dealer assignedDealer;           // Repartidor asignado
    private String zone;                     // Zona de entrega
    private String period;                   // Periodo de entrega (por ejemplo "Octubre 2025")
    private List<String> additionalServices; // Servicios adicionales (ej: "Seguro", "Entrega exprés")

    public Shipment(String shipmentId, User user, Dealer assignedDealer,
                    String zone, double price, String period) {
        this.shipmentId = shipmentId;
        this.user = user;
        this.assignedDealer = assignedDealer;
        this.zone = zone;
        this.price = price;
        this.period = period;
        this.additionalServices = new ArrayList<>();
    }

    /**
     * metodo para Crear, solicitudes de envío antes de ser asignadas.  toca arreglarlo
     */
    public Shipment(String shipmentId, User user, String zone, String period) {
        this.shipmentId = shipmentId;
        this.user = user;
        this.zone = zone;
        this.period = period;
        this.additionalServices = new ArrayList<>();
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public ShippingStatus getStatus() {
        return status;
    }

    public void setStatus(ShippingStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(double estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Incidence getIncident() {
        return incidence;
    }

    public void setIncidence(Incidence incidence) {
        this.incidence = incidence;
    }

    public List<String> getAdditionalServices() {
        return additionalServices;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public Incidence getIncidence() {
        return incidence;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PackageModel getPackageModel() {
        return packageModel;
    }

    public void setPackageModel(PackageModel packageModel) {
        this.packageModel = packageModel;
    }

    public LocalDateTime getCrationDate() {
        return crationDate;
    }

    public void setCrationDate(LocalDateTime crationDate) {
        this.crationDate = crationDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setAdditionalServices(List<String> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public void addService(String service) {
        this.additionalServices.add(service);
    }



    @Override
    public String track() {
        return "hola";
    }


    @Override
    public String toString() {
        return "Shipment{" +
                "shipmentId='" + shipmentId + '\'' +
                ", rate=" + rate +
                ", incidence=" + incidence +
                ", payment=" + payment +
                ", address=" + address +
                ", packageModel=" + packageModel +
                ", price=" + price +
                ", status=" + status +
                ", crationDate=" + crationDate +
                ", estimatedDeliveryDate=" + estimatedDeliveryDate +
                ", user=" + user +
                ", assignedDealer=" + assignedDealer +
                ", zone='" + zone + '\'' +
                ", period='" + period + '\'' +
                ", additionalServices=" + additionalServices +
                '}';
    }
}
