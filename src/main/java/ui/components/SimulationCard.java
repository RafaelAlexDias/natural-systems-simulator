package ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;

import javafx.scene.layout.VBox;

public class SimulationCard extends VBox {

    private final Label titleLabel;
    private final Label descriptionLabel;
    private final ImageView imageView;

    public SimulationCard(String name, String description, String imagePath) {
        getStyleClass().add("simulation-card");
        setPrefSize(300, 200);
        setMinSize(250, 200);
        setMaxWidth(300);
        setSpacing(8);
        setPadding(new Insets(0));

        if (imagePath != null) {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                Image image = new Image(imageUrl.toExternalForm());
                imageView = new ImageView(image);
                imageView.setFitWidth(294);
                imageView.setFitHeight(124);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);
                imageView.getStyleClass().add("card-image");
                getChildren().add(imageView);
            } else {
                imageView = null;
            }
        } else {
            imageView = null;
        }

        VBox textContent = new VBox(3);
        textContent.setStyle("-fx-padding: 7px 12px 8px 12px;");
        textContent.setMaxWidth(270);

        titleLabel = new Label(name);
        titleLabel.getStyleClass().add("card-title");

        descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("card-description");
        descriptionLabel.setWrapText(true);
        descriptionLabel.maxWidthProperty().bind(textContent.widthProperty().subtract(24));

        textContent.getChildren().addAll(titleLabel, descriptionLabel);
        getChildren().add(textContent);
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public Label getDescriptionLabel() {
        return descriptionLabel;
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }
}