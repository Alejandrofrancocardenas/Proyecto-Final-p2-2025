package co.edu.uniquindio.proyectofinalp2.model;

public class Admin extends Person{

    private Admin(Builder builder){
        super(builder);
    }

    public static class Builder extends Person.Builder<Builder>{
        @Override
        public Builder self(){
            return this;
        }

        @Override
        public Admin build(){
            return new Admin(this);
        }
    }
}
