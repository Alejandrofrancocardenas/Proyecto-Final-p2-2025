package co.edu.uniquindio.proyectofinalp2.chain;

import co.edu.uniquindio.proyectofinalp2.Model.Payment;

public class FinalApprovalHandler extends PaymentHandler {

    @Override
    public void handlePayment(Payment payment) {
        System.out.println("🎉 Pago aprobado y confirmado por el sistema.");
    }
}
