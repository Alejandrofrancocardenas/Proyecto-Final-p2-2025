 package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

 public class UserService {

     private final User user;

     public User getUser() {
        return user;
     }

     public UserService(User user) {
        this.user = user;
     }

     // metodo para crear una direccion
     public void addAddressToUser(User user, Address newAddress) {
         if (user == null || newAddress == null) {
             throw new IllegalArgumentException("Usuario o dirección inválidos");
         }
         user.getAddresses().add(newAddress);
     }

     // metodo para actualizar una drireccion
     public void updateAddress(User user, String idAddress, Address updatedAddress) {
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
     public void deleteAddress(User user, String idAddress) {
         if (user == null || idAddress == null) {
             throw new IllegalArgumentException("Usuario o dirección inválidos");
         }

         boolean removed = user.getAddresses().removeIf(a -> a.getIdAddress().equals(idAddress));

         if (!removed) {
             throw new NotFoundException("No se encontró ninguna dirección con ID: " + idAddress);
         }
     }


     // metodo para consultar direcciones
     public List<Address> listAddresses(User user) {
         if (user == null) {
             throw new IllegalArgumentException("Usuario inválido");
         }
         return user.getAddresses();
     }


    // metodo cotize la tarifa de envio según origen, destino, peso, volumen y prioridad.
    public double getPrice(Shipment shipment) {
        return ShippingService.getInstance().calculateBasePrice(shipment);

    }


    //metodo para crear solicitudes de envío antes de ser asignadas.
    public void createShipment(Shipment shipment) {
        shipment.setCreationDate(LocalDateTime.now());
        user.getShipments().add(shipment);
    }


    // metodo para modificar solicitudes de envio antes de ser asignadas
    public Shipment updateShipment(String shipmentId, User newSenderId, String newZone, String newPeriod){
        Shipment shipmentAux = findShipmentTempById(shipmentId);
        shipmentAux.setUser(newSenderId);
        shipmentAux.setZone(newZone);
        shipmentAux.setPeriod(newPeriod);
        return shipmentAux;
    }


    // metodo para cancelar solicitudes de envio (la elimina de una vez)
    public void cancelShipment(String shipmentId){
        Shipment shipmentAux = findShipmentTempById(shipmentId);
        user.getShipments().remove(shipmentAux);
    }


    // metodo para confirmar solicitud de envio cuando ya esta seguro
    public void confirmShipment(String shipmentId){
        Shipment shipmentAux = findShipmentTempById(shipmentId);
        double price = ShippingService.getInstance().calculateBasePrice(shipmentAux);
        shipmentAux.setCreationDate(LocalDateTime.now());
        Rate rate = new Rate("random", shipmentAux.getPackageModel().getVolume(), shipmentAux.getPackageModel().getWeight());

        shipmentAux.setRate(rate);
        shipmentAux.getRate().setBase(price);
    }


    // metodo para buscar una solicitud de envio en la lista de envios de usuario
    private Shipment findShipmentTempById(String shipmentId){
        for (Shipment shipment : user.getShipments()){
            if (shipment.getShipmentId().equals(shipmentId)){
                return shipment;
            }
        }
        throw new NotFoundException("Shipment with ID: " + shipmentId + " not found");
    }


    // metodo para pagar un envío, el envio que llega aca es el que sale de confirmShipment
    public void payShipment(User user, String shipmentId, double amount) {
         Shipment shipment = findShipmentTempById(shipmentId);
         if (user == null) {
            throw new IllegalArgumentException("Usuario");
        }

        if (amount >= shipment.getRate().getBase()) {
            Payment payment = new Payment(
                    "PAY-" + System.currentTimeMillis(),
                    amount,
                    java.time.LocalDateTime.now(),
                    true
            );
            user.getPayments().add(payment);
            shipment.setPayment(payment);

        }
        CompanyService.getInstance().makeShipment(shipment);
    }

    // metodo para consultar el historial de envios con filtro por fecha y estado
     public ArrayList<Shipment> shipmentsHistory(User user, LocalDateTime date, ShippingStatus shippingStatus){
         ArrayList<Shipment> shipmentsHistory = new ArrayList<>();
         for  (Shipment shipment : user.getShipments()){
             if (shipment.getCreationDate().isBefore(date)){
                if (shipment.getStatus().equals(shippingStatus)){
                    shipmentsHistory.add(shipment);
                }
             }
         }
         return shipmentsHistory;
     }
}
