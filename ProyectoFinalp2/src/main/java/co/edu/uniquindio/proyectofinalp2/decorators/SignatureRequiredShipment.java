package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Rate;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class SignatureRequiredShipment extends ShipmentDecorate{
    public SignatureRequiredShipment(Shipment shipment) {
        super(shipment);
        shipment.addService("Signature Required Shipment");

    }


    @Override
    public double getPrice() {
        return shipment.getRate().getBase() + 1000;
    }

    @Override
    public String track() {
        return super.track();
    }

}
