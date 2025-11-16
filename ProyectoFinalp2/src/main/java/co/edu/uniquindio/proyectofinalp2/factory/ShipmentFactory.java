package co.edu.uniquindio.proyectofinalp2.factory;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.decorators.FragileShipment;
import co.edu.uniquindio.proyectofinalp2.decorators.PriorityShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SecureShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SignatureRequiredShipment;
import co.edu.uniquindio.proyectofinalp2.strategy.*;

public class ShipmentFactory {

    public static Shipment createShipment(
            String type,
            String shipmentId,
            User user,
            String zone,
            Address originAddress,
            Address destinationAddress,
            PackageModel packageModel,
            Rate baseRate) {

        double price = baseRate.calculateShipmentRate(packageModel, originAddress, destinationAddress);
        baseRate.setBase(price);

        Shipment shipment = new NormalShipment.Builder()
                .shipmentId(shipmentId)
                .user(user)
                .zone(zone)
                .originAddress(originAddress)
                .destinationAddress(destinationAddress)
                .packageModel(packageModel)
                .rate(baseRate)
                .build();

        switch (type.toLowerCase()) {
            case "priority":
                shipment = new PriorityShipping(shipment);
                break;
            case "fragile":
                shipment = new FragileShipment(shipment);
                break;
            case "signature":
                shipment = new SignatureRequiredShipment(shipment);
                break;
            case "secure":
                shipment = new SecureShipping(shipment);
                break;
        }

        return shipment;
    }
}