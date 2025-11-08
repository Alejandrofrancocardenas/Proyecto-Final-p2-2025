package co.edu.uniquindio.proyectofinalp2.proxy;

import co.edu.uniquindio.proyectofinalp2.Model.User;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotHavePermission;
import co.edu.uniquindio.proyectofinalp2.service.UserService;

public class UserServiceProxy {
    private final UserService userService;

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }


    public void cancelShipment(String shipmentId, User currentUser) {
        if ("cliente".equalsIgnoreCase(currentUser.getRole())) {
            userService.cancelShipment(shipmentId);
            System.out.println("[AUDITORÍA] " + currentUser.getFullname() + " canceló el envío " + shipmentId);
        } else {
           throw new NotHavePermission("Solo los clientes pueden cancelar sus envios");
        }
    }
}
