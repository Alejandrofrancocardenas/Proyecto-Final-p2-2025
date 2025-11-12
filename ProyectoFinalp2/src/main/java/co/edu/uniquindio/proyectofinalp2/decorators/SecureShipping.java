package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class SecureShipping extends ShipmentDecorate {

    private static final long serialVersionUID = 1L;
    private static final double SECURE_COST = 50000.0; // Costo en pesos

    public SecureShipping(Shipment shipment) {
        super(shipment);
        shipment.addService("Envío Asegurado");
    }

    @Override
    public double getPrice() {
        return shipment.getPrice() + SECURE_COST;
    }

    @Override
    public String getDescription() {
        return shipment.getDescription() + " + Servicio de Seguro (Valor Adicional: " + SECURE_COST + " COP)";
    }
}