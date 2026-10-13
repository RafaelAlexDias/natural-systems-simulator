package ui;

import core.AppInfo;
import core.Log;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.util.Images;

import java.io.IOException;
import java.lang.System.Logger;
import java.net.URL;

public class MainApp extends Application {

    private static final Logger LOG = Log.get(MainApp.class);

    private static final double WIDTH = 1280;
    private static final double HEIGHT = 700;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/mainView.fxml")
        );

        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);

        URL theme = getClass().getResource("/css/home.css");
        if (theme != null) {
            scene.getStylesheets().add(theme.toExternalForm());
        }

        Images.load(getClass(), AppInfo.ICON_RESOURCE)
                .ifPresent(icon -> stage.getIcons().add(icon));

        stage.setTitle(AppInfo.WINDOW_TITLE);
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setResizable(true);
        stage.setMaximized(false);
        stage.show();

        LOG.log(Logger.Level.DEBUG, "Application started");
    }

    public static void main(String[] args) {
        launch();
    }
}
