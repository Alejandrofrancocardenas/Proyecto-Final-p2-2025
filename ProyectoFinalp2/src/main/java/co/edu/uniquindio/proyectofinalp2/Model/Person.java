package co.edu.uniquindio.proyectofinalp2.Model;

import java.io.Serializable;

/**
 * Clase abstracta que representa a una persona en el sistema,
 * de la cual heredan User, Admin y Dealer.
 */
public abstract class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String id;
    protected String fullname;
    protected String email;
    protected String phone;
    protected String password;
    // 🟢 Atributo para definir el rol (CLIENT, ADMIN, DEALER)
    protected String rol;


    protected Person(Builder<?>  builder) {
        this.id = builder.id;
        this.fullname = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.password = builder.password;
        // 🟢 Inicializar el rol
        this.rol = builder.rol;
    }


    // --- Getters y Setters ---

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getId() {
        return id;
    }

    public void setId(String idUser) {
        this.id = idUser;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 🟢 Getter y Setter para el Rol
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }


    // El Builder también debe ser Serializable
    public abstract static class Builder<T extends Builder<T>> implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id;
        private String name;
        private String email;
        private String phone;
        private String password;
        // 🟢 Campo 'rol' añadido al Builder
        private String rol;

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

        public T password(String password) {
            this.password = password;
            return self();
        }

        // 🟢 Método para establecer el Rol
        public T rol(String rol) {
            this.rol = rol;
            return self();
        }

        protected abstract T self();
        public abstract Person build();
    }

    @Override
    public String toString() {
        return  id       + " " +
                fullname + " " +
                email    + " " +
                phone;
    }
}