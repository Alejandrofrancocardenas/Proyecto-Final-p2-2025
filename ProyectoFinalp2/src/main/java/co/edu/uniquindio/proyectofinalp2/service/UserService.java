 package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.Model.User;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private List<User> users = new ArrayList<>();
    private static List<Shipment> shipmentsTemp = new ArrayList<>();

    // metodo para que un usuario cotize la tarifa de envio según origen, destino, peso, volumen y prioridad.
    public static double getPrice(String origin, String destination, double weight
            , double volume, String priority){
        double price = 0.0;
        price = ShippingService.calculatePrice(origin, destination, weight, volume, priority);
        return price;
    }

    //metodo para crear solicitudes de envío antes de ser asignadas.
    public static Shipment createShipment(String shipmentId, String senderId, String zone, String period){
        Shipment shipment = new Shipment(shipmentId, senderId, zone, period);
        shipmentsTemp.add(shipment);
        return shipment;
    }

    // metodo para modificar solicitudes de envio antes de ser asignadas
    public static Shipment updateShipment(String shipmentId, String newSenderId, String newZone, String newPeriod){
        Shipment shipmentAux = findShipmentTempById(shipmentId);
        if (shipmentAux == null){
            throw new NotFoundException("Shipment not found");
        } else {
            shipmentAux.setSenderId(newSenderId);
            shipmentAux.setZone(newZone);
            shipmentAux.setPeriod(newPeriod);
            return shipmentAux;
        }
    }

    // metodo para cancelar solicitudes de envio (la elimina de una vez)
    public static void cancelShipment(String shipmentId){
        Shipment shipmentAux = findShipmentTempById(shipmentId);
        if (shipmentAux == null){
            throw new NotFoundException("Shipment not found");
        }else{
            shipmentsTemp.remove(shipmentAux);
        }
    }

    // metodo para realizar solicitud de envio cuando ya esta seguro
    public static void makeShipment(String shipmentId){
        Shipment shipmentAux = findShipmentTempById(shipmentId);
        if (shipmentAux == null){
            throw new NotFoundException("Shipment not found");
        }else{
            CompanyService.makeShipment(shipmentAux);
        }
    }

    // metodo para que el usuario pueda pagar el envio


    // metodo para buscar una solicitud de envio en la lista de envios temporales de usuario
    private static Shipment findShipmentTempById(String shipmentId){
        Shipment shipmentAux = null;
        for (Shipment shipment : shipmentsTemp){
            if (shipment.getShipmentId().equals(shipmentId)){
                shipmentAux = shipment;
            }else{
                throw new NotFoundException("Shipment with ID: " + shipmentId + " not found");
            }
        }
        return shipmentAux;
    }

}
