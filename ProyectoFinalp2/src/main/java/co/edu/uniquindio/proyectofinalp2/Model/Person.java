package co.edu.uniquindio.proyectofinalp2.Model;

public abstract class Person {

    protected String id;
    protected String fullname;
    protected String email;
    protected String phone;


    protected Person(Builder<?>  builder) {
        this.id = builder.id;
        this.fullname = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        }


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

    public abstract static class Builder<T extends Builder<T>>{

        private String id;
        private String name;
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

    @Override
    public String toString() {
        return  id       + " " +
                fullname + " " +
                email    + " " +
                phone;
    }
}
