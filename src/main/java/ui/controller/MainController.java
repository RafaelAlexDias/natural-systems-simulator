package ui.controller;

import core.Log;
import core.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import ui.processing.ProcessingLauncher;

import java.io.IOException;
import java.lang.System.Logger;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Stream;

public class MainController {

    private static final Logger LOG = Log.get(MainController.class);

    private static final String ACTIVE_CLASS = "active";

    @FXML private StackPane contentArea;
    @FXML private Label homeNav;
    @FXML private Label simulationNav;
    @FXML private Label settingsNav;
    @FXML private Label aboutNav;

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
        navigateTo("/fxml/simulationView.fxml", simulationNav);
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
        URL resource = getClass().getResource(fxmlPath);

        if (resource == null) {
            LOG.log(Logger.Level.ERROR, "View not found on classpath: {0}", fxmlPath);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(resource);
            Node page = loader.load();

            connectController(loader.getController());
            applyPageStylesheet(page, fxmlPath);

            contentArea.getChildren().setAll(page);
            updateSelectedNavigation(selectedItem);

            LOG.log(Logger.Level.DEBUG, "Navigated to {0}", fxmlPath);
        } catch (IOException exception) {
            LOG.log(Logger.Level.ERROR, "Error loading view: " + fxmlPath, exception);
        }
    }

    /** Wires the page controllers that need to launch Processing simulations. */
    private void connectController(Object controller) {
        if (controller instanceof HomeController homeController) {
            homeController.setOnOpenSimulation(this::openSimulation);
        } else if (controller instanceof SimulationController simulationController) {
            simulationController.setOnOpenSimulation(this::openSimulation);
        }
    }

    private void applyPageStylesheet(Node page, String fxmlPath) {
        String cssPath = fxmlPath
                .replace("/fxml/", "/css/")
                .replace("View.fxml", ".css");

        var css = getClass().getResource(cssPath);

        if (css != null && page instanceof Parent parent) {
            parent.getStylesheets().add(css.toExternalForm());
        }
    }

    private void updateSelectedNavigation(Label selectedItem) {
        Stream.of(homeNav, simulationNav, settingsNav, aboutNav)
                .filter(Objects::nonNull)
                .forEach(item -> item.getStyleClass().remove(ACTIVE_CLASS));

        if (selectedItem != null && !selectedItem.getStyleClass().contains(ACTIVE_CLASS)) {
            selectedItem.getStyleClass().add(ACTIVE_CLASS);
        }
    }

    /**
     * Launches the selected Processing simulation in a separate process.
     */
    private void openSimulation(Simulation simulation) {
        ProcessingLauncher.launch(simulation.id());
    }
}
