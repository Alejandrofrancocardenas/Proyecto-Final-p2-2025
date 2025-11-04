package co.edu.uniquindio.proyectofinalp2.Model;

import java.util.ArrayList;
import java.util.List;

public class User extends Person {

    private String password;
    private String role;
    private List<Address> addresses;
    private List<Payment> payments;
    private List<Shipment> shipments;

    private User(Builder builder) {
        super(builder);
        this.password = builder.password;
        this.role = builder.rol;
        this.addresses = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.shipments = new ArrayList<>();
    }

    // Getters y setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRol(String rol) {
        this.role = rol;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    // Builder
    public static class Builder extends Person.Builder<Builder> {
        private String password;
        private String role;

        public Builder password(String password) {
            this.password = password;
            return self();
        }

        public Builder role(String role) { // 👈 metodo builder para rol
            this.role = role;
            return self();
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public User build() {
            return new User(this);
        }
    }
}
