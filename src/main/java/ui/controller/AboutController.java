package ui.controller;

import core.Log;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import ui.util.Images;

import java.lang.System.Logger;

public class AboutController {

    private static final Logger LOG = Log.get(AboutController.class);

    @FXML
    private ImageView javaLogo;

    @FXML
    private ImageView javafxLogo;

    @FXML
    private ImageView processingLogo;

    @FXML
    private ImageView gradleLogo;

    @FXML
    public void initialize() {
        loadLogo(javaLogo, "/images/technologies/java.png");
        loadLogo(javafxLogo, "/images/technologies/javafx.png");
        loadLogo(processingLogo, "/images/technologies/processing.png");
        loadLogo(gradleLogo, "/images/technologies/gradle.png");
    }

    private void loadLogo(ImageView imageView, String resourcePath) {
        Images.load(getClass(), resourcePath).ifPresentOrElse(
                imageView::setImage,
                () -> LOG.log(
                        Logger.Level.WARNING,
                        "Logo not found on classpath: {0}",
                        resourcePath
                )
        );
    }
}
