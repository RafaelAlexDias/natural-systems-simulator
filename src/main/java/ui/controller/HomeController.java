
package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class HomeController {

    @FXML
    private TilePane simulationGrid;

    @FXML
    private TextField searchField;

    private Consumer<String> onOpenSimulation;

    private final List<SimulationInfo> simulations = List.of(
            new SimulationInfo(
                    "Chaos Game - Triangle",
                    "Explore fractal patterns using a triangle "
                            + "and a random vertex selection rule.",
                    "△",
                    "chaos-game"
            ),
            new SimulationInfo(
                    "Chaos Game - Pentagon",
                    "Explore fractal patterns using a pentagon "
                            + "and a no-repeat vertex rule.",
                    "⬠",
                    "pentagon-chaos-game"
            ),
            new SimulationInfo(
                    "Chaos Game - Variable Sides",
                    "Explore fractals using 5 to 9 sides. "
                            + "Press a number key to change the shape.",
                    "⬡",
                    "x-sides-chaos-game"
            )
    );

    @FXML
    public void initialize() {
        displaySimulations();

        if (searchField != null) {
            searchField.textProperty().addListener(
                    (observable, oldValue, newValue) ->
                            filterSimulations(newValue)
            );
        }
    }

    public void setOnOpenSimulation(Consumer<String> action) {
        this.onOpenSimulation = action;
    }

    private void displaySimulations() {
        if (simulationGrid == null) {
            System.err.println(
                    "ERROR: simulationGrid was not injected. "
                            + "Check homeView.fxml."
            );
            return;
        }

        simulationGrid.getChildren().clear();

        for (SimulationInfo simulation : simulations) {
            simulationGrid.getChildren().add(
                    createSimulationCard(simulation)
            );
        }

        System.out.println(
                "Displayed simulations: "
                        + simulationGrid.getChildren().size()
        );
    }

    private void filterSimulations(String searchText) {
        if (simulationGrid == null) {
            return;
        }

        simulationGrid.getChildren().clear();

        String query = searchText == null
                ? ""
                : searchText.trim().toLowerCase(Locale.ROOT);

        for (SimulationInfo simulation : simulations) {
            boolean matches =
                    simulation.title()
                            .toLowerCase(Locale.ROOT)
                            .contains(query)
                            || simulation.description()
                            .toLowerCase(Locale.ROOT)
                            .contains(query);

            if (matches) {
                simulationGrid.getChildren().add(
                        createSimulationCard(simulation)
                );
            }
        }
    }

    private VBox createSimulationCard(SimulationInfo simulation) {
        VBox card = new VBox(12);
        card.getStyleClass().add("simulation-card");

        // Icon / preview area
        VBox imageArea = new VBox();
        imageArea.getStyleClass().add("card-image");
        imageArea.setMinHeight(120);
        imageArea.setPrefHeight(120);
        imageArea.setMaxWidth(Double.MAX_VALUE);
        imageArea.setAlignment(javafx.geometry.Pos.CENTER);

        Label iconLabel = new Label(simulation.icon());
        iconLabel.getStyleClass().add("card-placeholder");
        iconLabel.setStyle("-fx-font-size: 42px;");

        imageArea.getChildren().add(iconLabel);

        // Card title
        Label titleLabel = new Label(simulation.title());
        titleLabel.getStyleClass().add("card-title");
        titleLabel.setWrapText(true);

        // Card description
        Label descriptionLabel =
                new Label(simulation.description());
        descriptionLabel.getStyleClass().add("card-description");
        descriptionLabel.setWrapText(true);

        // Run button
        Button runButton = new Button("Run");
        runButton.getStyleClass().add("card-button");
        runButton.setMaxWidth(Double.MAX_VALUE);
        runButton.setOnAction(
                event -> openSimulation(simulation)
        );

        // Content padding without changing the CSS card background
        VBox content = new VBox(12);
        content.setStyle("-fx-padding: 16px;");
        content.getChildren().addAll(
                titleLabel,
                descriptionLabel,
                runButton
        );

        card.getChildren().addAll(imageArea, content);

        // Keep cards a consistent size in the TilePane
        card.setPrefWidth(260);
        card.setMinWidth(220);
        card.setMaxWidth(300);

        return card;
    }

    private void openSimulation(SimulationInfo simulation) {
        System.out.println(
                "Launching simulation: " + simulation.id()
        );

        if (onOpenSimulation == null) {
            System.err.println(
                    "ERROR: Simulation callback is not configured."
            );
            return;
        }

        try {
            onOpenSimulation.accept(simulation.id());
        } catch (RuntimeException exception) {
            System.err.println(
                    "Failed to launch simulation: " + simulation.id()
            );
            exception.printStackTrace();
        }
    }

    private record SimulationInfo(
            String title,
            String description,
            String icon,
            String id
    ) {
    }
}