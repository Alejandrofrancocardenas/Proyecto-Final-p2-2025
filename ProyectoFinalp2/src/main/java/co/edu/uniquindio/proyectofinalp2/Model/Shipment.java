package co.edu.uniquindio.proyectofinalp2.model;

import java.time.LocalDateTime;

public class Shipment {
    //conexion con otras clases
    private User user;
    private Dealer dealer;

    private String idShipment;
    private double weight;
    private double dimensions;
    private double price;
    private ShippingStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime estimatedDeliveryDate;

    public Shipment(String idShipment, double weight, double dimensions, double price, ShippingStatus status, LocalDateTime creationDate, LocalDateTime estimatedDeliveryDate) {
        this.idShipment = idShipment;
        this.weight = weight;
        this.dimensions = dimensions;
        this.price = price;
        this.status = status;
        this.creationDate = creationDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dealer getRepartidor() {
        return dealer;
    }

    public void setRepartidor(Dealer dealer) {
        this.dealer = dealer;
    }

    public String getIdShipment() {
        return idShipment;
    }

    public void setIdShipment(String idShipment) {
        this.idShipment = idShipment;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDimensions() {
        return dimensions;
    }

    public void setDimensions(double dimensions) {
        this.dimensions = dimensions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ShippingStatus getStatus() {
        return status;
    }

    public void setStatus(ShippingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "user=" + user +
                ", dealer=" + dealer +
                ", idShipment='" + idShipment + '\'' +
                ", weight=" + weight +
                ", dimensions=" + dimensions +
                ", price=" + price +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", estimatedDeliveryDate=" + estimatedDeliveryDate +
                '}';
    }
}
