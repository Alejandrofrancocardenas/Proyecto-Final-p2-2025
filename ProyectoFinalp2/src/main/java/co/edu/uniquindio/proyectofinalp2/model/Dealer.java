package co.edu.uniquindio.proyectofinalp2.model;

public class Dealer extends Person{
    // hola, oye cambié el nombre de repartidor a dealer, para trabajar e ingles

    private Dealer (Builder builder) {
        super(builder);
    }

    public static class Builder extends Person.Builder<Builder>{
        @Override
        public Builder self(){
            return this;
        }

        @Override
        public Dealer build(){
            return new Dealer(this);
        }
    }
}
