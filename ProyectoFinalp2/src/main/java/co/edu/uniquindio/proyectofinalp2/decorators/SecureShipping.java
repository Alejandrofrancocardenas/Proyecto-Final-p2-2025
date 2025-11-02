package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class SecureShipping extends ShipmentDecorate {
    public SecureShipping(Shipment shipment) {
        super(shipment);
        shipment.addService("SecureShipping");

    }


    @Override
    public double getPrice() {
        return shipment.getPrice() + 50000;
    }

    @Override
    public String track() {
        return super.track();

    }
}
