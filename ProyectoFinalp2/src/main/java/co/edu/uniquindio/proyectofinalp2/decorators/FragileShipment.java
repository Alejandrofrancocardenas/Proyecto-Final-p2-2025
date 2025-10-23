package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class FragileShipment extends ShipmentDecorate {
    public FragileShipment(Shipment shipment) {
        super(shipment);
    }

    @Override
    public void track() {
        super.track();
    }
}
