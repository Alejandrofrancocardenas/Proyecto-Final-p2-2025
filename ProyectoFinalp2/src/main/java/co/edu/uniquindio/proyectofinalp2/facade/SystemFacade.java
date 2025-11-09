package co.edu.uniquindio.proyectofinalp2.facade;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.dto.*;
import co.edu.uniquindio.proyectofinalp2.exceptions.*;
import co.edu.uniquindio.proyectofinalp2.service.*;
import co.edu.uniquindio.proyectofinalp2.chain.PaymentProcessor;

import java.time.LocalDate;
import java.util.*;

/**
 * SystemFacade
 *
 * This Facade centralizes access to all the system services,
 * offering a single interaction point for controllers or graphical interfaces.
 */
public class SystemFacade {

    // ==============================================================
    // 🔹 SERVICE INSTANCES
    // ==============================================================
    private final CompanyService companyService;
    private final AdminService adminService;
    private final DealerService dealerService;
    private final LoginService loginService;
    private final ReportService reportService;
    private final ShippingService shippingService;

    public SystemFacade() {
        this.companyService = CompanyService.getInstance();
        this.adminService = new AdminService();
        this.dealerService = new DealerService(companyService);
        this.loginService = LoginService.getInstance();
        this.reportService = ReportService.getInstance();
        this.shippingService = ShippingService.getInstance();
    }


    //  LOGIN SECCION

    public void registerUserLogin(String email, String password) {
        loginService.registrarUsuario(email, password);
    }

    public boolean verifyLoginCredentials(String email, String password) {
        return loginService.verificarCredenciales(email, password);
    }

    public boolean verifySessionCredentials(String email, String password, String role) {
        return Sesion.verificarCredenciales(email, password, role);
    }

    public void logout() {
        Sesion.cerrarSesion();
    }

    public User getCurrentUser() {
        return Sesion.getUsuarioActual();
    }


    //  USUARIOS (CRUD)

    public void registerUser(User user) {
        companyService.registerUser(user);
    }

    public UserDTO viewUser(String id) {
        return companyService.readUser(id);
    }

    public void updateUser(UserDTO dto) {
        companyService.updateUser(dto);
    }

    public void deleteUser(String id) {
        companyService.deleteUser(id);
    }

    public List<UserDTO> listUsers() {
        return companyService.listUserDTO();
    }

    public Optional<User> findUserById(String id) {
        return companyService.findUserByID(id);
    }


    // ADMIN SECCION

    public boolean addUserAsAdmin(User newUser) {
        return adminService.addUserAdmin(newUser);
    }

    public boolean deleteUserAsAdmin(User newUser) {
        return adminService.deleteUserAdmin(newUser);
    }

    public boolean updateUserAsAdmin(User newUser) {
        return adminService.updateUserAdmin(newUser);
    }

    public void showAllUsersAsAdmin() {
        adminService.showAllUsersAdmin();
    }

    public boolean addDealerAsAdmin(Dealer newDealer) {
        return adminService.addDealerAdmin(newDealer);
    }

    public boolean deleteDealerAsAdmin(Dealer newDealer) {
        return adminService.deleteDealerAdmin(newDealer);
    }

    public boolean updateDealerAsAdmin(Dealer newDealer) {
        return adminService.updateDealerAdmin(newDealer);
    }

    public void showAllDealersAsAdmin() {
        adminService.showAllDealersAdmin();
    }

    public boolean assignShipmentToDealer(String shipmentId, String dealerId) {
        return adminService.assignOrReassignShipment(shipmentId, dealerId);
    }

    public boolean registerShipmentIncidence(String shipmentId, Incidence incidence) {
        return adminService.registerShipmentIncidence(shipmentId, incidence);
    }

    public boolean updateShipmentStatus(String shipmentId, ShippingStatus status) {
        return adminService.updateShipmentStatus(shipmentId, status);
    }

    // METRICAS

    public Map<String, Double> getAverageDeliveryTimeByZone() {
        return adminService.getAverageDeliveryTimeByZone();
    }

    public Map<String, Long> getMostUsedAdditionalServices() {
        return adminService.getMostUsedAdditionalServices();
    }

    public Map<String, Double> getIncomeByPeriod() {
        return adminService.getIncomeByPeriod();
    }

    public Map<String, Long> getIncidencesByZone() {
        return adminService.getIncidencesByZone();
    }


    // DEALER SECCION

    public boolean addDealer(Dealer dealer) {
        return dealerService.addDealer(dealer);
    }

    public boolean updateDealer(Dealer dealer) {
        return dealerService.updateDealer(dealer);
    }

    public boolean deleteDealer(String id) {
        return dealerService.deleteDealer(id);
    }

    public boolean changeDealerAvailability(String dealerId, boolean available) {
        return dealerService.changeDealerAvailability(dealerId, available);
    }

    public void showShipmentsByDealer(String dealerId) {
        dealerService.showShipmentsByDealer(dealerId);
    }


    //  ENVIO SECCION

    public double calculateShipmentRate(Shipment shipment) {
        return shippingService.calculateBasePrice(shipment);
    }

    public Shipment applyShipmentDecorators(Shipment shipment, boolean priority, boolean fragile, boolean secure, boolean signature) {
        return shippingService.applyDecorators(shipment, priority, fragile, secure, signature);
    }

    public void createShipment(Shipment shipment) {
        companyService.makeShipment(shipment);
    }

    public List<Shipment> filterShipments(LocalDate date, ShippingStatus status, String zone) {
        return companyService.filterShipments(date, status, zone);
    }

    public String trackShipment(Shipment shipment) {
        return companyService.trackerShipment(shipment);
    }


    // REPORTES SECCIONES

    public void generateCsvReport(List<Shipment> shipments, String path) {
        reportService.generateCsvReport(shipments, path);
    }

    public void generatePdfReport(List<Shipment> shipments, String path) {
        reportService.generatePdfReport(shipments, path);
    }


    //  UTILIDADES

    public Company getCompany() {
        return companyService.getCompany();
    }

    public void processPaymentChain(Payment payment) {
        PaymentProcessor processor = new PaymentProcessor();
        processor.processPayment(payment);
    }

}
