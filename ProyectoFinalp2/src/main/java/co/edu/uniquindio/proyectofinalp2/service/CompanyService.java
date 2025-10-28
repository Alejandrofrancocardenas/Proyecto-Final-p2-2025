package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.dto.DealerDTO;
import co.edu.uniquindio.proyectofinalp2.dto.AdminDTO;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectEmailException;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectPasswordException;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyService {
    private static CompanyService instance;
    private List<User> users; // representa la lista de clientes finales
    private List<Admin> admins; // representa la lista de administradores
    private List<Dealer> deliveryMen; //representa la lista de repartidores

    private List<Shipment> shipments; // representa la lista de envios realizados


    private CompanyService() {
        this.users = new ArrayList<>();
        this.deliveryMen = new ArrayList<>();
        this.shipments = new ArrayList<>();
        this.admins = new ArrayList<>();
    }

    public static CompanyService getInstance() {
        if (instance == null) {
            instance = new CompanyService();
        }
        return instance;
    }

    //CRUD de usuarios
    //Create: registrar un usuario a partir de DTO
    public void registerUser(UserDTO dto) {
        User user = new User.Builder()
                .name(dto.getFullname())
                .id(dto.getIdUser())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
        //user.setAddresses(dto.getAddresses());
        users.add(user);
    }

    // Listar usuarios como DTO  para poder devolverlo al controller
    public List<UserDTO> listUserDTO() {
        List<UserDTO> list = new ArrayList<>();
        for (User userAux : users) {
            UserDTO dto = new UserDTO();
            dto.setIdUser(userAux.getId());
            dto.setFullname(userAux.getFullname());
            dto.setEmail(userAux.getEmail());
            dto.setPhone(userAux.getPhone());
            //dto.setAddresses(userAux.getAddresses());
            list.add(dto);
        }
        return list;
    }

    //bucar usuarios por ID
    private Optional<UserDTO> findUserByID(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setIdUser(u.getId());
                    dto.setFullname(u.getFullname());
                    dto.setEmail(u.getEmail());
                    dto.setPhone(u.getPhone());
                    return dto;
                });
    }

    //bucar usuarios por email
    private Optional<UserDTO> findUserByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setIdUser(u.getId());
                    dto.setFullname(u.getFullname());
                    dto.setEmail(u.getEmail());
                    dto.setPhone(u.getPhone());
                    return dto;
                });
    }

    //Read: ver un user
    private UserDTO readUser(String id){

        Optional<UserDTO> userFind = findUserByID(id);
        if (userFind.isPresent()){
            return userFind.get();
        } else {
            throw new NotFoundException("No se encontró ningun usuario con ID: " + id);
        }
    }

    //update: actulizar usuario
    private void updateUser(UserDTO dto) {
        Optional<User> userOp = users.stream()
                .filter(u -> u.getId().equals(dto.getIdUser()))
                .findFirst();

        if (userOp.isPresent()) {
            User user = userOp.get();
            user.setFullname(dto.getFullname());
            user.setEmail(dto.getEmail());
            user.setPhone(dto.getPhone());
            //user.setAddresses(dto.getAddresses());
        } else {
            throw new NotFoundException("No se encontró ningun usuario con ID: " + dto.getIdUser());
        }
    }


    //Delete: eliminar user
    private void deleteUser(String id){
        Optional<User> user = users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst();
        if (user.isPresent()){
            users.remove(user.get());
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
        admins.add(admin);
    }

    // Listar usuarios como DTO  para poder devolverlo al controller
    public List<AdminDTO> listAdminDTO() {
        List<AdminDTO> list = new ArrayList<>();
        for (Admin adminAux : admins) {
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
    private Optional<AdminDTO> findAdminByID(String id) {
        return admins.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .map(a -> {
                    AdminDTO dto = new AdminDTO();
                    dto.setIdAdmin(a.getId());
                    dto.setFullname(a.getFullname());
                    dto.setEmail(a.getEmail());
                    dto.setPhone(a.getPhone());
                    return dto;
                });
    }

    //Read: ver un admin
    private AdminDTO readAdmin(String id){

        Optional<AdminDTO> adminFind = findAdminByID(id);
        if (adminFind.isPresent()){
            return adminFind.get();
        } else {
            throw new NotFoundException("No se encontró ningun Admin con ID: " + id);
        }
    }

    //update: actulizar Admin
    private void updateADmin(AdminDTO dto) {
        Optional<Admin> adminOp = admins.stream()
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
        Optional<Admin> admin = admins.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
        if (admin.isPresent()){
            admins.remove(admin.get());
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
        deliveryMen.add(dealer);
    }

    // Listar repartidores como DTO  para poder devolverlo al controller
    public List<DealerDTO> listDealerDTO() {
        List<DealerDTO> list = new ArrayList<>();
        for (Dealer dealerAux : deliveryMen) {
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
    private Optional<DealerDTO> findDealerByID(String id) {
        return deliveryMen.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .map(d -> {
                    DealerDTO dto = new DealerDTO();
                    dto.setIdDealer(d.getId());
                    dto.setFullname(d.getFullname());
                    dto.setEmail(d.getEmail());
                    dto.setPhone(d.getPhone());
                    return dto;
                });
    }

    //Read: ver un dealer
    private DealerDTO readDealer(String id){

        Optional<DealerDTO> dealerFind = findDealerByID(id);
        if (dealerFind.isPresent()){
            return dealerFind.get();
        } else {
            throw new NotFoundException("No se encontró ningun repartidor con ID: " + id);
        }
    }

    //update: actulizar repartidor
    private void updateDealer(DealerDTO dto) {
        Optional<Dealer> dealerOp = deliveryMen.stream()
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
        Optional<Dealer> dealer = deliveryMen.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
        if (dealer.isPresent()){
            deliveryMen.remove(dealer.get());
        } else {
            throw new NotFoundException("No se encontró ningun repartidor con ID: " + id);
        }
    }

    //metodos de inicio de sesion users
    public UserDTO login(String email, String passwordID) {
        Optional<UserDTO> userOpt = findUserByEmail(email);

        if (userOpt.isEmpty()) {
            throw new IncorrectEmailException("No existe ninguna cuenta con el email: " + email);
        }

        UserDTO user = userOpt.get();

        if (!user.getIdUser().equals(passwordID)) {
            throw new IncorrectPasswordException("Incorrect Password, please try again");
        }

        // si pasa esta logica, sigifica que el usuario es valido
        // devuelve el user para que el controlador decida que hacer con el
        return user;
    }


    // metodo que recibe una solicitud de envio y hace el proceso para enviar
    public static void makeShipment(Shipment shipement){
        shipement.setStatus(ShippingStatus.ENROUTE);
    }


    // metodo para ratrear el estado del envio
    public static String trackerShipment(Shipment shipment){
        if (shipment != null){
            return shipment.track();
        } else {
            throw new NotFoundException("No se logró encontrar el envío que pusiste");
        }
    }

}
