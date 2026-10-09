package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/home.fxml")
        );

        Scene scene = new Scene(loader.load(), 1280, 700);

        URL css = getClass().getResource("/css/home.css");

        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        stage.setTitle("Natural-Systems-Simulator");
        stage.setScene(scene);

        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setResizable(true);
        stage.setMaximized(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}