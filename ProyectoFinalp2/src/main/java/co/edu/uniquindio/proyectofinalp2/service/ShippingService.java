package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.decorators.FragileShipment;
import co.edu.uniquindio.proyectofinalp2.decorators.PriorityShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SecureShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SignatureRequiredShipment;
import javafx.scene.layout.Priority;

public class ShippingService {

    public static ShippingService instance;
    private ShippingService() {
    }

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
        double price = 0;

        double weight = shipment.getPackageModel().getWeight();
        double volume = shipment.getPackageModel().getVolume();

        // Precio base por peso y volumen
        if (weight > 0 && volume > 0) {
            if (weight < 100 && volume < 500) {
                price += 2000;
            } else if (weight < 500 && volume < 1000) {
                price += 4000;
            } else {
                price += 99999;
            }
        }

        return price;
    }

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


     public Shipment applyDecorators(Shipment shipment, boolean isPriority, boolean isFragile, boolean isSecure, boolean isSignatureRequired) {
         if (isPriority) shipment = new PriorityShipping(shipment);
         if (isFragile) shipment = new FragileShipment(shipment);
         if (isSecure) shipment = new SecureShipping(shipment);
         if (isSignatureRequired) shipment = new SignatureRequiredShipment(shipment);
         return shipment;
     }


}

