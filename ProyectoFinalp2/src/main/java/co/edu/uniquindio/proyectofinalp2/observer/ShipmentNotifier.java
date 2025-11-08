package co.edu.uniquindio.proyectofinalp2.observer;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;

import java.util.ArrayList;
import java.util.List;

public class ShipmentNotifier {

    private final List<ShipmentObserver> observers = new ArrayList<>();

    public void addObserver(ShipmentObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ShipmentObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Shipment shipment) {
        for (ShipmentObserver observer : observers) {
            observer.update(shipment);
        }
    }
}
