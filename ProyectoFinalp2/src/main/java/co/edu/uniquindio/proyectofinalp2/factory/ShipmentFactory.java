package co.edu.uniquindio.proyectofinalp2.factory;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.decorators.FragileShipment;
import co.edu.uniquindio.proyectofinalp2.decorators.PriorityShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SecureShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SignatureRequiredShipment;
import co.edu.uniquindio.proyectofinalp2.strategy.*;

public class ShipmentFactory {

    /**
     * Crea un Shipment base utilizando la Rate (Strategy) pre-calculada y luego aplica el Decorador si es necesario.
     * @param type El tipo de servicio base (Priority, Fragile, Normal).
     * @param shipmentId El ID del envío.
     * @param user El usuario.
     * @param zone La zona de envío.
     * @param originAddress La dirección de origen.
     * @param destinationAddress La dirección de destino.
     * @param packageModel El paquete.
     * @param baseRate La Rate (con Strategy) calculada por el UserService.
     * @return El Shipment (posiblemente decorado).
     */
    public static Shipment createShipment(
            String type,
            String shipmentId,
            User user,
            String zone,
            Address originAddress,
            Address destinationAddress,
            PackageModel packageModel,
            Rate baseRate) { // 🟢 ACEPTA 8 PARÁMETROS

        // 1. Calcular el precio base usando la estrategia del Rate proporcionado
        double price = baseRate.calculateShipmentRate(packageModel, originAddress, destinationAddress);
        baseRate.setBase(price);

        // 2. Crear la instancia BASE (NormalShipment) usando el Builder
        Shipment shipment = new NormalShipment.Builder()
                .shipmentId(shipmentId)
                .user(user)
                .zone(zone)
                .originAddress(originAddress)
                .destinationAddress(destinationAddress)
                .packageModel(packageModel)
                .rate(baseRate) // ⬅️ FIX CRÍTICO: Se pasa la Rate obligatoria al Builder ANTES de .build()
                .build();

        // 3. Aplicar Decoradores (si aplica)
        // La Rate ya fue asignada en el paso 2.
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