package co.edu.uniquindio.proyectofinalp2.Model;

import co.edu.uniquindio.proyectofinalp2.strategy.ShippingCostStrategy;

public class Rate {
    private String rateId;
    private double base;
    private double weightPrice;
    private double volumePrice;
    private String surcharges;

    private ShippingCostStrategy costStrategy;

    public Rate(String rateId, ShippingCostStrategy costStrategy) {
        this.rateId = rateId;
        this.costStrategy = costStrategy;
    }

    public Rate(String rateId, co.edu.uniquindio.proyectofinalp2.strategy.ShippingCostStrategy costStrategy) {
    }


    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getWeightPrice() {
        return weightPrice;
    }

    public void setWeightPrice(double weightPrice) {
        this.weightPrice = weightPrice;
    }

    public double getVolumePrice() {
        return volumePrice;
    }

    public void setVolumePrice(double volumePrice) {
        this.volumePrice = volumePrice;
    }

    public String getSurcharges() {
        return surcharges;
    }

    public void setSurcharges(String surcharges) {
        this.surcharges = surcharges;
    }


    @Override
    public String toString() {
        return rateId       + " " +
                base        + " " +
                weightPrice + " " +
                volumePrice + " " +
                surcharges;
    }


    public double calculateShipmentRate(PackageModel packageModel, Address address) {
        return costStrategy.calculateCost(packageModel, address);
    }
}
