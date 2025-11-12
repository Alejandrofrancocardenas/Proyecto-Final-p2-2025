package co.edu.uniquindio.proyectofinalp2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Cargar la vista de Login
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/co/edu/uniquindio/proyectofinalp2/View/LoginView.fxml")
        );
        Parent root = fxmlLoader.load();

        // Crear la escena
        Scene scene = new Scene(root);

        // Configurar el Stage
        stage.setTitle("Plataforma de Logística - Sistema de Envíos");
        stage.setResizable(true);
        stage.setScene(scene);

        // Maximizar la ventana
        stage.setMaximized(true);

        // Establecer tamaño mínimo
        stage.setMinWidth(1024);
        stage.setMinHeight(768);

        stage.show();
    }

    /**
     * Método para cambiar de escena manteniendo la ventana maximizada
     * @param fxml Nombre del archivo FXML (ej: "LoginView.fxml")
     */
    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("/co/edu/uniquindio/proyectofinalp2/View/" + fxml)
        );
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        // Mantener el estado maximizado
        boolean wasMaximized = primaryStage.isMaximized();

        primaryStage.setScene(scene);

        if (wasMaximized) {
            primaryStage.setMaximized(true);
        }
    }

    /**
     * Método sobrecargado que acepta un Parent ya cargado
     */
    public static void setRoot(Parent root) {
        Scene scene = new Scene(root);

        // Mantener el estado maximizado
        boolean wasMaximized = primaryStage.isMaximized();

        primaryStage.setScene(scene);

        if (wasMaximized) {
            primaryStage.setMaximized(true);
        }
    }

    /**
     * Obtener el Stage principal
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}