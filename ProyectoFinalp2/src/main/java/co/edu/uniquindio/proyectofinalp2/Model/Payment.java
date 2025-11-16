package co.edu.uniquindio.proyectofinalp2.Model;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String paymentMethod;
    private double amount;
    private LocalDateTime paymentDate;
    private boolean isPaid;
    private User user;


    public Payment(String paymentMethod, double amount, LocalDateTime paymentDate, boolean isPaid, User user) {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.isPaid = isPaid;
        this.user = user;
    }


    public Payment(double amount, boolean isPaid) {

        this("Pendiente", amount, null, isPaid, null);
    }


    public Payment() {

        this("N/A", 0.0, null, false, null);
    }

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