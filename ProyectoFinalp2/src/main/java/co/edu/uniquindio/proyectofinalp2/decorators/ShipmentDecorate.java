package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.ITracker;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class ShipmentDecorate implements ITracker {

    protected Shipment shipment;

    public ShipmentDecorate(Shipment shipment) {
        this.shipment = shipment;
    }

    @Override
    public void track() {
        shipment.track();
    }
}
