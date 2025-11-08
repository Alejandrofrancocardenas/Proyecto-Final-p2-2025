package co.edu.uniquindio.proyectofinalp2.proxy;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.service.CompanyService;

public class PaymentProxy {
    private final Shipment shipment;

    public PaymentProxy(Shipment shipment) {
        this.shipment = shipment;
    }

    public void makeShipment() {
        if (shipment.getPayment() != null && shipment.getPayment().isResult()) {
            CompanyService.getInstance().makeShipment(shipment);
        } else {
            throw new IllegalStateException("El pago no ha sido confirmado. No se puede realizar el envio.");
        }
    }
}
