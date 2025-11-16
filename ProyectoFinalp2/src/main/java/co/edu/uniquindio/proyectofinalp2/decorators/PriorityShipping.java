package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class PriorityShipping extends ShipmentDecorate {

    private static final long serialVersionUID = 1L;
    private static final double URGENT_COST = 5000.0;

    public PriorityShipping(Shipment decoratedShipment) {
        super(decoratedShipment);
        decoratedShipment.addService("Envío Prioritario");
    }

    @Override
    public String getDescription() {
        return shipment.getDescription() + " + Envío Prioritario (Valor Adicional: " + URGENT_COST + " COP)";
    }

    @Override
    public double getPrice() {
        return shipment.getPrice() + URGENT_COST;
    }
}