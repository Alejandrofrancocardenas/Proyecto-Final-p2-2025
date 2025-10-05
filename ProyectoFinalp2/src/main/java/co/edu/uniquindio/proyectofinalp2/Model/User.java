package co.edu.uniquindio.proyectofinalp2.Model;

public class User extends Person {

    private String password;

    private User(Builder builder) {
        super(builder);
        this.password = builder.password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
