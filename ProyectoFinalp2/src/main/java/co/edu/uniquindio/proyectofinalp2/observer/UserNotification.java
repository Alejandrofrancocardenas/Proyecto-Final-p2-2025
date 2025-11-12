package co.edu.uniquindio.proyectofinalp2.observer;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

public class UserNotification implements ShipmentObserver {

    private final NotificationHandler handler;

    public UserNotification(NotificationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void update(Shipment shipment) {
        String message = "🚚 El envío " + shipment.getShipmentId() +
                " cambió su estado a " + shipment.getStatus();
        handler.onNotification(message);
    }
}
