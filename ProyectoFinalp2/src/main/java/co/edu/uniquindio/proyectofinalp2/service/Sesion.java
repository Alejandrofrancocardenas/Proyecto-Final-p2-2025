package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.User;

import java.util.ArrayList;
import java.util.List;

public class Sesion {

    private static User usuarioActual;
    private static List<User> usuariosRegistrados = new ArrayList<>();

    public static User getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(User usuario) {
        usuarioActual = usuario;
    }

    public static List<User> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }

    public static void registrarUsuario(User user) {
        usuariosRegistrados.add(user);
    }

    public static User verificarCredenciales(String correo, String password, String rolSeleccionado) {
        for (User user : usuariosRegistrados) {
            if (user.getEmail().equalsIgnoreCase(correo) &&
                    user.getPassword().equals(password) &&
                    user.getRol().equalsIgnoreCase(rolSeleccionado)) {

                usuarioActual = user;
                return user;
            }
        }
        return null;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

}