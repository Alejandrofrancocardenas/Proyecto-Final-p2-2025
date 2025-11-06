package co.edu.uniquindio.proyectofinalp2.Model;

public class Rate {
    private String rateId;
    private double base;
    private double weightPrice;
    private double volumePrice;
    private String surcharges;


    public Rate(String rateId, double weightPrice, double volumePrice) {
        this.rateId = rateId;
        this.weightPrice = weightPrice;
        this.volumePrice = volumePrice;
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
        return "Rate{" +
                "rateId='" + rateId + '\'' +
                ", base=" + base +
                ", weightPrice=" + weightPrice +
                ", volumePrice=" + volumePrice +
                ", proritySurcharge=" + surcharges +
                '}';
    }


    public static double calculateShipmentRate(double weight, double volume) {
        double price = 0;

        if (weight > 0 && volume > 0) {
            if (weight < 100 && volume < 500) {
                price += 2000;
            } else if (weight < 500 && volume < 1000) {
                price += 4000;
            } else {
                price += 99999;
            }
        }
        return price;
    }
}
