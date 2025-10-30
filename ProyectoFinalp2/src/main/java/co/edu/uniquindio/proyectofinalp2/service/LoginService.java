package co.edu.uniquindio.proyectofinalp2.service;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

    private static LoginService instance;
    private final Map<String, String> usuariosRegistrados = new HashMap<>();

    private LoginService() {}

    public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    public void registrarUsuario(String correo, String contrasena) {
        if (usuariosRegistrados.containsKey(correo)) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }
        usuariosRegistrados.put(correo, contrasena);
    }

    public boolean verificarCredenciales(String correo, String contrasena) {
        return usuariosRegistrados.containsKey(correo)
                && usuariosRegistrados.get(correo).equals(contrasena);
    }
}
