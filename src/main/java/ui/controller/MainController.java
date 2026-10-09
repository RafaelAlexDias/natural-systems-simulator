
package ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import ui.processing.ChaosGameLauncher;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    private Label homeNav;

    @FXML
    private Label simulationsNav;

    @FXML
    private Label settingsNav;

    @FXML
    private Label aboutNav;

    @FXML
    public void initialize() {
        navigateTo("/fxml/homeView.fxml", homeNav);
    }

    @FXML
    private void showHome() {
        navigateTo("/fxml/homeView.fxml", homeNav);
    }

    @FXML
    private void showSimulations() {
        navigateTo("/fxml/simulationView.fxml", simulationsNav);
    }

    @FXML
    private void showSettings() {
        navigateTo("/fxml/settingsView.fxml", settingsNav);
    }

    @FXML
    private void showAbout() {
        navigateTo("/fxml/aboutView.fxml", aboutNav);
    }

    private void navigateTo(String fxmlPath, Label selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(fxmlPath)
            );

            Node page = loader.load();

            Object controller = loader.getController();

            // Permite à página inicial lançar simulações.
            if (controller instanceof HomeController homeController) {
                homeController.setOnOpenSimulation(this::openSimulation);
            }

            contentArea.getChildren().setAll(page);

            updateSelectedNavigation(selectedItem);

        } catch (IOException exception) {
            System.err.println(
                    "Error loading view: " + fxmlPath
            );
            exception.printStackTrace();
        }
    }

    private void updateSelectedNavigation(Label selectedItem) {
        if (homeNav != null) {
            homeNav.getStyleClass().remove("selected");
        }

        if (simulationsNav != null) {
            simulationsNav.getStyleClass().remove("selected");
        }

        if (settingsNav != null) {
            settingsNav.getStyleClass().remove("selected");
        }

        if (aboutNav != null) {
            aboutNav.getStyleClass().remove("selected");
        }

        if (selectedItem != null
                && !selectedItem.getStyleClass().contains("selected")) {
            selectedItem.getStyleClass().add("selected");
        }
    }

    /**
     * Launches the selected Processing simulation in a separate process.
     */
    private void openSimulation(String simulationId) {
        ChaosGameLauncher.launch(simulationId);
    }
}