package co.edu.uniquindio.proyectofinalp2.factory;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.decorators.FragileShipment;
import co.edu.uniquindio.proyectofinalp2.decorators.PriorityShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SecureShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SignatureRequiredShipment;
import co.edu.uniquindio.proyectofinalp2.strategy.*;

public class ShipmentFactory {

    public static Shipment createShipment(String type, String shipmentId, User user, String zone, Address address, PackageModel packageModel) {

        ShippingCostStrategy costStrategy;
        Shipment shipment = new NormalShipment.Builder()
                .shipmentId(shipmentId)
                .user(user)
                .zone(zone)
                .address(address)
                .packageModel(packageModel)
                .build();

        // Asignar decorador y estrategia según tipo
        switch (type.toLowerCase()) {
            case "priority":
                shipment = new PriorityShipping(shipment);
                costStrategy = new PriorityCostStrategy();
                break;
            case "fragile":
                shipment = new FragileShipment(shipment);
                costStrategy = new FragileCostStrategy();
                break;
            case "signature":
                shipment = new SignatureRequiredShipment(shipment);
                costStrategy = new SignatureCostStrategy();
                break;
            case "secure":
                shipment = new SecureShipping(shipment);
                costStrategy = new SecureCostStrategy();
                break;
            default:
                costStrategy = new NormalCostStrategy();
                break;
        }

        // Crear la tarifa con su estrategia
        Rate rate = new Rate("R-" + shipmentId, costStrategy);
        double price = rate.calculateShipmentRate(packageModel, address);
        rate.setBase(price);

        shipment.setRate(rate);
        return shipment;
    }
}
