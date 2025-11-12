package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

/**
 * Decorador para marcar un envío como Frágil, incrementando el costo.
 */
public class FragileShipment extends ShipmentDecorate {

    private static final long serialVersionUID = 1L;
    private static final double FRAGILE_COST = 2500.0; // 2500 COP

    public FragileShipment(Shipment decoratedShipment) {
        super(decoratedShipment);
        decoratedShipment.addService("Manejo de Frágiles");
    }

    @Override
    public String getDescription() {
        return shipment.getDescription() + " + Manejo de Frágiles (Valor Adicional: " + FRAGILE_COST + " COP)";
    }

    @Override
    public double getPrice() {
        return shipment.getPrice() + FRAGILE_COST;
    }
}