package co.edu.uniquindio.proyectofinalp2.Model;

import java.time.LocalDateTime;

public class Payment {

    private String idPay;
    private double mount;
    private LocalDateTime date;
    private String payMethod;
    private boolean result;
    private User user;

    //  Constructor corto
    public Payment(String idPay, double mount, LocalDateTime date, boolean result) {
        this.idPay = idPay;
        this.mount = mount;
        this.date = date;
        this.result = result;
    }

    //  Constructor completo
    public Payment(String idPay, double mount, LocalDateTime date, String payMethod, boolean result) {
        this.idPay = idPay;
        this.mount = mount;
        this.date = date;
        this.payMethod = payMethod;
        this.result = result;
    }

    // Getters y Setters...
    public String getIdPay() { return idPay; }
    public void setIdPay(String idPay) { this.idPay = idPay; }

    public double getMount() { return mount; }
    public void setMount(double mount) { this.mount = mount; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getPayMethod() { return payMethod; }
    public void setPayMethod(String payMethod) { this.payMethod = payMethod; }

    public boolean isResult() { return result; }
    public void setResult(boolean result) { this.result = result; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

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
