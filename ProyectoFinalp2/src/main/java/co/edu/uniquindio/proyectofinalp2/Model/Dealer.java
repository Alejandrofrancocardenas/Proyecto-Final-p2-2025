package co.edu.uniquindio.proyectofinalp2.Model;

import java.util.ArrayList;
import java.util.List;

public class Dealer extends Person {

    private boolean available;
    private int deliveriesMade;
    private List<Shipment> assignedShipments;


    protected Dealer(Builder builder) {
        super(builder);
        this.available = builder.available;
        this.deliveriesMade = builder.deliveriesMade;
        // Inicialización de la lista de envíos asignados
        this.assignedShipments = builder.assignedShipments != null ? builder.assignedShipments : new ArrayList<>();
    }


    public List<Shipment> getAssignedShipments() {
        return assignedShipments;
    }

    public void addShipment(Shipment shipment) {
        if (assignedShipments == null) {
            assignedShipments = new ArrayList<>();
        }
        assignedShipments.add(shipment);
    }


    public boolean isAvailable() {
        return available;
    }

    public boolean getAvailable() {
        return available;
    }


    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getDeliveriesMade() {
        return deliveriesMade;
    }

    public void setDeliveriesMade(int deliveriesMade) {
        this.deliveriesMade = deliveriesMade;
    }



    public double calcularTiempoPromedioEntregas() {
        if (assignedShipments == null || assignedShipments.isEmpty()) {
            return 0.0;
        }

        double totalHoras = 0;
        int entregasValidas = 0;

        for (Shipment s : assignedShipments) {
            if (s.getEstimatedDeliveryDate() > 0 && s.getStatus() == ShippingStatus.DELIVERED) {
                totalHoras += s.getEstimatedDeliveryDate();
                entregasValidas++;
            }
        }

        return entregasValidas > 0 ? totalHoras / entregasValidas : 0.0;
    }
    public static class Builder extends Person.Builder<Builder> {
        private boolean available = false;
        private int deliveriesMade = 0;
        private List<Shipment> assignedShipments;

        public Builder available(boolean available) {
            this.available = available;
            return this;
        }

        public Builder deliveriesMade(int deliveriesMade) {
            this.deliveriesMade = deliveriesMade;
            return this;
        }

        public Builder assignedShipments(List<Shipment> assignedShipments) {
            this.assignedShipments = assignedShipments;
            return this;
        }


        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Dealer build() {
            return new Dealer(this);
        }

    }

    // --- Método toString() ---

    @Override
    public String toString() {
        return "Dealer{" +
                "nombre='" + getFullname() + '\'' +
                ", correo='" + getEmail() + '\'' +
                ", telefono='" + getPhone() + '\'' +
                ", disponible=" + isAvailable() +
                ", entregasRealizadas=" + deliveriesMade +
                '}';
    }
}