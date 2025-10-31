package co.edu.uniquindio.proyectofinalp2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class
App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/co/edu/uniquindio/proyectofinalp2/View/LoginView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        scene.getStylesheets().add(
                getClass().getResource("/co/edu/uniquindio/proyectofinalp2/css/estilos.css").toExternalForm()
        );
        stage.setScene(scene);
        stage.setTitle("Plataforma de Logística");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
