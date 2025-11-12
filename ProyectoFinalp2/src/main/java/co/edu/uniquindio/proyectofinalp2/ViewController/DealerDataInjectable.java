package co.edu.uniquindio.proyectofinalp2.ViewController;

import co.edu.uniquindio.proyectofinalp2.Model.Dealer; /**
 * Interfaz para inyectar el objeto Dealer en los sub-controladores.
 */
public interface DealerDataInjectable {
    void setDealer(Dealer dealer);
}
