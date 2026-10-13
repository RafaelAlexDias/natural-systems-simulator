package ui.controller;

import core.Log;
import core.Simulation;
import core.Simulations;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.lang.System.Logger;
import java.util.List;
import java.util.function.Consumer;

public class HomeController {

    private static final Logger LOG = Log.get(HomeController.class);

    /** Number of most recent simulations shown on the home page. */
    private static final int LATEST_LIMIT = 6;

    @FXML
    private TilePane simulationGrid;

    private Consumer<Simulation> onOpenSimulation;

    @FXML
    public void initialize() {
        displaySimulations(Simulations.latest(LATEST_LIMIT));
    }

    public void setOnOpenSimulation(Consumer<Simulation> action) {
        this.onOpenSimulation = action;
    }

    private void displaySimulations(List<Simulation> simulations) {
        if (simulationGrid == null) {
            LOG.log(
                    Logger.Level.ERROR,
                    "simulationGrid was not injected. Check homeView.fxml."
            );
            return;
        }

        simulationGrid.getChildren().setAll(
                simulations.stream().map(this::createSimulationCard).toList()
        );

        LOG.log(Logger.Level.DEBUG, "Displayed {0} simulations", simulations.size());
    }

    private VBox createSimulationCard(Simulation simulation) {
        VBox card = new VBox(12);
        card.getStyleClass().add("simulation-card");

        VBox imageArea = new VBox();
        imageArea.getStyleClass().add("card-image");
        imageArea.setMinHeight(120);
        imageArea.setPrefHeight(120);
        imageArea.setMaxWidth(Double.MAX_VALUE);
        imageArea.setAlignment(Pos.CENTER);

        Label iconLabel = new Label(simulation.icon());
        iconLabel.getStyleClass().add("card-placeholder");
        iconLabel.setStyle("-fx-font-size: 42px;");

        imageArea.getChildren().add(iconLabel);

        Label titleLabel = new Label(simulation.name());
        titleLabel.getStyleClass().add("card-title");
        titleLabel.setWrapText(true);

        Label descriptionLabel = new Label(simulation.description());
        descriptionLabel.getStyleClass().add("card-description");
        descriptionLabel.setWrapText(true);

        Button runButton = new Button(simulation.isRunnable() ? "Run" : "Coming soon");
        runButton.getStyleClass().add("card-button");
        runButton.setMaxWidth(Double.MAX_VALUE);
        runButton.setDisable(!simulation.isRunnable());
        runButton.setOnAction(event -> openSimulation(simulation));

        VBox content = new VBox(12);
        content.setStyle("-fx-padding: 16px;");
        content.getChildren().addAll(titleLabel, descriptionLabel, runButton);

        card.getChildren().addAll(imageArea, content);

        card.setPrefWidth(260);
        card.setMinWidth(220);
        card.setMaxWidth(300);

        return card;
    }

    private void openSimulation(Simulation simulation) {
        if (onOpenSimulation == null) {
            LOG.log(Logger.Level.ERROR, "Simulation callback is not configured.");
            return;
        }

        LOG.log(Logger.Level.DEBUG, "Launching simulation: {0}", simulation.id());

        try {
            onOpenSimulation.accept(simulation);
        } catch (RuntimeException exception) {
            LOG.log(
                    Logger.Level.ERROR,
                    "Failed to launch simulation: " + simulation.id(),
                    exception
            );
        }
    }
}
