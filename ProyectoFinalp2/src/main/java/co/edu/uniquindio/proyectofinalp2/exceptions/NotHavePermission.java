package co.edu.uniquindio.proyectofinalp2.exceptions;

public class NotHavePermission extends RuntimeException {
    public NotHavePermission() {
    }

    public NotHavePermission(String s) {
        super(s);
    }
}
