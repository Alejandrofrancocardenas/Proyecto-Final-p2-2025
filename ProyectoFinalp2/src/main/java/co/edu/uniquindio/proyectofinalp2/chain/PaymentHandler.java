package co.edu.uniquindio.proyectofinalp2.chain;

import co.edu.uniquindio.proyectofinalp2.Model.Payment;

public abstract class PaymentHandler {
    protected PaymentHandler nextHandler;

    // Establece el siguiente eslabón en la cadena
    public void setNextHandler(PaymentHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    // Método abstracto que cada handler debe implementar
    public abstract void handlePayment(Payment payment);
}
