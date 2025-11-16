package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.decorators.FragileShipment;
import co.edu.uniquindio.proyectofinalp2.decorators.PriorityShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SecureShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SignatureRequiredShipment;

public class ShippingService {

    public static ShippingService instance;
    private ShippingService() {}

    public static ShippingService getInstance() {
        if (instance == null) {
            instance = new ShippingService();
        }
        return instance;
    }

      public Shipment applyDecorators(Shipment shipment, boolean isPriority, boolean isFragile, boolean isSecure, boolean isSignatureRequired) {



        if (isPriority) {
            shipment = new PriorityShipping(shipment);
        }

        if (isFragile) {
            shipment = new FragileShipment(shipment);
        }

        if (isSecure) {
            shipment = new SecureShipping(shipment);
        }

        if (isSignatureRequired) {
            shipment = new SignatureRequiredShipment(shipment);
        }

        return shipment;
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
}