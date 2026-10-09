package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import ui.components.SimulationCard;

import java.util.ArrayList;
import java.util.List;

public class HomeController {

    @FXML
    private TilePane simulationGrid;

    @FXML
    private TextField searchField;

    private final List<SimulationCard> simulationCards = new ArrayList<>();

    private String lastSelectedCategory = "home";

    @FXML
    public void initialize() {
        addSimulation("DLA",
                "O padrão DLA (Diffusion-Limited Aggregation) forma "
                        + "estruturas ramificadas e fractais através de partículas "
                        + "em movimento aleatório que se colam.",
                "/images/simulations/dla.png");

        addSimulation("Chaos Game",
                "Explore padrões fractais gerados por regras geométricas.",
                null);

        addSimulation("Cellular Automata",
                "Observe sistemas complexos através de regras locais.",
                null);

        addSimulation("Simulation 4",
                "Adicione aqui a descrição da tua simulação.",
                null);

        addSimulation("Simulation 5",
                "Adicione aqui a descrição da tua simulação.",
                null);

        addSimulation("Simulation 6",
                "Adicione aqui a descrição da tua simulação.",
                null);
    }

    private void addSimulation(
            String name,
            String description,
            String imagePath) {

        SimulationCard card = new SimulationCard(name, description, imagePath);
        card.setOnMouseClicked(event -> {
            System.out.println("Simulação selecionada: " + name);
        });

        simulationCards.add(card);
        simulationGrid.getChildren().add(card);
    }

    @FXML
    private void filterSimulations() {
        String query = searchField.getText()
                .trim()
                .toLowerCase();

        simulationGrid.getChildren().setAll(
                simulationCards.stream()
                        .filter(card -> {
                            String cardName = card.getTitle().toLowerCase();
                            return cardName.contains(query);
                        })
                        .toList()
        );
    }

    // Métodos de navegação da sidebar - chamados ao clicar nos labels
    @FXML
    private void showHome(MouseEvent event) {
        selectNavigationItem("home", (javafx.scene.control.Label) event.getSource());
        System.out.println("Home selected");
    }

    @FXML
    private void showSimulations(MouseEvent event) {
        selectNavigationItem("simulations", (javafx.scene.control.Label) event.getSource());
        if (simulationGrid.getChildren().isEmpty()) {
            addSimulation("Exemplo 1", "Primeira simulação de exemplo", null);
            addSimulation("Exemplo 2", "Segunda simulação de exemplo", null);
        }
        filterSimulations();
        System.out.println("Navigating to Simulations");
    }

    @FXML
    private void showSettings(MouseEvent event) {
        selectNavigationItem("settings", (javafx.scene.control.Label) event.getSource());
        showSettingsContent();
        System.out.println("Navigating to Settings");
    }

    @FXML
    private void showAbout(MouseEvent event) {
        selectNavigationItem("about", (javafx.scene.control.Label) event.getSource());
        showAboutContent();
        System.out.println("Navigating to About");
    }

    // Gerencia o estado visual da navbar - realça o item selecionado com retângulo azul
    private void selectNavigationItem(String category, javafx.scene.control.Label clickedLabel) {
        // Remove classe active de todos os labels da sidebar (precisei encontrar todos os nodes no VBox)
        java.util.List<javafx.scene.control.Label> labels = new java.util.ArrayList<>();
        
        // Busca todos os labels no sidebar VBox
        javafx.scene.Node sidebar = (javafx.scene.Node) ((javafx.scene.layout.VBox) simulationGrid.getScene().getWindow().getScene().getRoot().lookup(".sidebar"));
        if (sidebar != null) {
            for (javafx.scene.Node node : sidebar.getChildrenUnmodifiable()) {
                if (node instanceof javafx.scene.control.Label) {
                    labels.add((javafx.scene.control.Label) node);
                }
            }
        }
        
        // Remove active de todos
        for (javafx.scene.control.Label label : labels) {
            label.getStyleClass().remove("active");
        }
        
        // Adiciona active ao clicado
        if (clickedLabel != null) {
            clickedLabel.getStyleClass().add("active");
        }
        
        lastSelectedCategory = category;
        System.out.println("Navigated to: " + category);
    }

    // Conteúdo de About - mostrado na área central
    private void showAboutContent() {
        simulationGrid.getChildren().clear();
        
        VBox aboutBox = new VBox(15);
        aboutBox.setStyle("-fx-padding: 40; -fx-alignment: center;");
        aboutBox.setMaxWidth(500);
        aboutBox.setStyle("-fx-background: #1C252B; -fx-background-radius: 10; -fx-padding: 30;");
        
        Label title = new Label("Sobre Natural-Systems-Simulator");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #F4F4F4;");
        
        Label version = new Label("Versão 1.0");
        version.setStyle("-fx-font-size: 16px; -fx-text-fill: #AAB4BC;");
        
        Label developed = new Label("Desenvolvido por Rafael (MSSN Project)");
        developed.setStyle("-fx-font-size: 14px; -fx-text-fill: #AAB4BC;");
        
        Label technologies = new Label("Tecnologias: Java 25, JavaFX 25, Gradle 9.8");
        technologies.setStyle("-fx-font-size: 14px; -fx-text-fill: #AAB4BC;");
        
        Label desc = new Label("""
                Sistema de simulação de sistemas naturais.
                Inclui: DLA, Jogos do Caos, Autómatas Celulares e mais.
                """);
        desc.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E3E5;");
        
        aboutBox.getChildren().addAll(title, version, developed, technologies, desc);
        
        simulationGrid.getChildren().add(aboutBox);
    }

    // Conteúdo de Settings - mostrado na área central
    private void showSettingsContent() {
        simulationGrid.getChildren().clear();
        
        VBox settingsBox = new VBox(15);
        settingsBox.setStyle("-fx-padding: 40; -fx-alignment: center;");
        settingsBox.setMaxWidth(500);
        settingsBox.setStyle("-fx-background: #1C252B; -fx-background-radius: 10; -fx-padding: 30;");
        
        Label title = new Label("Configurações");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #F4F4F4;");
        
        Label theme = new Label("Tema: Escuro (Dark Mode)");
        theme.setStyle("-fx-font-size: 16px; -fx-text-fill: #AAB4BC;");
        
        Label font = new Label("Fonte: Georgia");
        font.setStyle("-fx-font-size: 16px; -fx-text-fill: #AAB4BC;");
        
        Label versionLab = new Label("Versão: Java 25 + JavaFX 25");
        versionLab.setStyle("-fx-font-size: 16px; -fx-text-fill: #AAB4BC;");
        
        Label sims = new Label("Simulações disponíveis: 6");
        sims.setStyle("-fx-font-size: 16px; -fx-text-fill: #AAB4BC;");
        
        Label desc = new Label("""
                Sistema de simulação de sistemas naturais utilizando JavaFX e Processing.
                """);
        desc.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E3E5;");
        
        settingsBox.getChildren().addAll(title, theme, font, versionLab, sims, desc);
        
        simulationGrid.getChildren().add(settingsBox);
    }
}