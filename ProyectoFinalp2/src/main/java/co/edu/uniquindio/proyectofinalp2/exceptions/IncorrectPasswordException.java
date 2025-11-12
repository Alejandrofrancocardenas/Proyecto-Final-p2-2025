package co.edu.uniquindio.proyectofinalp2.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
    }

    public IncorrectPasswordException(String s) {
        super(s);
    }
}
