package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class SignatureRequiredShipment extends ShipmentDecorate{
    public SignatureRequiredShipment(Shipment shipment) {
        super(shipment);
    }

    @Override
    public String track() {
        return super.track();
    }

}
