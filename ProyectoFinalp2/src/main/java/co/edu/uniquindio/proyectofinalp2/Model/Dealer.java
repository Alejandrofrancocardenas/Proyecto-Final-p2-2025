package co.edu.uniquindio.proyectofinalp2.Model;

import java.util.List;

public class Dealer extends Person {

    private boolean disponible;
    private int entregasRealizadas;

    // Constructor con builder
    protected Dealer(Builder builder) {
        super(builder);
        this.disponible = builder.disponible;
        this.entregasRealizadas = builder.entregasRealizadas;
    }
    private List<Shipment> assignedShipments;

    public List<Shipment> getAssignedShipments() {
        return assignedShipments;
    }

    public void addShipment(Shipment shipment) {
        assignedShipments.add(shipment);
    }

    // Getters y Setters
    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getEntregasRealizadas() {
        return entregasRealizadas;
    }

    public void setEntregasRealizadas(int entregasRealizadas) {
        this.entregasRealizadas = entregasRealizadas;
    }
    public double calcularTiempoPromedioEntregas() {
        if (assignedShipments == null || assignedShipments.isEmpty()) {
            return 0.0;
        }

        double totalHoras = 0;
        int entregasValidas = 0;

        for (Shipment s : assignedShipments) {
            if (s.getDeliveryTimeHours() > 0) { // solo cuenta las entregas completadas
                totalHoras += s.getDeliveryTimeHours();
                entregasValidas++;
            }
        }

        return entregasValidas > 0 ? totalHoras / entregasValidas : 0.0;
    }

    // Builder estático
    public static class Builder extends Person.Builder<Builder> {
        private boolean disponible;
        private int entregasRealizadas;

        public Builder disponible(boolean disponible) {
            this.disponible = disponible;
            return this;
        }

        public Builder entregasRealizadas(int entregasRealizadas) {
            this.entregasRealizadas = entregasRealizadas;
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
                ", disponible=" + disponible +
                ", entregasRealizadas=" + entregasRealizadas +
                '}';
    }
}
