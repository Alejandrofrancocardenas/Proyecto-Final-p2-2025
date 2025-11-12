package co.edu.uniquindio.proyectofinalp2.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa la información de un pago asociado a un envío.
 */
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String paymentMethod;     // Ej: "Efectivo", "Tarjeta", "Transferencia", etc.
    private double amount;            // Monto del pago
    private LocalDateTime paymentDate;  // Fecha y hora en que se realizó el pago
    private boolean isPaid;           // Estado del pago (true = pagado)
    private User user; // El atributo User que se agregó.

    // --- CONSTRUCTORES ---

    /**
     * CONSTRUCTOR PRINCIPAL (5 ARGUMENTOS): Inicializa un nuevo pago con todos los detalles.
     * Este es el constructor al que deben llamar los otros.
     *
     * @param paymentMethod Método de pago utilizado.
     * @param amount Monto total pagado.
     * @param paymentDate Fecha y hora del pago.
     * @param isPaid Estado de la transacción.
     * @param user El usuario asociado a este pago.
     */
    public Payment(String paymentMethod, double amount, LocalDateTime paymentDate, boolean isPaid, User user) {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.isPaid = isPaid;
        this.user = user;
    }

    /**
     * Constructor usado en la clase Shipment para inicialización básica (costo base).
     * Corrige el error llamando al constructor de 5 argumentos.
     * * @param amount Monto inicial del envío.
     * @param isPaid Estado inicial.
     */
    public Payment(double amount, boolean isPaid) {
        // CORRECCIÓN: Ahora llama al constructor de 5 argumentos, pasando 'null' para el User.
        this("Pendiente", amount, null, isPaid, null);
    }

    /**
     * Constructor vacío por defecto (si es necesario para serialización o frameworks).
     * Corrige el error llamando al constructor de 5 argumentos.
     */
    public Payment() {
        // CORRECCIÓN: Ahora llama al constructor de 5 argumentos, pasando 'N/A', 0.0, false, y 'null' para el User.
        this("N/A", 0.0, null, false, null);
    }

    // -------------------------------------------------------------------
    // Getters y Setters
    // -------------------------------------------------------------------

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "method='" + paymentMethod + '\'' +
                ", amount=" + amount +
                ", date=" + (paymentDate != null ? paymentDate.toLocalDate() : "N/A") +
                ", isPaid=" + isPaid +
                ", user=" + (user != null ? user.getId() : "N/A") + // Se incluye el usuario en toString
                '}';
    }
}