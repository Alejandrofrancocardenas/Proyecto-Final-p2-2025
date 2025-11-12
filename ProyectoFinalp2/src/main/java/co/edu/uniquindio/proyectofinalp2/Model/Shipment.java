package co.edu.uniquindio.proyectofinalp2.Model;

import co.edu.uniquindio.proyectofinalp2.observer.ShipmentNotifier;
import co.edu.uniquindio.proyectofinalp2.observer.ShipmentObserver;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa un envío dentro del sistema.
 * Es el componente base del patrón Decorator y utiliza el patrón Builder para su construcción.
 * La información de costo base se obtiene del objeto Rate.
 */
public abstract class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String shipmentId;               // Identificador único del envío
    protected Rate rate;                       // La tarifa que incluye el costo base
    protected Incidence incidence;             // Posibles incidencias
    protected Payment payment;                 // El pago asociado al envío

    protected Address originAddress;          // Dirección de origen
    protected Address destinationAddress;     // Dirección de destino

    protected PackageModel packageModel;       // El paquete/modelo del envío
    protected ShippingStatus status;           // Estado actual: "Pendiente", "En Camino", "Entregado", etc.
    protected LocalDateTime creationDate;       // Fecha de creación
    protected double estimatedDeliveryDate;    // Tiempo estimado de entrega (en horas)
    protected User user;                       // Usuario que realiza el envío
    protected Dealer assignedDealer;           // Repartidor asignado
    protected String zone;                     // Zona de entrega
    protected String period;                   // Periodo de entrega (ej: "Octubre 2025")
    protected List<String> additionalServices = new ArrayList<>(); // Servicios adicionales (Decoradores)
    protected String productName;              // Nombre del producto enviado

    private final ShipmentNotifier notifier = new ShipmentNotifier();


    // -------------------------------------------------------------------
    // MÉTODOS CLAVE PARA EL PATRÓN DECORATOR
    // -------------------------------------------------------------------

    /**
     * Devuelve la descripción del envío, incluyendo todos los servicios adicionales.
     */
    public abstract String getDescription();

    /**
     * Calcula el costo total del envío (base + decoradores).
     * RENOMBRADO de getCost() a getPrice() para la consistencia con UserService.
     */
    public abstract double getPrice(); // ⬅️ CAMBIADO A getPrice()

    /**
     * Obtiene el costo base del envío directamente desde el objeto Rate.
     * Este es el precio inicial antes de aplicar decoradores.
     */
    public double getRateBaseCost() {
        return rate != null ? rate.getBasePrice() : 0.0;
    }
    // -------------------------------------------------------------------


    // Métodos para gestionar observadores
    public void addObserver(ShipmentObserver observer) {
        notifier.addObserver(observer);
    }

    public void removeObserver(ShipmentObserver observer) {
        notifier.removeObserver(observer);
    }

    // Constructor que usa el Builder
    protected Shipment(Builder<?> builder) {
        this.shipmentId = builder.shipmentId;
        this.user = builder.user;
        this.zone = builder.zone;
        this.originAddress = builder.originAddress;
        this.destinationAddress = builder.destinationAddress;
        this.packageModel = builder.packageModel;
        this.productName = builder.productName;
        this.status = ShippingStatus.CREATED;
        this.creationDate = LocalDateTime.now();

        // 🟢 FIX CRÍTICO (Anteriormente alrededor de la línea 88)
        // El error ocurría al intentar usar el campo 'rate' (que aún era null)
        // en la lógica de fallback. Ahora forzamos su existencia.
        if (builder.rate == null) {
            throw new IllegalStateException("Error de Builder: El objeto Rate (Tarifa base) es obligatorio para construir un Shipment.");
        }

        // Asignar el Rate recibido
        this.rate = builder.rate;

        // El Payment se inicializa usando el costo base de la tarifa
        this.payment = builder.payment != null ? builder.payment : new Payment(this.getRateBaseCost(), false);
    }

    // Constructor vacío para uso en el patrón Decorator
    protected Shipment() {}

    // -------------------------------------------------------------------
    // Getters y Setters
    // -------------------------------------------------------------------

    public String getShipmentId() { return shipmentId; }
    public void setShipmentId(String shipmentId) { this.shipmentId = shipmentId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Dealer getAssignedDealer() { return assignedDealer; }
    public void setAssignedDealer(Dealer assignedDealer) { this.assignedDealer = assignedDealer; }
    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }
    public ShippingStatus getStatus() { return status; }
    public void setStatus(ShippingStatus status) {
        this.status = status;
        notifier.notifyObservers(this);
    }
    public double getEstimatedDeliveryDate() { return estimatedDeliveryDate; }
    public void setEstimatedDeliveryDate(double estimatedDeliveryDate) { this.estimatedDeliveryDate = estimatedDeliveryDate; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public Incidence getIncident() { return incidence; }
    public void setIncidence(Incidence incidence) { this.incidence = incidence; }
    public List<String> getAdditionalServices() { return additionalServices; }
    public Rate getRate() { return rate; }
    public void setRate(Rate rate) { this.rate = rate; }
    public Incidence getIncidence() { return incidence; }
    public Address getOriginAddress() { return originAddress; }
    public void setOriginAddress(Address originAddress) { this.originAddress = originAddress; }
    public Address getDestinationAddress() { return destinationAddress; }
    public void setDestinationAddress(Address destinationAddress) { this.destinationAddress = destinationAddress; }
    public PackageModel getPackageModel() { return packageModel; }
    public void setPackageModel(PackageModel packageModel) { this.packageModel = packageModel; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
    public void setAdditionalServices(List<String> additionalServices) { this.additionalServices = additionalServices; }
    public void addService(String service) { this.additionalServices.add(service); }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    // Se asume que Incidence tiene un constructor con String
    public void setIncidenceReport(String report) { /* this.incidence = new Incidence(report); */ } // Comentado por si Incidence no es una clase simple

    public abstract String track(); // Método abstracto para el seguimiento

    // -------------------------------------------------------------------
    // Builder
    // -------------------------------------------------------------------

    public abstract static class Builder<T extends Builder<T>> implements Serializable {

        private static final long serialVersionUID = 1L;

        private String shipmentId;
        private Address originAddress;
        private Address destinationAddress;
        private PackageModel packageModel;
        private User user;
        private String zone;
        private String productName;
        private Rate rate; // El Rate para definir el costo base
        private Payment payment;

        public T shipmentId(String shipmentId) {
            this.shipmentId = shipmentId;
            return self();
        }

        public T originAddress(Address originAddress) {
            this.originAddress = originAddress;
            return self();
        }

        public T destinationAddress(Address destinationAddress) {
            this.destinationAddress = destinationAddress;
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

        public T productName(String productName) {
            this.productName = productName;
            return self();
        }

        public T rate(Rate rate) {
            this.rate = rate;
            return self();
        }

        public T payment(Payment payment) {
            this.payment = payment;
            return self();
        }

        protected abstract T self();
        public abstract Shipment build();

    }

    @Override
    public String toString() {
        return "Shipment{" +
                "shipmentId='" + shipmentId + '\'' +
                " baseRate=" + (rate != null ? rate.getBasePrice() : "N/A") +
                ", originAddress=" + originAddress +
                ", destinationAddress=" + destinationAddress +
                ", productName='" + productName + '\'' +
                ", status=" + status +
                ", cost=" + getPrice() + // ⬅️ CAMBIADO A getPrice()
                '}';
    }
}