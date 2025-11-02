package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public abstract class ShipmentDecorate extends Shipment {

    protected Shipment shipment;


    public ShipmentDecorate(Shipment shipment) {
        // pasamos null al padre porque el decorador no se construye con builder
        super();
        this.shipment = shipment;
    }


    @Override
    public double getPrice() {
        return shipment.getPrice();
    }

    @Override
    public String track() {
        return shipment.track();
    }
}
