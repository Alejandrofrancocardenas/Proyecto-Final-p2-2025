package co.edu.uniquindio.proyectofinalp2.Model;

import com.fasterxml.jackson.databind.ser.std.NullSerializer;

public class Admin extends Person {

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
