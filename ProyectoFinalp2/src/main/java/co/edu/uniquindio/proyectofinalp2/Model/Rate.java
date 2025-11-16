package co.edu.uniquindio.proyectofinalp2.Model;

import co.edu.uniquindio.proyectofinalp2.strategy.ShippingCostStrategy;
import java.io.Serializable;

public class Rate implements Serializable {
    private static final long serialVersionUID = 1L;

    private String rateId;
    private double basePrice;
    private ShippingCostStrategy costStrategy;

    public Rate(String rateId, ShippingCostStrategy costStrategy) {
        this.rateId = rateId;
        this.costStrategy = costStrategy;
    }

    public double calculateShipmentRate(PackageModel packageModel, Address originAddress, Address destinationAddress) {
        return this.costStrategy.calculateShippingRate(packageModel, originAddress, destinationAddress);
    }


    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBase(double basePrice) {
        this.basePrice = basePrice;
    }

    public ShippingCostStrategy getCostStrategy() {
        return costStrategy;
    }

    public void setCostStrategy(ShippingCostStrategy costStrategy) {
        this.costStrategy = costStrategy;
    }
}