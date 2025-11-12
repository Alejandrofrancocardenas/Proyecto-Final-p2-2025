package co.edu.uniquindio.proyectofinalp2.chain;

import co.edu.uniquindio.proyectofinalp2.Model.Payment;

public class UserValidationHandler extends PaymentHandler {

    @Override
    public void handlePayment(Payment payment) {
        // Verifica si el usuario está asociado al pago usando el getter correcto
        if (payment.getUser() == null) {
            System.out.println("❌ Error: El pago no tiene un usuario asociado.");
            return;
        }
        System.out.println("✅ Usuario verificado correctamente.");
        if (nextHandler != null) nextHandler.handlePayment(payment);
    }
}