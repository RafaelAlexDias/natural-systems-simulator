
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
            LOG.log(
                    Logger.Level.ERROR,
                    "View not found on classpath: {0}",
                    fxmlPath
            );
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
            LOG.log(
                    Logger.Level.ERROR,
                    "Error loading view: " + fxmlPath,
                    exception
            );
        }
    }

    /**
     * Connects page controllers to navigation and simulation actions.
     */
    private void connectController(Object controller) {
        if (controller instanceof HomeController homeController) {
            // Preserve the existing Home behaviour.
            homeController.setOnOpenSimulation(this::openSimulation);

        } else if (controller instanceof SimulationController simulationController) {
            // Selecting a card opens its details page.
            simulationController.setOnOpenSimulation(
                    this::openSimulationDetails
            );

        } else if (controller instanceof SimulationDetailsController detailsController) {
            // The Run Simulation button launches Processing.
            detailsController.setOnRunSimulation(this::openSimulation);

            // The breadcrumb returns to the simulation catalogue.
            detailsController.setOnBack(this::showSimulations);
        }
    }

    /**
     * Opens the details page for the selected simulation.
     */
    private void openSimulationDetails(Simulation simulation) {
        if (simulation == null) {
            LOG.log(Logger.Level.WARNING, "Cannot open details for a null simulation.");
            return;
        }

        URL resource = getClass().getResource(
                "/fxml/simulationDetailsView.fxml"
        );

        if (resource == null) {
            LOG.log(
                    Logger.Level.ERROR,
                    "Simulation details view not found."
            );
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(resource);
            Parent page = loader.load();

            SimulationDetailsController controller =
                    loader.getController();

            controller.setSimulation(simulation);

            // Connect callbacks before displaying the page.
            connectController(controller);
            applyPageStylesheet(page, "/fxml/simulationDetailsView.fxml");

            contentArea.getChildren().setAll(page);
            updateSelectedNavigation(simulationNav);

            LOG.log(
                    Logger.Level.DEBUG,
                    "Opened details for simulation: {0}",
                    simulation.id()
            );

        } catch (IOException exception) {
            LOG.log(
                    Logger.Level.ERROR,
                    "Error loading simulation details: " + simulation.id(),
                    exception
            );
        }
    }

    /**
     * Loads the stylesheet associated with an FXML view.
     */
    private void applyPageStylesheet(Node page, String fxmlPath) {
        String cssPath = fxmlPath
                .replace("/fxml/", "/css/")
                .replace("View.fxml", ".css");

        URL css = getClass().getResource(cssPath);

        if (css != null && page instanceof Parent parent) {
            parent.getStylesheets().add(css.toExternalForm());
        }
    }

    private void updateSelectedNavigation(Label selectedItem) {
        Stream.of(homeNav, simulationNav, settingsNav, aboutNav)
                .filter(Objects::nonNull)
                .forEach(item ->
                        item.getStyleClass().remove(ACTIVE_CLASS)
                );

        if (selectedItem != null
                && !selectedItem.getStyleClass().contains(ACTIVE_CLASS)) {
            selectedItem.getStyleClass().add(ACTIVE_CLASS);
        }
    }

    /**
     * Launches a simulation in a separate process.
     */
    private void openSimulation(Simulation simulation) {
        if (simulation == null) {
            LOG.log(Logger.Level.WARNING, "Cannot launch a null simulation.");
            return;
        }

        if (!simulation.isRunnable()) {
            LOG.log(
                    Logger.Level.WARNING,
                    "Simulation is not runnable: {0}",
                    simulation.id()
            );
            return;
        }

        ProcessingLauncher.launch(simulation.id());
    }
}
