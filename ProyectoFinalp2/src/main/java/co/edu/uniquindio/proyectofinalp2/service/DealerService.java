package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import java.util.*;
import java.util.UUID; // Importado para generar IDs únicos

public class DealerService {

    private static DealerService instance;

    private final CompanyService companyService;
    private final Company company;

    public DealerService() {
        this.companyService = CompanyService.getInstance();
        this.company = Company.getInstance();
    }

    public static DealerService getInstance() {
        if (instance == null) {
            instance = new DealerService();
        }
        return instance;
    }

    public Optional<Dealer> findDealerById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }

        return company.getDealers().stream()
                .filter(d -> id.equals(d.getId()))
                .findFirst();
    }

    public List<Dealer> listAllDealers() {
        return company.getDealers();
    }

    public boolean addDealer(Dealer dealer) {
        if (dealer == null) {
            System.err.println("⚠️ No se puede añadir un repartidor nulo.");
            return false;
        }

        if (dealer.getId() == null || dealer.getId().trim().isEmpty()) {
            String newId = UUID.randomUUID().toString();
            dealer.setId(newId);

            System.out.println("🔑 ID generado automáticamente: " + newId);
        } else {
            if (findDealerById(dealer.getId()).isPresent()) {
                System.out.println("⚠️ Ya existe un repartidor con ID: " + dealer.getId());
                return false;
            }
        }

        company.getDealers().add(dealer);
        System.out.println("✅ Repartidor añadido correctamente con ID: " + dealer.getId() + " - " + dealer.getFullname());
        return true;
    }

    public boolean updateDealer(Dealer updatedDealer) {
        if (updatedDealer == null || updatedDealer.getId() == null) {
            System.err.println("⚠️ No se puede actualizar un repartidor nulo o sin ID.");
            return false;
        }

        Optional<Dealer> existingDealer = findDealerById(updatedDealer.getId());
        if (existingDealer.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + updatedDealer.getId());
            return false;
        }

        Dealer dealer = existingDealer.get();
        dealer.setFullname(updatedDealer.getFullname());
        dealer.setPhone(updatedDealer.getPhone());
        dealer.setEmail(updatedDealer.getEmail());
        dealer.setAvailable(updatedDealer.getAvailable());
        dealer.setDeliveriesMade(updatedDealer.getDeliveriesMade());

        System.out.println("✅ Repartidor actualizado: " + dealer.getFullname());
        return true;
    }

    public boolean deleteDealer(String idDealer) {
        Optional<Dealer> dealerOpt = findDealerById(idDealer);
        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + idDealer);
            return false;
        }

        company.getDealers().remove(dealerOpt.get());
        System.out.println("🗑️ Repartidor eliminado con éxito.");
        return true;
    }

    public boolean changeDealerAvailability(String idDealer, boolean available) {
        Optional<Dealer> dealerOpt = findDealerById(idDealer);
        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + idDealer);
            return false;
        }

        Dealer dealer = dealerOpt.get();
        dealer.setAvailable(available);
        System.out.println("🔄 Estado de disponibilidad actualizado: " + (available ? "🟢 Disponible" : "🔴 No disponible"));
        return true;
    }

    public void showShipmentsByDealer(String idDealer) {
        Optional<Dealer> dealerOpt = findDealerById(idDealer);
        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + idDealer);
            return;
        }

        Dealer dealer = dealerOpt.get();
        List<Shipment> shipments = dealer.getAssignedShipments();

        if (shipments == null || shipments.isEmpty()) {
            System.out.println("📦 El repartidor " + dealer.getFullname() + " no tiene envíos asignados.");
            return;
        }

        System.out.println("📋 Envíos asignados al repartidor " + dealer.getFullname() + ":");
        for (Shipment s : shipments) {
            System.out.println("------------------------------------------");
            System.out.println("🆔 ID Envío: " + "ID NO DISPONIBLE EN LA CLASE BASE");
            System.out.println("🚚 Estado: " + s.getStatus());
        }
        System.out.println("------------------------------------------");
    }
}