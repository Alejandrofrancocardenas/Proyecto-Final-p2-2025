package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.decorators.FragileShipment; // 🟢 Importar decoradores
import co.edu.uniquindio.proyectofinalp2.decorators.PriorityShipping; // 🟢 Importar decoradores
import co.edu.uniquindio.proyectofinalp2.dto.AdminDTO;
import co.edu.uniquindio.proyectofinalp2.dto.DealerDTO;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectEmailException;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectPasswordException;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotFoundException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyService {
    private static CompanyService instance;
    public final Company company;


    private static final String USERS_FILE = "data/company_users.dat";
    private static final String ADMINS_FILE = "data/company_admins.dat";
    private static final String DEALERS_FILE = "data/company_dealers.dat";
    private static final String SHIPMENTS_FILE = "data/company_shipments.dat";

    private CompanyService() {
        this.company = Company.getInstance();
    }

    public static CompanyService getInstance() {
        if (instance == null) {
            instance = new CompanyService();
        }
        return instance;
    }


    public Optional<User> findUserByEmail(String email) {
        return company.getUsers().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Admin> findAdminByEmail(String email) {
        return company.getAdmins().stream()
                .filter(a -> a.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Dealer> findDealerByEmail(String email) {
        return company.getDealers().stream()
                .filter(dealer -> dealer.getEmail().equals(email))
                .findFirst();
    }



    public void registerUser(User user) {
        Optional<User> userAux = findUserByEmail(user.getEmail());
        if (userAux.isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta con el email: "+ user.getEmail());
        }
        company.getUsers().add(user);
    }

    public void registerAdmin(Admin admin) {
        Optional<Admin> adminAux = findAdminByEmail(admin.getEmail());
        if (adminAux.isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta Admin con el email: " + admin.getEmail());
        }
        company.getAdmins().add(admin);

    }

    public void registerDealer(Dealer dealer) {
        Optional<Dealer> dealerAux = findDealerByEmail(dealer.getEmail());
        if (dealerAux.isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta Repartidor con el email: " + dealer.getEmail());
        }
        company.getDealers().add(dealer);

    }

    public User login(String email, String password) {
        Optional<User> user = findUserByEmail(email);

        if (user.isEmpty()) {
            throw new IncorrectEmailException("No existe ninguna cuenta de usuario con el email: " + email);
        }

        User userAux = user.get();
        if (userAux.getPassword() == null || !userAux.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Contraseña incorrecta, por favor inténtalo de nuevo.");
        }
        return userAux;
    }

    public Admin loginAdmin(String email, String password) {
        Optional<Admin> admin = findAdminByEmail(email);

        if (admin.isEmpty()) {
            throw new IncorrectEmailException("No existe ninguna cuenta de administrador con el email: " + email);
        }

        Admin adminAux = admin.get();
        if (adminAux.getPassword() == null || !adminAux.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Contraseña incorrecta, por favor inténtalo de nuevo.");
        }
        return adminAux;
    }

    public Dealer loginDealer(String email, String password) {
        Optional<Dealer> dealer = findDealerByEmail(email);

        if (dealer.isEmpty()) {
            throw new IncorrectEmailException("No existe ninguna cuenta de repartidor con el email: " + email);
        }

        Dealer dealerAux = dealer.get();
        if (dealerAux.getPassword() == null || !dealerAux.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Contraseña incorrecta, por favor inténtalo de nuevo.");
        }
        return dealerAux;
    }


    public List<String> getAvailableDecorators() {
        List<String> decorators = new ArrayList<>();
        decorators.add("Fragile");
        decorators.add("Urgent");
        decorators.add("SecureShipping");
        decorators.add("SignatureRequiredShipment");
        return decorators;
    }


    public void makeShipment(Shipment shipment) {
        this.company.getShipments().add(shipment);
        System.out.println("📦 Envío registrado con éxito: " + shipment.getShipmentId());
    }

    public void addShipmentToCompany(Shipment shipment) {
        this.company.getShipments().add(shipment);
        System.out.println("📦 Envío " + shipment.getShipmentId() + " registrado centralmente y persistido.");
    }


}