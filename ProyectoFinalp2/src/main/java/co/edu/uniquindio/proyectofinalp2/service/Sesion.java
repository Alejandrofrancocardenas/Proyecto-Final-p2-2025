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

    /**
     * Verifica credenciales, establece el usuario actual y devuelve el objeto User.
     * @param correo El correo ingresado.
     * @param password La contraseña ingresada.
     * @param rolSeleccionado El rol seleccionado.
     * @return El objeto User si el login es exitoso, null si falla.
     */
    public static User verificarCredenciales(String correo, String password, String rolSeleccionado) {
        for (User user : usuariosRegistrados) {
            if (user.getEmail().equalsIgnoreCase(correo) &&
                    user.getPassword().equals(password) &&
                    user.getRol().equalsIgnoreCase(rolSeleccionado)) {

                usuarioActual = user;
                return user; // Devuelve el objeto User
            }
        }
        return null; // Devuelve null si no hay coincidencia
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

}