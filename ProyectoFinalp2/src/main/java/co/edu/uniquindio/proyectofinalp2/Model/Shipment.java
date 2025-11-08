package co.edu.uniquindio.proyectofinalp2.Model;

import co.edu.uniquindio.proyectofinalp2.observer.ShipmentNotifier;
import co.edu.uniquindio.proyectofinalp2.observer.ShipmentObserver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un envío dentro del sistema.
 * Cada envío tiene un remitente (usuario), un repartidor asignado,
 * información sobre la zona, precio, estado, tiempo de entrega y servicios adicionales.
 */
public abstract class Shipment {

    protected String shipmentId;               // Identificador único del envío
    protected Rate rate;                       // la tarifa
    protected Incidence incidence;             // piuede tener incidencias
    protected Payment payment;                 // el pago que nesecita el envio para ser enviado
    protected Address address;                 // aqui va el origen y el destino
    protected PackageModel packageModel;       // el envio nesecita tener un paqwuete para enviar
    protected ShippingStatus status;           // Estado actual: "Pendiente", "En Camino", "Entregado", etc.
    protected LocalDateTime creationDate;       // fecha cuando se crea
    protected double estimatedDeliveryDate;    // Tiempo que tardó en entregarse (en horas)
    protected User user;                       // el usuario que realiza el envío
    protected Dealer assignedDealer;           // Repartidor asignado
    protected String zone;                     // Zona de entrega
    protected String period;                   // Periodo de entrega (por ejemplo "Octubre 2025")
    protected List<String> additionalServices = new ArrayList<>(); // Servicios adicionales (ej: "Seguro", "Entrega exprés")

    private final ShipmentNotifier notifier = new ShipmentNotifier();


    // Métodos para gestionar observadores
    public void addObserver(ShipmentObserver observer) {
        notifier.addObserver(observer);
    }

    public void removeObserver(ShipmentObserver observer) {
        notifier.removeObserver(observer);
    }


    protected Shipment(Builder<?> builder) {
        this.shipmentId = builder.shipmentId;
        this.user = builder.user;
        this.zone = builder.zone;
        this.address = builder.address;
        this.packageModel = builder.packageModel;
    }

    // este se usa unicamente para decoradores que no necesitan builder
    protected Shipment() {}

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
        notifier.notifyObservers(this);
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

    public double getPrice() {
        return this.rate.getBase();
    }

    public abstract String track();

    public abstract static class Builder<T extends Builder<T>> {

        private String shipmentId;
        private Address address;
        private PackageModel packageModel;
        private User user;
        private String zone;

        public T shipmentId(String shipmentId) {
            this.shipmentId = shipmentId;
            return self();
        }

        public T address(Address address) {
            this.address = address;
            return self();
        }

        public T packageModel(PackageModel packageModel) {
            this.packageModel = packageModel;
            return self();
        }

        public T user(User user) {
            this.user = user;
            return self();
        }

        public T zone(String zone) {
            this.zone = zone;
            return self();
        }

        protected abstract T self();
        public abstract Shipment build();

    }

    @Override
    public String toString() {
        return shipmentId             + " " +
                rate                  + " " +
                incidence             + " " +
                payment               + " " +
                address               + " " +
                packageModel          + " " +
                status                + " " +
                creationDate          + " " +
                estimatedDeliveryDate + " " +
                user                  + " " +
                assignedDealer        + " " +
                zone                  + " " +
                period                + " " +
                additionalServices;
    }
}
