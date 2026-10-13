package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class BoidAceleradorTravaoApp implements IProcessingApp {
    private Boid b;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private Body target;
    private List<Body> allTrackingBodies;

    @Override
    public void setup(PApplet parent) {
        // Setup SubPlot
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        // Setup Boid
        b = new Boid(new PVector(), 1, 0.5f, parent.color(0), parent, plt);
        b.addBehavior(new Seek(1f));
        // Setup Target
        target = new Body(new PVector(), new PVector(), 1f, 0.2f, parent.color(255, 0, 0));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target);
        // Setup Eye
        Eye eye = new Eye(b, allTrackingBodies);
        b.setEye(eye);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Limpeza da window
        parent.background(255);
        // Aplicar os comportamentos
        b.applyBehaviors(dt);
        // Display do Boid e do Target
        b.display(parent, plt);
        target.display(parent, plt);
    }

    @Override
    public void keyPressed(PApplet parent) {
        // Caso a tecla '+' seja pressionada, a velocidade do Boid é aumentada
        if (parent.key == '+') {
            b.increaseSpeed();
        }
        // Caso a tecla '-' seja pressionada, a velocidade do Boid é diminuída
        else if (parent.key == '-') {
            b.decreaseSpeed();
        }
    }

    @Override
    public void mousePressed(PApplet parent) {
        // Ao clicarmos com o mouse na window, a posição do target vai ser alterada
        double[] ww = plt.getWorldCoord(parent.mouseX, parent.mouseY);
        target.setPos(new PVector((float) ww[0], (float) ww[1]));
    }

    @Override
    public void mouseDragged(PApplet parent) {

    }
}
