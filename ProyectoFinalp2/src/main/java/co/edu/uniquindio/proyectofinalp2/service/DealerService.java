package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import java.util.*;

public class DealerService {

    private final CompanyService companyService;

    public DealerService(CompanyService companyService) {
        this.companyService = companyService;
    }

    //Buscar Dealer
    public Optional<Dealer> findDealerById(String id) {
        return companyService.findDealerByID(id);
    }

    // RF-019: Crear repartidor
    public boolean addDealer(Dealer dealer) {
        if (findDealerById(dealer.getId()).isPresent()) {
            System.out.println("⚠️ Ya existe un repartidor con ID: " + dealer.getId());
            return false;
        }

        companyService.getCompany().getDealers().add(dealer);
        System.out.println("✅ Repartidor añadido correctamente: " + dealer.getFullname());
        return true;
    }

    // RF-019: Actualizar repartidor
    public boolean updateDealer(Dealer updatedDealer) {
        Optional<Dealer> existingDealer = findDealerById(updatedDealer.getId());
        if (existingDealer.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + updatedDealer.getId());
            return false;
        }

        Dealer dealer = existingDealer.get();
        dealer.setFullname(updatedDealer.getFullname());
        dealer.setPhone(updatedDealer.getPhone());
        dealer.setEmail(updatedDealer.getEmail());
        dealer.setAvaliable(updatedDealer.getAvaliable());
        dealer.setDeliveriesMade(updatedDealer.getDeliveriesMade());

        System.out.println("✅ Repartidor actualizado: " + dealer.getFullname());
        return true;
    }

    // RF-019: Eliminar repartidor
    public boolean deleteDealer(String idDealer) {
        Optional<Dealer> dealerOpt = findDealerById(idDealer);
        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + idDealer);
            return false;
        }

        companyService.getCompany().getDealers().remove(dealerOpt.get());
        System.out.println("🗑️ Repartidor eliminado con éxito.");
        return true;
    }

    // RF-020: Cambiar disponibilidad
    public boolean changeDealerAvailability(String idDealer, boolean available) {
        Optional<Dealer> dealerOpt = findDealerById(idDealer);
        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + idDealer);
            return false;
        }

        Dealer dealer = dealerOpt.get();
        dealer.setAvaliable(available);
        System.out.println("🔄 Estado de disponibilidad actualizado: " + (available ? "🟢 Disponible" : "🔴 No disponible"));
        return true;
    }

    // RF-021: Consultar envíos asignados
    public void showShipmentsByDealer(String idDealer) {
        Optional<Dealer> dealerOpt = findDealerById(idDealer);
        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + idDealer);
            return;
        }

        Dealer dealer = dealerOpt.get();
        List<Shipment> shipments = dealer.getAssignedShipments();

        if (shipments == null || shipments.isEmpty()) {
            System.out.println("📦 El repartidor no tiene envíos asignados.");
            return;
        }

        System.out.println("📋 Envíos asignados al repartidor " + dealer.getFullname() + ":");
        for (Shipment s : shipments) {
            System.out.println("------------------------------------------");
            System.out.println("🆔 ID Envío: " + s.getShipmentId());
            System.out.println("🚚 Estado: " + s.getStatus());
            System.out.println("📍 Zona: " + s.getZone());
        }
        System.out.println("------------------------------------------");
    }

}
