package co.edu.uniquindio.proyectofinalp2.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Person {

    protected String idUser;
    protected String fullname;
    protected String email;
    protected String phone;
    protected List<String> addresses;


    protected Person(Builder<?>  builder) {
        this.idUser = builder.id;
        this.fullname = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.addresses = new ArrayList<>();
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public abstract static class Builder<T extends Builder<T>>{

        private String name;
        private String id;
        private String email;
        private String phone;

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T id(String id) {
            this.id = id;
            return self();
        }

        public T email(String email) {
            this.email = email;
            return self();
        }

        public T phone(String phone) {
            this.phone = phone;
            return self();
        }

        protected abstract T self();
        public abstract Person build();
    }

}
