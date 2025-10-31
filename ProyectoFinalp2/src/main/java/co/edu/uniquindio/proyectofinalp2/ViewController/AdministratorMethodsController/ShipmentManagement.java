package co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController;

import co.edu.uniquindio.proyectofinalp2.Model.Dealer;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.Model.ShippingStatus;
import co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador encargado de la gestión de envíos.
 * Permite registrar, actualizar, buscar y eliminar envíos.
 */
public class ShipmentManagement {

    private List<Shipment> shipments;

    public ShipmentManagement() {
        this.shipments = new ArrayList<>();
    }
    private AdministratorController administratorController;
    public void setAdministratorController(AdministratorController administratorController) {
        this.administratorController = administratorController;
    }
    // ================== MÉTODOS PRINCIPALES ==================

    /**
     * Registra un nuevo envío en el sistema.
     */
    public boolean registerShipment(Shipment shipment) {
        if (shipment == null) return false;

        boolean exists = shipments.stream()
                .anyMatch(s -> s.getShipmentId().equalsIgnoreCase(shipment.getShipmentId()));

        if (exists) {
            System.out.println("❌ El envío con ID " + shipment.getShipmentId() + " ya existe.");
            return false;
        }

        shipments.add(shipment);
        System.out.println("✅ Envío registrado correctamente: " + shipment.getShipmentId());
        return true;
    }

    /**
     * Asigna un dealer a un envío existente.
     */
    public boolean assignDealerToShipment(String shipmentId, Dealer dealer) {
        Shipment shipment = findShipmentById(shipmentId);
        if (shipment == null) {
            System.out.println("❌ No se encontró el envío con ID: " + shipmentId);
            return false;
        }

        shipment.setAssignedDealer(dealer);
        System.out.println("✅ Dealer " + dealer.getFullname() + " asignado al envío " + shipmentId);
        return true;
    }

    /**
     * Actualiza el estado del envío.
     */
    public boolean updateShipmentStatus(String shipmentId, ShippingStatus newStatus) {
        Shipment shipment = findShipmentById(shipmentId);
        if (shipment == null) return false;

        shipment.setStatus(newStatus);
        System.out.println("📦 Estado actualizado a '" + newStatus + "' para el envío " + shipmentId);
        return true;
    }

    /**
     * Elimina un envío del sistema.
     */
    public boolean removeShipment(String shipmentId) {
        Optional<Shipment> shipmentOpt = shipments.stream()
                .filter(s -> s.getShipmentId().equalsIgnoreCase(shipmentId))
                .findFirst();

        if (shipmentOpt.isEmpty()) {
            System.out.println("❌ No se encontró el envío con ID: " + shipmentId);
            return false;
        }

        shipments.remove(shipmentOpt.get());
        System.out.println("🗑️ Envío eliminado: " + shipmentId);
        return true;
    }

    /**
     * Busca un envío por su ID.
     */
    public Shipment findShipmentById(String shipmentId) {
        return shipments.stream()
                .filter(s -> s.getShipmentId().equalsIgnoreCase(shipmentId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene todos los envíos registrados.
     */
    public List<Shipment> getAllShipments() {
        return shipments;
    }

    /**
     * Filtra los envíos según su estado (por ejemplo, "Pendiente" o "Entregado").
     */
    public List<Shipment> getShipmentsByStatus(ShippingStatus status) {
        List<Shipment> filtered = new ArrayList<>();
        for (Shipment s : shipments) {
            if (s.getStatus().equals(status)) {
                filtered.add(s);
            }
        }
        return filtered;
    }

    /**
     * Calcula el promedio de tiempo de entrega en horas.
     */
    public double calculateAverageDeliveryTime() {
        if (shipments.isEmpty()) return 0;

        double total = 0;
        int count = 0;
        for (Shipment s : shipments) {
            if (s.getEstimatedDeliveryDate() > 0) {
                total += s.getEstimatedDeliveryDate();
                count++;
            }
        }

        return count > 0 ? total / count : 0;
    }
}
