package co.edu.uniquindio.proyectofinalp2.chain;

import co.edu.uniquindio.proyectofinalp2.Model.Payment;

public class PaymentProcessor {

    private PaymentHandler firstHandler;

    public PaymentProcessor() {
        // Configura la cadena completa
        UserValidationHandler userValidation = new UserValidationHandler();
        AmountValidationHandler amountValidation = new AmountValidationHandler();
        MethodValidationHandler methodValidation = new MethodValidationHandler();
        FinalApprovalHandler finalApproval = new FinalApprovalHandler();

        // Enlaza los handlers
        userValidation.setNextHandler(amountValidation);
        amountValidation.setNextHandler(methodValidation);
        methodValidation.setNextHandler(finalApproval);

        // Define el primero de la cadena
        this.firstHandler = userValidation;
    }

    public void processPayment(Payment payment) {
        if (firstHandler != null) {
            firstHandler.handlePayment(payment);
        }
    }
}
