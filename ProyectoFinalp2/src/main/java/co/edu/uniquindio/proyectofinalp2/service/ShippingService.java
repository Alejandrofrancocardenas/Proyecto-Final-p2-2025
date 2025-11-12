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

    // --------------------------------------------------------------------------------
    // 1. ELIMINACIÓN DE calculateBasePrice
    // --------------------------------------------------------------------------------
    // 💡 NOTA: Se ha eliminado el método calculateBasePrice.
    // Ahora, se asume que la tarifa base (Rate) ha sido calculada y asignada al envío
    // por la Factory o el Strategy antes de llamar a este servicio.
    // Para obtener el costo base, se debe usar directamente shipment.getRate().getBase().


    // --------------------------------------------------------------------------------
    // 2. CORRECCIÓN LÓGICA EN applyDecorators (Patrón Decorator)
    // --------------------------------------------------------------------------------

    /**
     * Aplica decoradores al envío. La responsabilidad de sumar los costos adicionales
     * recae en los objetos Decorator cuando se llama a su método getPrice(),
     * no manipulando el objeto Rate base.
     */
    public Shipment applyDecorators(Shipment shipment, boolean isPriority, boolean isFragile, boolean isSecure, boolean isSignatureRequired) {

        // 💥 CORRECCIÓN CRÍTICA: Se eliminaron todas las llamadas a setBase() y setSurcharges()
        // ya que la clase Rate no los soporta, y la lógica de costo debe ir en el Decorator.

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


    // --------------------------------------------------------------------------------
    // 3. MÉTODOS EXPERIMENTALES (Refinados)
    // --------------------------------------------------------------------------------
    // Estos métodos ahora simplemente envuelven el Shipment si el flag es true,
    // siguiendo el patrón Decorator puro.

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