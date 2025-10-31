 package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.Payment;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.Model.User;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

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
            shipmentAux.setUserId(newSenderId);
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

    // metodo para crear una direccion
    public static void addAddressToUser(User user, Address newAddress) {
        if (user == null || newAddress == null) {
            throw new IllegalArgumentException("Usuario o dirección inválidos");
        }
        user.getAddresses().add(newAddress);
    }

    // metodo para actualizar una drireccion
    public static void updateAddress(User user, String idAddress, Address updatedAddress) {
        if (user == null || idAddress == null) {
            throw new IllegalArgumentException("Usuario o dirección inválidos");
        }

        Address address = user.getAddresses()
                .stream()
                .filter(a -> a.getIdAddress().equals(idAddress))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Dirección no encontrada: " + idAddress));

        address.setAlias(updatedAddress.getAlias());
        address.setStreet(updatedAddress.getStreet());
        address.setCity(updatedAddress.getCity());
        address.setCoordinates(updatedAddress.getCoordinates());
    }


    // metodo para eliminar una direccion
    public static void deleteAddress(User user, String idAddress) {
        if (user == null || idAddress == null) {
            throw new IllegalArgumentException("Usuario o dirección inválidos");
        }

        boolean removed = user.getAddresses().removeIf(a -> a.getIdAddress().equals(idAddress));

        if (!removed) {
            throw new NotFoundException("No se encontró ninguna dirección con ID: " + idAddress);
        }
    }


    // metodo para consultar direcciones
    public static List<Address> listAddresses(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Usuario inválido");
        }
        return user.getAddresses();
    }

    // metodo para pagar un envío
    public static void payShipment(User user, Shipment shipment, double amount) {
        if (user == null || shipment == null) {
            throw new IllegalArgumentException("Usuario o envío inválido");
        }

        Payment payment = new Payment(
                "PAY-" + System.currentTimeMillis(),
                amount,
                java.time.LocalDateTime.now(),
                true
        );

        user.getPayments().add(payment);
        shipment.setPayment(payment);
    }
}
