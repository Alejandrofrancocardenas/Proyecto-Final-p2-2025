package co.edu.uniquindio.proyectofinalp2.exceptions;

public class IncorrectEmailException extends RuntimeException {
    public IncorrectEmailException() {
    }

    public IncorrectEmailException(String s) {
        super(s);
    }
}
