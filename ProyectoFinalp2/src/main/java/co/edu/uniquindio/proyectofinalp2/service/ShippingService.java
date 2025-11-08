package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.Rate;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.decorators.FragileShipment;
import co.edu.uniquindio.proyectofinalp2.decorators.PriorityShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SecureShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SignatureRequiredShipment;
import javafx.scene.layout.Priority;

public class ShippingService {

    public static ShippingService instance;
    private ShippingService() {}

    public static ShippingService getInstance() {
        if (instance == null) {
            instance = new ShippingService();
        }
        return instance;
    }

    /**
     * Calcula la tarifa de envío según origen, destino, peso, volumen y prioridad.
     */
    public double calculateBasePrice(Shipment shipment) {
        if (shipment.getRate() == null) {
            throw new IllegalStateException("El envio no tiene una tarifa asignada");
        }

        // Precio base por peso y volumen
        return shipment.getRate().calculateShipmentRate(shipment.getPackageModel(), shipment.getAddress());
    }


    // requiere que ya tenga la tarifa el envio osea que esto se hace despues de haber creado el envio con la tarifa
    public Shipment applyDecorators(Shipment shipment, boolean isPriority, boolean isFragile, boolean isSecure, boolean isSignatureRequired) {
        if (isPriority) {
            shipment.getRate().setSurcharges("Prioridad");
            shipment.getRate().setBase(shipment.getRate().getBase() + 2000);
            shipment = new PriorityShipping(shipment);
        }

        if (isFragile) {
            shipment.getRate().setSurcharges("Frágil");
            shipment.getRate().setBase(shipment.getRate().getBase() + 8000);
            shipment = new FragileShipment(shipment);
        }

        if (isSecure) {
            shipment.getRate().setSurcharges("Seguro");
            shipment.getRate().setBase(shipment.getRate().getBase() + 6200);
            shipment = new SecureShipping(shipment);
        }

        if (isSignatureRequired) {
            shipment.getRate().setSurcharges("Firma requerida");
            shipment.getRate().setBase(shipment.getRate().getBase() + 10000);
            shipment = new SignatureRequiredShipment(shipment);
        }

        return shipment;
    }


    // esto de aca abajo es experimental--------------------------------------------------------------------------------
    public Shipment applyPriority(Shipment shipment, boolean isPriority) {
        if (isPriority) {
            return new PriorityShipping(shipment);
        }
        return shipment;
    }

    public Shipment applyFragile(Shipment shipment, boolean isFragile) {
        if (isFragile) {
            return new FragileShipment(shipment);
        }
        return shipment;
    }

    public Shipment applySecure(Shipment shipment, boolean isSecure) {
        if (isSecure) {
            return new SecureShipping(shipment);
        }
        return shipment;
    }

    public Shipment applySignatureRequired(Shipment shipment, boolean isSignatureRequired) {
        if (isSignatureRequired) {
            return new SignatureRequiredShipment(shipment);
        }
        return shipment;
    }
}

