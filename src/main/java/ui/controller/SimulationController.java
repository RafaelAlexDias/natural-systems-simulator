package ui.controller;

import core.Log;
import core.Simulation;
import core.Simulations;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import ui.util.Images;

import java.lang.System.Logger;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class SimulationController {

    private static final Logger LOG = Log.get(SimulationController.class);

    private static final String CATEGORY_ALL = "All";
    private static final String CATEGORY_FRACTALS = "Fractals";
    private static final String CATEGORY_CHAOS = "Chaos Game";
    private static final String CATEGORY_AA = "AA";
    private static final String CATEGORY_PHYSICS = "Physics";

    @FXML private TextField searchField;
    @FXML private Button filterButton;
    @FXML private Button allButton;
    @FXML private Button fractalsButton;
    @FXML private Button chaosButton;
    @FXML private Button aaButton;
    @FXML private Button physicsButton;
    @FXML private Button recentButton;
    @FXML private Label resultsLabel;
    @FXML private TilePane simulationGrid;

    private final List<Simulation> simulations = Simulations.all();

    private String selectedCategory = CATEGORY_ALL;
    private SortMode sortMode = SortMode.NAME;
    private Consumer<Simulation> onOpenSimulation;

    private enum SortMode {
        NAME,
        NEWEST
    }

    @FXML
    public void initialize() {
        renderSimulations();
    }

    /** Allows {@link MainController} to connect a card to the Processing launcher. */
    public void setOnOpenSimulation(Consumer<Simulation> callback) {
        this.onOpenSimulation = callback;
    }

    @FXML
    private void filterSimulations() {
        renderSimulations();
    }

    @FXML
    private void showAll() {
        selectCategory(CATEGORY_ALL, allButton);
    }

    @FXML
    private void showFractals() {
        selectCategory(CATEGORY_FRACTALS, fractalsButton);
    }

    @FXML
    private void showChaosGame() {
        selectCategory(CATEGORY_CHAOS, chaosButton);
    }

    @FXML
    private void showAutonomousAgents() {
        selectCategory(CATEGORY_AA, aaButton);
    }

    @FXML
    private void showPhysics() {
        selectCategory(CATEGORY_PHYSICS, physicsButton);
    }

    @FXML
    private void showRecentlyAdded() {
        selectedCategory = CATEGORY_ALL;
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

        MenuItem fractals = new MenuItem(CATEGORY_FRACTALS);
        fractals.setOnAction(event -> showFractals());

        MenuItem chaos = new MenuItem(CATEGORY_CHAOS);
        chaos.setOnAction(event -> showChaosGame());

        MenuItem aa = new MenuItem(CATEGORY_AA);
        aa.setOnAction(event -> showAutonomousAgents());

        MenuItem physics = new MenuItem(CATEGORY_PHYSICS);
        physics.setOnAction(event -> showPhysics());

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
                aa,
                physics,
                new SeparatorMenuItem(),
                sortByName,
                sortByNewest
        );

        menu.show(filterButton, Side.BOTTOM, 0, 4);
    }

    private void selectCategory(String category, Button chip) {
        selectedCategory = category;
        updateActiveChip(chip);
        renderSimulations();
    }

    private void renderSimulations() {
        String query = searchField == null
                ? ""
                : searchField.getText().trim().toLowerCase(Locale.ROOT);

        Comparator<Simulation> comparator = sortMode == SortMode.NEWEST
                ? Comparator.comparing(Simulation::dateAdded).reversed()
                : Comparator.comparing(Simulation::name, String.CASE_INSENSITIVE_ORDER);

        List<Simulation> filtered = simulations.stream()
                .filter(simulation -> selectedCategory.equals(CATEGORY_ALL)
                        || simulation.category().equals(selectedCategory))
                .filter(simulation -> matches(simulation, query))
                .sorted(comparator)
                .toList();

        simulationGrid.getChildren().setAll(
                filtered.stream().map(this::createCard).toList()
        );

        resultsLabel.setText(
                filtered.size() + (filtered.size() == 1
                        ? " simulation found"
                        : " simulations found")
        );

        LOG.log(
                Logger.Level.DEBUG,
                "Rendered {0} simulations (category={1}, sort={2})",
                filtered.size(),
                selectedCategory,
                sortMode
        );
    }

    private boolean matches(Simulation simulation, String query) {
        if (query.isEmpty()) {
            return true;
        }

        return simulation.name().toLowerCase(Locale.ROOT).contains(query)
                || simulation.description().toLowerCase(Locale.ROOT).contains(query)
                || simulation.category().toLowerCase(Locale.ROOT).contains(query);
    }

    private Node createCard(Simulation simulation) {
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

        Images.load(getClass(), simulation.imagePath()).ifPresent(image -> {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(294);
            imageView.setFitHeight(120);
            imageView.setPreserveRatio(false);
            imageView.setSmooth(true);
            imageArea.getChildren().add(imageView);
        });

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

        card.setOnMouseClicked(event -> openSimulation(simulation));

        return card;
    }

    private void openSimulation(Simulation simulation) {
        if (!simulation.isRunnable()) {
            LOG.log(
                    Logger.Level.DEBUG,
                    "Simulation is not runnable yet: {0}",
                    simulation.id()
            );
            return;
        }

        if (onOpenSimulation == null) {
            LOG.log(
                    Logger.Level.ERROR,
                    "No simulation launcher configured for: {0}",
                    simulation.id()
            );
            return;
        }

        onOpenSimulation.accept(simulation);
    }

    private void updateActiveChip(Button activeButton) {
        for (Button button : List.of(
                allButton, fractalsButton, chaosButton,
                aaButton, physicsButton, recentButton)) {
            button.getStyleClass().remove("active-chip");
        }

        if (!activeButton.getStyleClass().contains("active-chip")) {
            activeButton.getStyleClass().add("active-chip");
        }
    }
}
