package co.edu.uniquindio.proyectofinalp2.proxy;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.Model.ShippingStatus;
import co.edu.uniquindio.proyectofinalp2.Model.User;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotHavePermission;

public class ShipmentProxy extends Shipment {
    private final Shipment realShipment;
    private final User currentUser;


    public ShipmentProxy(Shipment realShipment,  User user) {
        this.realShipment = realShipment;
        this.currentUser = user;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public double getPrice() {
        return 0;
    }

    public double getCost() {
        return 0;
    }

    @Override
    public String track() {
        if (realShipment.getStatus() == ShippingStatus.CANCELLED) {
            throw new NotHavePermission("No se puede rastrear un envío cancelado");
        }
        return realShipment.track();
    }

    public void cancel() {
        if (currentUser.getId().equals(realShipment.getUser().getId()) ||
            currentUser.getRol().equalsIgnoreCase("admin")) {
            realShipment.setStatus(ShippingStatus.CANCELLED);
        } else {
            throw new NotHavePermission("No tienes permiso para cancelar este envío");
        }
    }
}
