package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import java.time.LocalDateTime;
import java.util.List;


public abstract class ShipmentDecorate extends Shipment {

    private static final long serialVersionUID = 1L;
    protected Shipment shipment;

    public ShipmentDecorate(Shipment shipment) {
        super();
        this.shipment = shipment;
    }


    @Override
    public String getDescription() {
        return shipment.getDescription();
    }

    @Override
    public String track() {
        return shipment.track();
    }


    @Override
    public Address getOriginAddress() {
        return shipment.getOriginAddress();
    }

    @Override
    public Address getDestinationAddress() {
        return shipment.getDestinationAddress();
    }

    @Override
    public void setOriginAddress(Address address) {
        shipment.setOriginAddress(address);
    }

    @Override
    public void setDestinationAddress(Address address) {
        shipment.setDestinationAddress(address);
    }

    @Override
    public String getShipmentId() {
        return shipment.getShipmentId();
    }

    @Override
    public void setShipmentId(String id) {
        shipment.setShipmentId(id);
    }

    @Override
    public User getUser() {
        return shipment.getUser();
    }

    @Override
    public void setUser(User user) {
        shipment.setUser(user);
    }

    @Override
    public ShippingStatus getStatus() {
        return shipment.getStatus();
    }

    @Override
    public void setStatus(ShippingStatus status) {
        shipment.setStatus(status);
    }

    @Override
    public Rate getRate() {
        return shipment.getRate();
    }

    @Override
    public void setRate(Rate rate) {
        shipment.setRate(rate);
    }

    @Override
    public PackageModel getPackageModel() {
        return shipment.getPackageModel();
    }

    @Override
    public void setPackageModel(PackageModel packageModel) {
        shipment.setPackageModel(packageModel);
    }

    @Override
    public String getZone() {
        return shipment.getZone();
    }

    @Override
    public void setZone(String zone) {
        shipment.setZone(zone);
    }

    @Override
    public String getPeriod() {
        return shipment.getPeriod();
    }

    @Override
    public void setPeriod(String period) {
        shipment.setPeriod(period);
    }

    @Override
    public LocalDateTime getCreationDate() {
        return shipment.getCreationDate();
    }

    @Override
    public void setCreationDate(LocalDateTime date) {
        shipment.setCreationDate(date);
    }

    @Override
    public double getEstimatedDeliveryDate() {
        return shipment.getEstimatedDeliveryDate();
    }

    @Override
    public void setEstimatedDeliveryDate(double estimatedDeliveryDate) {
        shipment.setEstimatedDeliveryDate(estimatedDeliveryDate);
    }

    @Override
    public Dealer getAssignedDealer() {
        return shipment.getAssignedDealer();
    }

    @Override
    public void setAssignedDealer(Dealer dealer) {
        shipment.setAssignedDealer(dealer);
    }

    @Override
    public Incidence getIncidence() {
        return shipment.getIncidence();
    }

    @Override
    public void setIncidence(Incidence incidence) {
        shipment.setIncidence(incidence);
    }

    @Override
    public Payment getPayment() {
        return shipment.getPayment();
    }

    @Override
    public void setPayment(Payment payment) {
        shipment.setPayment(payment);
    }

    @Override
    public List<String> getAdditionalServices() {
        return shipment.getAdditionalServices();
    }

    @Override
    public void setAdditionalServices(List<String> services) {
        shipment.setAdditionalServices(services);
    }

    @Override
    public void addService(String service) {
        shipment.addService(service);
    }

    @Override
    public String getProductName() {
        return shipment.getProductName();
    }

    @Override
    public void setProductName(String productName) {
        shipment.setProductName(productName);
    }
}
