package co.edu.uniquindio.proyectofinalp2.chain;

import co.edu.uniquindio.proyectofinalp2.Model.Payment;

public class MethodValidationHandler extends PaymentHandler {

    @Override
    public void handlePayment(Payment payment) {

        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().isEmpty()) {
            System.out.println("❌ Error: Método de pago no especificado.");
            return;
        }
        System.out.println("✅ Método de pago verificado: " + payment.getPaymentMethod());
        if (nextHandler != null) nextHandler.handlePayment(payment);
    }
}
