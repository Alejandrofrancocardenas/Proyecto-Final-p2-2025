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

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/co/edu/uniquindio/proyectofinalp2/View/LoginView.fxml")
        );
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Plataforma de Logística - Sistema de Envíos");
        stage.setResizable(true);
        stage.setScene(scene);

        stage.setMaximized(true);

        stage.setMinWidth(1024);
        stage.setMinHeight(768);

        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("/co/edu/uniquindio/proyectofinalp2/View/" + fxml)
        );
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        boolean wasMaximized = primaryStage.isMaximized();

        primaryStage.setScene(scene);

        if (wasMaximized) {
            primaryStage.setMaximized(true);
        }
    }
    public static void setRoot(Parent root) {
        Scene scene = new Scene(root);

        boolean wasMaximized = primaryStage.isMaximized();

        primaryStage.setScene(scene);

        if (wasMaximized) {
            primaryStage.setMaximized(true);
        }
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}