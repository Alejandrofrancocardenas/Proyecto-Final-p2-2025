package co.edu.uniquindio.proyectofinalp2.Model;

public class NormalShipment extends Shipment {


    public NormalShipment(String shipmentId, User user, Dealer assignedDealer, String zone, double price, String period) {
        super(shipmentId, user, assignedDealer, zone, price, period);
    }

    public NormalShipment(String shipmentId, User user, String zone, String period) {
        super(shipmentId, user, zone, period);
    }

    @Override
    public String track() {
        return "\nEl envío es normal.";
    }

}
