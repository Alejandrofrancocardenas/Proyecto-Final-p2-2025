package co.edu.uniquindio.proyectofinalp2.Model;

import java.util.List;

public class Dealer extends Person {

    private boolean avaliable;
    private int deliveriesMade;

    // Constructor con builder
    protected Dealer(Builder builder) {
        super(builder);
        this.avaliable = builder.avaliable;
        this.deliveriesMade = builder.deliveriesMade;
    }

    private List<Shipment> assignedShipments;

    public List<Shipment> getAssignedShipments() {
        return assignedShipments;
    }

    public void addShipment(Shipment shipment) {
        assignedShipments.add(shipment);
    }

    // Getters y Setters
    public boolean getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
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
            if (s.getEstimatedDeliveryDate() > 0) { // solo cuenta las entregas completadas
                totalHoras += s.getEstimatedDeliveryDate();
                entregasValidas++;
            }
        }

        return entregasValidas > 0 ? totalHoras / entregasValidas : 0.0;
    }

    // Builder estático
    public static class Builder extends Person.Builder<Builder> {
        private boolean avaliable;
        private int deliveriesMade;

        public Builder avaliable(boolean avaliable) {
            this.avaliable = avaliable;
            return this;
        }

        public Builder deliveriesMade(int deliveriesMade) {
            this.deliveriesMade = deliveriesMade;
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
    @Override
    public String toString() {
        return "Dealer{" +
                "nombre='" + fullname + '\'' +
                ", correo='" + email + '\'' +
                ", telefono='" + phone + '\'' +
                ", disponible=" + avaliable +
                ", entregasRealizadas=" + deliveriesMade +
                '}';
    }
}
