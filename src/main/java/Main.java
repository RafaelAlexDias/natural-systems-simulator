import chaos_game.ChaosGame;
import chaos_game.Pentagon_ChaosGame;
import chaos_game.XSides_ChaosGame;
import processing.core.PApplet;
import setup.IProcessingApp;

public class Main extends PApplet {
    private enum Mode {
        MENU, CHAOS_TRIANGLE, CHAOS_PENTAGON, CHAOS_XSIDES
    }

    private Mode mode = Mode.MENU;
    private IProcessingApp currentSim;

    private final String[] menuOptions = {
            "1: Chaos Game - Triangle (Sierpinski)",
            "2: Chaos Game - Pentagon (no repeat previous vertex)",
            "3: Chaos Game - X-Sides (press 5-9 to change)"
    };

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    @Override
    public void settings() {
        size(800, 700);
    }

    @Override
    public void setup() {
        surface.setTitle("MSSN — Natural Systems Simulator");
    }

    @Override
    public void draw() {
        if (mode == Mode.MENU) {
            drawMenu();
        } else {
            if (currentSim != null) {
                currentSim.draw(this, frameRate > 0 ? 1.0f / frameRate : 0f);
            }
        }
    }

    private void drawMenu() {
        background(34, 79, 36);
        textAlign(CENTER, CENTER);
        fill(255);
        textSize(28);
        text("MSSN — Natural Systems Simulator", width / 2f, height / 2f - 80);
        textSize(18);
        fill(220);
        text("Select a simulation:", width / 2f, height / 2f - 30);
        textSize(16);
        fill(200);
        for (int i = 0; i < menuOptions.length; i++) {
            text(menuOptions[i], width / 2f, height / 2f + 20 + i * 30);
        }
        textSize(14);
        fill(180);
        text("Press 1, 2, or 3 to start. Press - to return to menu.", width / 2f, height - 60);
    }

    @Override
    public void keyPressed() {
        if (mode == Mode.MENU) {
            switch (key) {
                case '1':
                    startSimulation(Mode.CHAOS_TRIANGLE);
                    break;
                case '2':
                    startSimulation(Mode.CHAOS_PENTAGON);
                    break;
                case '3':
                    startSimulation(Mode.CHAOS_XSIDES);
                    break;
                default:
                    break;
            }
        } else {
            if (key == '-') {
                returnToMenu();
                return;
            }
            if (currentSim != null) {
                currentSim.keyPressed(this);
            }
        }
    }

    @Override
    public void mousePressed() {
        if (mode != Mode.MENU && currentSim != null) {
            currentSim.mousePressed(this);
        }
    }

    @Override
    public void mouseDragged() {
        if (mode != Mode.MENU && currentSim != null) {
            currentSim.mouseDragged(this);
        }
    }

    private void startSimulation(Mode simMode) {
        mode = simMode;
        background(255);
        switch (simMode) {
            case CHAOS_TRIANGLE:
                surface.setTitle("Chaos Game - Triangle");
                currentSim = new ChaosGame();
                currentSim.setup(this);
                break;
            case CHAOS_PENTAGON:
                surface.setTitle("Chaos Game - Pentagon");
                currentSim = new Pentagon_ChaosGame();
                currentSim.setup(this);
                break;
            case CHAOS_XSIDES:
                surface.setTitle("Chaos Game - X-Sides (5-9)");
                currentSim = new XSides_ChaosGame();
                currentSim.setup(this);
                break;
            default:
                mode = Mode.MENU;
                break;
        }
    }

    private void returnToMenu() {
        mode = Mode.MENU;
        surface.setTitle("MSSN — Natural Systems Simulator");
        currentSim = null;
    }
}