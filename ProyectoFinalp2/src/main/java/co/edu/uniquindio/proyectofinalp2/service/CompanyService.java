package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.Repartidor;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotFoundException;
import co.edu.uniquindio.proyectofinalp2.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyService {
    private List<User> users; // representa la lista de clientes finales
    //private List<Admin> admins; // representa la lista de administradores
    //debemos cambiarlo a ingles
    private List<Repartidor> repartidores; //representa la lista de repartidores

    //private List<Shipment> shipments; // representa la lista de envios realizados


    public CompanyService() {
        this.users = new ArrayList<>();
        this.repartidores = new ArrayList<>();
    }

    //CRUD de usuarios
    //Create: registrar un usuario a partir de DTO
    public void register(UserDTO dto) {
        User user = new User.Builder()
                .name(dto.getFullname())
                .id(dto.getIdUser())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
        user.setAddresses(dto.getAddresses());
        users.add(user);
    }

    // Listar usuarios como DTO  para poder devolverlo al controller
    public List<UserDTO> list() {
        List<UserDTO> list = new ArrayList<>();
        for (User userAux : users) {
            UserDTO dto = new UserDTO();
            dto.setIdUser(userAux.getIdUser());
            dto.setFullname(userAux.getFullname());
            dto.setEmail(userAux.getEmail());
            dto.setPhone(userAux.getPhone());
            dto.setAddresses(userAux.getAddresses());
            list.add(dto);
        }
        return list;
    }

    //bucar usuarios por ID
    private Optional<UserDTO> findUserByID(String id){
        return users.stream()
                .filter(u -> u.getIdUser().equals(id))
                .map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setIdUser(u.getIdUser());
                    dto.setFullname(u.getFullname());
                    dto.setEmail(u.getEmail());
                    dto.setPhone(u.getPhone());
                    dto.setAddresses(u.getAddresses());
                    return dto;
                })
                .findFirst();
    }

    //Read: ver una mascota
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
                .filter(u -> u.getIdUser().equals(dto.getIdUser()))
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
    private void deleteUser(String id){
        Optional<User> user = users.stream()
                    .filter(u -> u.getIdUser().equals(id))
                    .findFirst();
        if (user.isPresent()){
            users.remove(user.get());
        } else {
            throw new NotFoundException("No se encontró ningun usuario con ID: " + id);
        }
    }

}
