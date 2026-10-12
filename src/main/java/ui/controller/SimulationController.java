
package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class SimulationController {

    @FXML private TextField searchField;
    @FXML private Button filterButton;
    @FXML private Button allButton;
    @FXML private Button fractalsButton;
    @FXML private Button chaosButton;
    @FXML private Button recentButton;
    @FXML private Label resultsLabel;
    @FXML private TilePane simulationGrid;

    private final List<SimulationInfo> simulations = List.of(
            new SimulationInfo(
                    "DLA",
                    "Fractals",
                    "Diffusion-Limited Aggregation forms branching "
                            + "structures as randomly moving particles stick together.",
                    "/images/simulations/dla.png",
                    LocalDate.of(2026, 10, 10),
                    "dla"
            ),
            new SimulationInfo(
                    "Chaos Game",
                    "Chaos Game",
                    "Generate geometric fractals by repeatedly moving "
                            + "towards randomly selected vertices.",
                    "/images/simulations/chaos-game.png",
                    LocalDate.of(2026, 10, 9),
                    "chaos-game"
            ),
            new SimulationInfo(
                    "Pentagon Chaos Game",
                    "Chaos Game",
                    "Explore fractal patterns generated using the vertices "
                            + "of a pentagon.",
                    null,
                    LocalDate.of(2026, 10, 8),
                    "pentagon-chaos-game"
            ),
            new SimulationInfo(
                    "X-Sides Chaos Game",
                    "Chaos Game",
                    "Experiment with chaos-game rules using polygons "
                            + "with different numbers of sides.",
                    null,
                    LocalDate.of(2026, 10, 7),
                    "x-sides-chaos-game"
            ),
            new SimulationInfo(
                    "Sierpinski Triangle",
                    "Fractals",
                    "A recursive triangular fractal built from smaller triangles.",
                    "/images/simulations/sierpinski.png",
                    LocalDate.of(2026, 10, 6),
                    "sierpinski"
            )
    );

    private String selectedCategory = "All";
    private SortMode sortMode = SortMode.NAME;
    private Consumer<String> onOpenSimulation;

    private enum SortMode {
        NAME,
        NEWEST
    }

    private record SimulationInfo(
            String name,
            String category,
            String description,
            String imagePath,
            LocalDate dateAdded,
            String id
    ) {}

    @FXML
    public void initialize() {
        renderSimulations();
    }

    /**
     * Allows MainController to connect a card to the Processing launcher.
     */
    public void setOnOpenSimulation(Consumer<String> callback) {
        this.onOpenSimulation = callback;
    }

    @FXML
    private void filterSimulations() {
        renderSimulations();
    }

    @FXML
    private void showAll() {
        selectedCategory = "All";
        updateActiveChip(allButton);
        renderSimulations();
    }

    @FXML
    private void showFractals() {
        selectedCategory = "Fractals";
        updateActiveChip(fractalsButton);
        renderSimulations();
    }

    @FXML
    private void showChaosGame() {
        selectedCategory = "Chaos Game";
        updateActiveChip(chaosButton);
        renderSimulations();
    }

    @FXML
    private void showRecentlyAdded() {
        selectedCategory = "All";
        sortMode = SortMode.NEWEST;
        updateActiveChip(recentButton);
        renderSimulations();
    }

    @FXML
    private void showFilters() {
        ContextMenu menu = new ContextMenu();

        MenuItem categoryTitle = new MenuItem("Filter by category");
        categoryTitle.setDisable(true);

        MenuItem all = new MenuItem("All simulations");
        all.setOnAction(event -> showAll());

        MenuItem fractals = new MenuItem("Fractals");
        fractals.setOnAction(event -> showFractals());

        MenuItem chaos = new MenuItem("Chaos Game");
        chaos.setOnAction(event -> showChaosGame());

        RadioMenuItem sortByName = new RadioMenuItem("Sort by name");
        RadioMenuItem sortByNewest = new RadioMenuItem("Recently added first");

        sortByName.setSelected(sortMode == SortMode.NAME);
        sortByNewest.setSelected(sortMode == SortMode.NEWEST);

        sortByName.setOnAction(event -> {
            sortMode = SortMode.NAME;
            renderSimulations();
        });

        sortByNewest.setOnAction(event -> {
            sortMode = SortMode.NEWEST;
            renderSimulations();
        });

        menu.getItems().addAll(
                categoryTitle,
                all,
                fractals,
                chaos,
                new SeparatorMenuItem(),
                sortByName,
                sortByNewest
        );

        menu.show(filterButton,
                javafx.geometry.Side.BOTTOM,
                0,
                4);
    }

    private void renderSimulations() {
        String query = searchField == null
                ? ""
                : searchField.getText().trim().toLowerCase(Locale.ROOT);

        Comparator<SimulationInfo> comparator =
                sortMode == SortMode.NEWEST
                        ? Comparator.comparing(SimulationInfo::dateAdded).reversed()
                        : Comparator.comparing(
                        SimulationInfo::name,
                        String.CASE_INSENSITIVE_ORDER
                );

        List<SimulationInfo> filtered = simulations.stream()
                .filter(simulation ->
                        selectedCategory.equals("All")
                                || simulation.category().equals(selectedCategory))
                .filter(simulation ->
                        simulation.name().toLowerCase(Locale.ROOT).contains(query)
                                || simulation.description()
                                .toLowerCase(Locale.ROOT).contains(query)
                                || simulation.category()
                                .toLowerCase(Locale.ROOT).contains(query))
                .sorted(comparator)
                .toList();

        simulationGrid.getChildren().clear();

        for (SimulationInfo simulation : filtered) {
            simulationGrid.getChildren().add(createCard(simulation));
        }

        resultsLabel.setText(
                filtered.size() + (filtered.size() == 1
                        ? " simulation found"
                        : " simulations found")
        );
    }

    private Node createCard(SimulationInfo simulation) {
        VBox card = new VBox(0);
        card.getStyleClass().add("simulation-card");
        card.setPrefSize(300, 220);
        card.setMinSize(250, 220);
        card.setMaxWidth(340);

        VBox imageArea = new VBox();
        imageArea.getStyleClass().add("simulation-image-area");
        imageArea.setMinHeight(120);
        imageArea.setPrefHeight(120);
        imageArea.setMaxHeight(120);

        if (simulation.imagePath() != null) {
            URL resource = getClass().getResource(simulation.imagePath());

            if (resource != null) {
                ImageView imageView = new ImageView(
                        new Image(resource.toExternalForm())
                );

                imageView.setFitWidth(294);
                imageView.setFitHeight(120);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);

                imageArea.getChildren().add(imageView);
            }
        }

        if (imageArea.getChildren().isEmpty()) {
            Label placeholder = new Label(simulation.category());
            placeholder.getStyleClass().add("image-placeholder");
            imageArea.getChildren().add(placeholder);
        }

        VBox details = new VBox(5);
        details.getStyleClass().add("simulation-card-details");

        Label name = new Label(simulation.name());
        name.getStyleClass().add("simulation-card-title");

        Label category = new Label(simulation.category());
        category.getStyleClass().add("simulation-card-category");

        Label description = new Label(simulation.description());
        description.getStyleClass().add("simulation-card-description");
        description.setWrapText(true);
        description.setMaxWidth(275);

        details.getChildren().addAll(name, category, description);
        card.getChildren().addAll(imageArea, details);

        card.setOnMouseClicked(event -> {
            if (onOpenSimulation != null) {
                onOpenSimulation.accept(simulation.id());
            } else {
                System.out.println(
                        "No simulation launcher configured for: "
                                + simulation.id()
                );
            }
        });

        return card;
    }

    private void updateActiveChip(Button activeButton) {
        for (Button button : List.of(
                allButton, fractalsButton, chaosButton, recentButton)) {
            button.getStyleClass().remove("active-chip");
        }

        if (!activeButton.getStyleClass().contains("active-chip")) {
            activeButton.getStyleClass().add("active-chip");
        }
    }
}