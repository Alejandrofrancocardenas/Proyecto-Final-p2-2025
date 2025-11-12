package co.edu.uniquindio.proyectofinalp2.decorators;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class SignatureRequiredShipment extends ShipmentDecorate {

    private static final long serialVersionUID = 1L;
    private static final double SIGNATURE_COST = 1000.0; // Costo en pesos

    public SignatureRequiredShipment(Shipment shipment) {
        super(shipment);
        shipment.addService("Firma Requerida");
    }

    @Override
    public String getDescription() {
        return shipment.getDescription() + " + Requerimiento de Firma (Valor Adicional: " + SIGNATURE_COST + " COP)";
    }

    @Override
    public double getPrice() {
        return shipment.getPrice() + SIGNATURE_COST;
    }
}