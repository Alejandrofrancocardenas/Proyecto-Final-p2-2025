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
        this.role = builder.role;
        this.addresses = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.shipments = new ArrayList<>();
    }

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

    public void addAddress(Address address) {
        if (address != null) {
            addresses.add(address);
        }
    }

    public void addPayment(Payment payment) {
        if (payment != null) {
            payments.add(payment);
        }
    }

    public void addShipment(Shipment shipment) {
        if (shipment != null) {
            shipments.add(shipment);
        }
    }

    public static class Builder extends Person.Builder<Builder> {
        private String password;
        private String role;

        public Builder password(String password) {
            this.password = password;
            return self();
        }

        public Builder role(String role) {
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + getFullname() + '\'' +
                ", role='" + role + '\'' +
                ", addresses=" + addresses +
                ", payments=" + payments.size() +
                ", shipments=" + shipments.size() +
                '}';
    }
}
