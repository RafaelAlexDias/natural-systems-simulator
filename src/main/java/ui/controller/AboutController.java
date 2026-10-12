
package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class AboutController {

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
        URL resource = getClass().getResource(resourcePath);

        if (resource != null) {
            imageView.setImage(new Image(resource.toExternalForm()));
        }
    }
}