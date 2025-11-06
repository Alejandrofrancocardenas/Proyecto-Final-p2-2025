package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Rate;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public abstract class ShipmentDecorate extends Shipment {

    protected Shipment shipment;

    public ShipmentDecorate(Shipment shipment) {
        super();
        this.shipment = shipment;
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
    public double getPrice() {
        return shipment.getRate().getBase();
    }

    @Override
    public String track() {
        return shipment.track();
    }
}
