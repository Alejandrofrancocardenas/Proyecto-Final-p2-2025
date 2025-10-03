package co.edu.uniquindio.proyectofinalp2.model;

import java.util.ArrayList;
import java.util.List;

public class User extends Person{
    private List<Address> addresses;

    private User(Builder builder) {
        super(builder);
        addresses = new ArrayList<>();
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public static class Builder extends Person.Builder<Builder>{


        @Override
        public Builder self() {
            return this;
        }

        @Override
        public User build(){
            return new User(this);
        }
    }
}
