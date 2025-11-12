package co.edu.uniquindio.proyectofinalp2.observer;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;



public class DealerNotification implements ShipmentObserver {

    private final NotificationHandler handler;

    public DealerNotification(NotificationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void update(Shipment shipment) {
        String message = "🚚 El envío " + shipment.getShipmentId() +
                " cambió su estado a " + shipment.getStatus();
        handler.onNotification(message);
    }
}
