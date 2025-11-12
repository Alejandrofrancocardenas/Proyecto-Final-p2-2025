/**
 * Define el módulo de la aplicación y sus dependencias.
 * La directiva 'opens' es crucial para permitir que JavaFX (javafx.fxml)
 * acceda a los controladores mediante reflexión.
 */
module co.edu.uniquindio.proyectofinalp2 {

    // --- Módulos Requeridos (Requires) ---
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Asegúrate de que graphics esté aquí para el Stage

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires itextpdf;
    requires com.fasterxml.jackson.databind;
    requires java.base; // Incluir explícitamente java.base

    // --- Apertura de Paquetes para Reflexión (Opens) ---

    // 💥 Esencial: Permite que JavaFX acceda a las propiedades de las clases del modelo (para TableView y Data Binding)
    opens co.edu.uniquindio.proyectofinalp2.Model to javafx.base;

    // 🔥 NUEVO: Abrir el paquete decorators para que JavaFX pueda acceder a sus propiedades en TableView
    opens co.edu.uniquindio.proyectofinalp2.decorators to javafx.base;

    // 🔥 NUEVO: Abrir el paquete dto si tienes DTOs que se usan en tablas
    opens co.edu.uniquindio.proyectofinalp2.dto to javafx.base;

    // Abrir el paquete principal (donde está App.java) para que el FXMLLoader pueda cargar LoginView.fxml, etc.
    opens co.edu.uniquindio.proyectofinalp2 to javafx.fxml;

    // Abrir el paquete de controladores generales (Ej: LoginController, UserController)
    opens co.edu.uniquindio.proyectofinalp2.ViewController to javafx.fxml;

    // Abrir el paquete de controladores de usuario
    opens co.edu.uniquindio.proyectofinalp2.ViewController.UserViewControllers to javafx.fxml;

    // Abrir el paquete de controladores específicos de métodos del administrador
    opens co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController to javafx.fxml;

    // 🟢 CORRECCIÓN: Abrir el paquete de controladores del repartidor (Dealer)
    // Esta línea faltaba y causaba el error 'IllegalAccessException'
    opens co.edu.uniquindio.proyectofinalp2.ViewController.DealerViewController to javafx.fxml;


    // --- Exportación de Paquetes (Exports) ---

    // Exportar el paquete principal para que sea ejecutable
    exports co.edu.uniquindio.proyectofinalp2;

    // Exportar todos los paquetes de controladores que otros módulos o el sistema de carga necesiten ver
    exports co.edu.uniquindio.proyectofinalp2.ViewController;
    exports co.edu.uniquindio.proyectofinalp2.ViewController.UserViewControllers;
    exports co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController;

    // 🟢 CORRECCIÓN: Exportar el paquete de controladores del repartidor (Dealer)
    exports co.edu.uniquindio.proyectofinalp2.ViewController.DealerViewController;

    // Exportar el modelo y decorators si otros módulos los necesitan
    exports co.edu.uniquindio.proyectofinalp2.Model;
    exports co.edu.uniquindio.proyectofinalp2.decorators;
}