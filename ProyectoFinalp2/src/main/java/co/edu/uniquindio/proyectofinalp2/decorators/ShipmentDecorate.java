package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public abstract class ShipmentDecorate extends Shipment {

    protected Shipment shipment;

    public ShipmentDecorate(Shipment shipment) {
        super(shipment.getShipmentId(),
                shipment.getUser(),
                shipment.getAssignedDealer(),
                shipment.getZone(),
                shipment.getPrice(),
                shipment.getPeriod());
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
