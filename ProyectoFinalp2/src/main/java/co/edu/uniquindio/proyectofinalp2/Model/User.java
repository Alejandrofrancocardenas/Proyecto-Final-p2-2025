package co.edu.uniquindio.proyectofinalp2.Model;

import java.util.ArrayList;
import java.util.List;

public class User extends Person {

    private String password;
    private List<Address> addresses;
    private List<Payment> payments;
    private List<Shipment> shipments;


    private User(Builder builder) {
        super(builder);
        this.password = builder.password;
        this.addresses = new ArrayList<Address>();
        this.payments = new ArrayList<Payment>();
        this.shipments = new ArrayList<Shipment>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    // Builder concreto
    public static class Builder extends Person.Builder<Builder> {

        private String password;

        public Builder password(String password) {
            this.password = password;
            return this;
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