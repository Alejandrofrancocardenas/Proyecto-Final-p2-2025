package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    List<User> users = new ArrayList<>();

    // Registrar un usuario a partir de DTO
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

}
