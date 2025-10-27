package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class SecureShipping extends ShipmentDecorate {
    public SecureShipping(Shipment shipment) {
        super(shipment);
    }

    @Override
    public String track() {
        return super.track();

    }
}
