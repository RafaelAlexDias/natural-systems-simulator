package ui.controller;

import core.Log;
import core.Simulation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ui.util.Images;

import java.lang.System.Logger;
import java.util.function.Consumer;

/**
 * Details page for a single {@link Simulation}.
 *
 * <p>The page is a pure view over the catalogue: it reads the simulation's name,
 * description, controls and preview image directly from {@link Simulation}, so
 * the data is never duplicated here.
 */
public class SimulationDetailsController {

    private static final Logger LOG = Log.get(SimulationDetailsController.class);

    @FXML private Label breadcrumbName;
    @FXML private ImageView simulationImage;
    @FXML private Label simulationTitle;
    @FXML private Label simulationDescription;
    @FXML private VBox controlsContainer;
    @FXML private Button runButton;

    private Simulation simulation;
    private Consumer<Simulation> onRunSimulation;
    private Runnable onBack;

    /** Assigns the simulation to display; must be called after the FXML loads. */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
        displaySimulation(simulation);
    }

    public void setOnRunSimulation(Consumer<Simulation> callback) {
        this.onRunSimulation = callback;
    }

    public void setOnBack(Runnable callback) {
        this.onBack = callback;
    }

    @FXML
    private void runSimulation() {
        if (onRunSimulation != null && simulation != null) {
            onRunSimulation.accept(simulation);
        }
    }

    @FXML
    private void backToSimulations() {
        if (onBack != null) {
            onBack.run();
        }
    }

    private void displaySimulation(Simulation simulation) {
        if (simulation == null) {
            LOG.log(Logger.Level.WARNING, "No simulation provided to the details page.");
            return;
        }

        breadcrumbName.setText(simulation.name());
        simulationTitle.setText(simulation.name());
        simulationDescription.setText(simulation.description());
        runButton.setDisable(!simulation.isRunnable());

        controlsContainer.getChildren().clear();

        for (String control : simulation.controls()) {
            Label label = new Label("\u2022  " + control);
            label.getStyleClass().add("control-item");
            label.setWrapText(true);
            controlsContainer.getChildren().add(label);
        }

        simulationImage.setImage(null);
        Images.load(getClass(), simulation.imagePath())
                .ifPresent(simulationImage::setImage);
    }
}
