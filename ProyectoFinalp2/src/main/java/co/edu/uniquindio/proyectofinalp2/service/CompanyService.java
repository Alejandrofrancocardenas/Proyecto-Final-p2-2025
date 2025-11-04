package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.dto.DealerDTO;
import co.edu.uniquindio.proyectofinalp2.dto.AdminDTO;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectEmailException;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectPasswordException;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyService {
    private static CompanyService instance;

    private final Company company;


    private CompanyService() {
        this.company = new Company();
    }

    public static CompanyService getInstance() {
        if (instance == null) {
            instance = new CompanyService();
        }
        return instance;
    }

    //CRUD de usuarios
    //Create: registrar un usuario
    public void registerUser(User user) {
        Optional<User> userAux = findUserByID(user.getId());
        if (userAux.isPresent()) {
            throw new IllegalArgumentException("No pueden haber 2 usuarios con id "+ user.getId());
        }
        company.getUsers().add(user);
    }

    // Listar usuarios como DTO  para poder devolverlo al controller
    public List<UserDTO> listUserDTO() {
        List<UserDTO> list = new ArrayList<>();
        for (User userAux : company.getUsers()) {
            UserDTO dto = new UserDTO();
            dto.setIdUser(userAux.getId());
            dto.setFullname(userAux.getFullname());
            dto.setEmail(userAux.getEmail());
            dto.setPhone(userAux.getPhone());
            dto.setAddresses(userAux.getAddresses());
            list.add(dto);
        }
        return list;
    }

    //bucar usuarios por ID
    public Optional<User> findUserByID(String id) {
        return company.getUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    //bucar usuarios por email
    public Optional<User> findUserByEmail(String email) {
        return company.getUsers().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    //Read: ver un user
    public UserDTO readUser(String id){

        Optional<User> userFind = findUserByID(id);
        if (userFind.isPresent()){
            User userAux = userFind.get();
            UserDTO dto = new UserDTO();
            dto.setIdUser(userAux.getId());
            dto.setFullname(userAux.getFullname());
            dto.setEmail(userAux.getEmail());
            dto.setPhone(userAux.getPhone());
            dto.setAddresses(userAux.getAddresses());
            return dto;
        } else {
            throw new NotFoundException("No se encontró ningun usuario con ID: " + id);
        }
    }

    //update: actulizar usuario
    public void updateUser(UserDTO dto) {
        Optional<User> userOp = company.getUsers().stream()
                .filter(u -> u.getId().equals(dto.getIdUser()))
                .findFirst();

        if (userOp.isPresent()) {
            User user = userOp.get();
            user.setFullname(dto.getFullname());
            user.setEmail(dto.getEmail());
            user.setPhone(dto.getPhone());
            user.setAddresses(dto.getAddresses());
        } else {
            throw new NotFoundException("No se encontró ningun usuario con ID: " + dto.getIdUser());
        }
    }


    //Delete: eliminar user
    public void deleteUser(String id){
        Optional<User> user = company.getUsers().stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst();
        if (user.isPresent()){
            company.getUsers().remove(user.get());
        } else {
            throw new NotFoundException("No se encontró ningun usuario con ID: " + id);
        }
    }

    //CRUD para Admins
    //Create: registrar un usuario a partir de DTO
    public void registerAdmin(UserDTO dto) {
        Admin admin = new Admin.Builder()
                .name(dto.getFullname())
                .id(dto.getIdUser())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
        company.getAdmins().add(admin);
    }

    // Listar usuarios como DTO  para poder devolverlo al controller
    public List<AdminDTO> listAdminDTO() {
        List<AdminDTO> list = new ArrayList<>();
        for (Admin adminAux : company.getAdmins()) {
            AdminDTO dto = new AdminDTO();
            dto.setIdAdmin(adminAux.getId());
            dto.setFullname(adminAux.getFullname());
            dto.setEmail(adminAux.getEmail());
            dto.setPhone(adminAux.getPhone());
            list.add(dto);
        }
        return list;
    }

    //bucar Admin por ID
    public Optional<Admin> findAdminByID(String id) {
        return company.getAdmins().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    //Read: ver un admin
    private AdminDTO readAdmin(String id){

        Optional<Admin> adminFind = findAdminByID(id);
        if (adminFind.isPresent()){
            Admin adminAux = adminFind.get();
            AdminDTO dto = new AdminDTO();
            dto.setIdAdmin(adminAux.getId());
            dto.setFullname(adminAux.getFullname());
            dto.setEmail(adminAux.getEmail());
            dto.setPhone(adminAux.getPhone());
            return dto;
        } else {
            throw new NotFoundException("No se encontró ningun Admin con ID: " + id);
        }
    }

    //update: actulizar Admin
    public void updateAdmin(AdminDTO dto) {
        Optional<Admin> adminOp = company.getAdmins().stream()
                .filter(a -> a.getId().equals(dto.getIdAdmin()))
                .findFirst();

        if (adminOp.isPresent()) {
            Admin admin = adminOp.get();
            admin.setFullname(dto.getFullname());
            admin.setEmail(dto.getEmail());
            admin.setPhone(dto.getPhone());
        } else {
            throw new NotFoundException("No se encontró ningun Admin con ID: " + dto.getIdAdmin());
        }
    }


    //Delete: eliminar Admin
    private void deleteAdmin(String id){
        Optional<Admin> admin = company.getAdmins().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
        if (admin.isPresent()){
            company.getAdmins().remove(admin.get());
        } else {
            throw new NotFoundException("No se encontró ningun admin con ID: " + id);
        }
    }


    //CRUD para Repartidores
    //Create: registrar un repartidor a partir de DTO
    public void registerDealer(DealerDTO dto) {
        Dealer dealer = new Dealer.Builder()
                .name(dto.getFullname())
                .id(dto.getIdDealer())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
        company.getDealers().add(dealer);
    }

    // Listar repartidores como DTO  para poder devolverlo al controller
    public List<DealerDTO> listDealerDTO() {
        List<DealerDTO> list = new ArrayList<>();
        for (Dealer dealerAux : company.getDealers()) {
            DealerDTO dto = new DealerDTO();
            dto.setIdDealer(dealerAux.getId());
            dto.setFullname(dealerAux.getFullname());
            dto.setEmail(dealerAux.getEmail());
            dto.setPhone(dealerAux.getPhone());
            list.add(dto);
        }
        return list;
    }

    //bucar Dealer por ID
    public Optional<Dealer> findDealerByID(String id) {
        return company.getDealers().stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    //Read: ver un dealer
    private DealerDTO readDealer(String id){

        Optional<Dealer> dealerFind = findDealerByID(id);
        if (dealerFind.isPresent()){
            Dealer dealerAux = dealerFind.get();
            DealerDTO dto = new DealerDTO();
            dto.setIdDealer(dealerAux.getId());
            dto.setFullname(dealerAux.getFullname());
            dto.setEmail(dealerAux.getEmail());
            dto.setPhone(dealerAux.getPhone());
            return dto;
        } else {
            throw new NotFoundException("No se encontró ningun repartidor con ID: " + id);
        }
    }


    //update: actulizar repartidor
    private void updateDealer(DealerDTO dto) {
        Optional<Dealer> dealerOp = company.getDealers().stream()
                .filter(d -> d.getId().equals(dto.getIdDealer()))
                .findFirst();

        if (dealerOp.isPresent()) {
            Dealer dealer = dealerOp.get();
            dealer.setFullname(dto.getFullname());
            dealer.setEmail(dto.getEmail());
            dealer.setPhone(dto.getPhone());
        } else {
            throw new NotFoundException("No se encontró ningun repartidor con ID: " + dto.getIdDealer());
        }
    }


    //Delete: eliminar repartidor
    private void deleteDealer(String id){
        Optional<Dealer> dealer = company.getDealers().stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
        if (dealer.isPresent()){
            company.getDealers().remove(dealer.get());
        } else {
            throw new NotFoundException("No se encontró ningun repartidor con ID: " + id);
        }
    }

    //metodos de inicio de sesion users
    public UserDTO login(String email, String password) {
        Optional<User> user = findUserByEmail(email);

        if (user.isEmpty()) {
            throw new IncorrectEmailException("No existe ninguna cuenta con el email: " + email);
        }

        User userAux = user.get();
        if (!userAux.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Incorrect Password, please try again");
        }

        UserDTO dto = new UserDTO();
        dto.setIdUser(userAux.getId());
        dto.setFullname(userAux.getFullname());
        dto.setEmail(userAux.getEmail());
        dto.setPhone(userAux.getPhone());
        dto.setAddresses(userAux.getAddresses());
        return dto;
        // si pasa esta logica, sigifica que el usuario es valido
        // devuelve el user para que el controlador decida que hacer con el
    }


    // metodo que recibe una solicitud de envio y hace el proceso para enviar, requiere que el pago sea true
    public void makeShipment(Shipment shipment){
        //asignar repartidor
        shipment.setPeriod("toca poner una fecha");
        shipment.setStatus(ShippingStatus.ENROUTE);
        company.getShipments().add(shipment);
    }


    // metodo para ratrear el estado del envio
    public String trackerShipment(Shipment shipment){
        if (shipment != null){
            return shipment.track();
        } else {
            throw new NotFoundException("No se logró encontrar el envío que pusiste");
        }
    }

    public List<Shipment> filterShipments(LocalDate date, ShippingStatus status, String zone) {
        return company.getShipments().stream()
                .filter(s -> (date == null || s.getCrationDate().toLocalDate().equals(date)))
                .filter(s -> (status == null || s.getStatus() == status))
                .filter(s -> (zone == null || s.getZone().equalsIgnoreCase(zone)))
                .toList();
    }

//    public Shipment getShipmentDetails(String shipmentId) {
//        return findShipmentById(shipmentId);
//    }

    public Company getCompany() {
        return this.company;
    }
}
