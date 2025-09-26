package co.edu.uniquindio.proyectofinalp2.model;

public class User extends Person{

    private User(Builder builder) {
        super(builder);
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
