package co.edu.uniquindio.proyectofinalp2.Model;

import java.time.LocalDateTime;

public class Payment {
    private String idPay;
    private double mount;
    private LocalDateTime date;
    private String payMethod;
    private boolean result;

    public Payment(String idPay, double mount, LocalDateTime date, boolean result) {
        this.idPay = idPay;
        this.mount = mount;
        this.date = date;
        this.result = result;
    }


    public String getIdPay() {
        return idPay;
    }

    public void setIdPay(String idPay) {
        this.idPay = idPay;
    }

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return idPay  + " " +
                mount + " " +
                date + " " +
                result;
    }
}
