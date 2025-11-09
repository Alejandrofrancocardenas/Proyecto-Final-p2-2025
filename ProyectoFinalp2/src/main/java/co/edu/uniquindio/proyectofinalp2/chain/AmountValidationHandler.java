package co.edu.uniquindio.proyectofinalp2.chain;

import co.edu.uniquindio.proyectofinalp2.Model.Payment;

public class AmountValidationHandler extends PaymentHandler {

    @Override
    public void handlePayment(Payment payment) {
        // Verifica si el monto es válido
        if (payment.getMount() <= 0) {
            System.out.println("❌ Error: Monto de pago inválido.");
            return;
        }
        System.out.println("✅ Monto de pago validado: $" + payment.getMount());
        if (nextHandler != null) nextHandler.handlePayment(payment);
    }
}
