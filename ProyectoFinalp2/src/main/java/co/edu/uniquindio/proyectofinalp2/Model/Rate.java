package co.edu.uniquindio.proyectofinalp2.Model;

public class Rate {
    private String rateId;
    private double base;
    private double weightPrice;
    private double volumePrice;
    private double proritySurcharge;

    public Rate(String rateId, double base, double weightPrice, double volumePrice, double proritySurcharge) {
        this.rateId = rateId;
        this.base = base;
        this.weightPrice = weightPrice;
        this.volumePrice = volumePrice;
        this.proritySurcharge = proritySurcharge;
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

    public double getProritySurcharge() {
        return proritySurcharge;
    }

    public void setProritySurcharge(double proritySurcharge) {
        this.proritySurcharge = proritySurcharge;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "rateId='" + rateId + '\'' +
                ", base=" + base +
                ", weightPrice=" + weightPrice +
                ", volumePrice=" + volumePrice +
                ", proritySurcharge=" + proritySurcharge +
                '}';
    }

    public double calculateShipmentRate(Shipment shipment) {
        //logiacaa
        return 0;
    }
}
