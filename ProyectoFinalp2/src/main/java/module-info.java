module co.edu.uniquindio.proyectofinalp2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires co.edu.uniquindio.proyectofinalp2;
    opens co.edu.uniquindio.proyectofinalp2.ViewController to javafx.fxml;
    opens co.edu.uniquindio.proyectofinalp2 to javafx.fxml;
    exports co.edu.uniquindio.proyectofinalp2;

}