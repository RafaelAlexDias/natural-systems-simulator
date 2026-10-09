
package ui.processing;

import chaos_game.ChaosGame;
import chaos_game.Pentagon_ChaosGame;
import chaos_game.XSides_ChaosGame;
import processing.core.PApplet;
import setup.IProcessingApp;

public class ProcessingSketch extends PApplet {

    private static String simulationId;

    private IProcessingApp simulation;

    public static void setSimulationId(String id) {
        simulationId = id;
    }

    @Override
    public void settings() {
        size(800, 700);
    }

    @Override
    public void setup() {
        switch (simulationId) {
            case "chaos-game" -> {
                simulation = new ChaosGame();
                surface.setTitle("MSSN — Chaos Game (Triangle)");
            }

            case "pentagon-chaos-game" -> {
                simulation = new Pentagon_ChaosGame();
                surface.setTitle("MSSN — Chaos Game (Pentagon)");
            }

            case "x-sides-chaos-game" -> {
                simulation = new XSides_ChaosGame();
                surface.setTitle("MSSN — Chaos Game (Variable Sides)");
            }

            default -> {
                System.err.println(
                        "Unknown simulation ID: " + simulationId
                );
                exit();
                return;
            }
        }

        background(255);
        simulation.setup(this);
    }

    @Override
    public void draw() {
        if (simulation == null) {
            return;
        }

        float dt = frameRate > 0 ? 1.0f / frameRate : 0.0f;
        simulation.draw(this, dt);
    }

    @Override
    public void mousePressed() {
        if (simulation != null) {
            simulation.mousePressed(this);
        }
    }

    @Override
    public void mouseDragged() {
        if (simulation != null) {
            simulation.mouseDragged(this);
        }
    }

    @Override
    public void keyPressed() {
        if (simulation != null) {
            simulation.keyPressed(this);
        }
    }
}