import core.Simulation;
import core.Simulations;
import processing.core.PApplet;
import setup.IProcessingApp;

import java.util.List;

/**
 * Standalone Processing menu, useful to run the sketches without the JavaFX
 * shell. The real application entry point is {@code ui.MainApp}; this class
 * simply reuses {@link Simulations} instead of duplicating the dispatch logic.
 */
public class Main extends PApplet {

    private static final List<String> MENU_IDS = List.of(
            "chaos-game",
            "pentagon-chaos-game",
            "x-sides-chaos-game",
            "dla-simulation"
    );

    private IProcessingApp currentSim;

    public static void main(String[] args) {
        PApplet.main(Main.class, args);
    }

    @Override
    public void settings() {
        size(800, 700);
    }

    @Override
    public void setup() {
        surface.setTitle("MSSN \u2014 Natural Systems Simulator");
    }

    @Override
    public void draw() {
        if (currentSim == null) {
            drawMenu();
            return;
        }

        currentSim.draw(this, frameRate > 0 ? 1.0f / frameRate : 0f);
    }

    private void drawMenu() {
        background(34, 79, 36);
        textAlign(CENTER, CENTER);
        fill(255);
        textSize(28);
        text("MSSN \u2014 Natural Systems Simulator", width / 2f, height / 2f - 80);
        textSize(18);
        fill(220);
        text("Select a simulation:", width / 2f, height / 2f - 30);
        textSize(16);
        fill(200);

        for (int i = 0; i < MENU_IDS.size(); i++) {
            text((i + 1) + ": " + nameOf(i), width / 2f, height / 2f + 20 + i * 30);
        }

        textSize(14);
        fill(180);
        text(
                "Press 1-" + MENU_IDS.size() + " to start. Press - to return to menu.",
                width / 2f,
                height - 60
        );
    }

    private String nameOf(int index) {
        return Simulations.byId(MENU_IDS.get(index))
                .map(Simulation::name)
                .orElse(MENU_IDS.get(index));
    }

    @Override
    public void keyPressed() {
        if (currentSim == null) {
            int index = key - '1';

            if (index >= 0 && index < MENU_IDS.size()) {
                startSimulation(MENU_IDS.get(index));
            }
            return;
        }

        if (key == '-') {
            returnToMenu();
            return;
        }

        currentSim.keyPressed(this);
    }

    private void startSimulation(String simulationId) {
        Simulation simulation = Simulations.byId(simulationId).orElse(null);

        if (simulation == null || !simulation.isRunnable()) {
            return;
        }

        currentSim = simulation.create();
        surface.setTitle(simulation.name());
        background(255);
        currentSim.setup(this);
    }

    private void returnToMenu() {
        currentSim = null;
        surface.setTitle("MSSN \u2014 Natural Systems Simulator");
    }

    @Override
    public void mousePressed() {
        if (currentSim != null) {
            currentSim.mousePressed(this);
        }
    }

    @Override
    public void mouseDragged() {
        if (currentSim != null) {
            currentSim.mouseDragged(this);
        }
    }
}
