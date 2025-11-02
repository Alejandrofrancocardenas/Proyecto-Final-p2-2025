package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class PriorityShipping extends ShipmentDecorate{
    public PriorityShipping(Shipment shipment) {
        super(shipment);
        shipment.addService("PriorityShipping");

    }

    @Override
    public double getPrice() {
        return shipment.getPrice() + 5000;
    }

    @Override
    public String track() {
        return shipment.track() + "\nEl envío tiene prioridad alta (express).";
    }
}
