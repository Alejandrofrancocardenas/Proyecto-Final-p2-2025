package co.edu.uniquindio.proyectofinalp2.Model;

import java.time.LocalDateTime;

public class Payment {

    private String idPay;          // ID del pago
    private double mount;          // Monto del pago
    private LocalDateTime date;    // Fecha y hora del pago
    private String payMethod;      // Método de pago (Tarjeta, Efectivo, etc.)
    private boolean result;        // Resultado del pago (aprobado o rechazado)
    private User user;             // Usuario que realiza el pago

    // Constructor principal
    public Payment(String idPay, double mount, LocalDateTime date, boolean result) {
        this.idPay = idPay;
        this.mount = mount;
        this.date = date;
        this.result = result;
    }

    // 🔹 Getter y Setter para idPay
    public String getIdPay() {
        return idPay;
    }

    public void setIdPay(String idPay) {
        this.idPay = idPay;
    }

    // 🔹 Getter y Setter para mount
    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
    }

    // 🔹 Getter y Setter para date
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // 🔹 Getter y Setter para payMethod
    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    // 🔹 Getter y Setter para result
    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    // 🔹 Nuevo: Getter y Setter para user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // 🔹 Representación en texto del pago
    @Override
    public String toString() {
        return "Payment{" +
                "idPay='" + idPay + '\'' +
                ", mount=" + mount +
                ", date=" + date +
                ", payMethod='" + payMethod + '\'' +
                ", result=" + result +
                ", user=" + (user != null ? user.getFullname() : "N/A") +
                '}';
    }
}
