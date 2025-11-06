package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Rate;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class FragileShipment extends ShipmentDecorate {
    public FragileShipment(Shipment shipment) {
        super(shipment);
        shipment.addService("FragileShipment");
    }

    @Override
    public double getPrice() {
        return shipment.getRate().getBase() + 500000;
    }

    @Override
    public String track() {
        return super.track();
    }
}
