package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

import static aa.DNA.random;

public class BoidWanderSeekApp implements IProcessingApp {

    private Boid b;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private Body target;
    private List<Body> allTrackingBodies;
    private float minBoidTargetDistance = 2.0f;

    @Override
    public void setup(PApplet parent) {
        // Setup SubPlot
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        // Setup Boid
        b = new Boid(new PVector(), 1, 0.5f, parent.color(0), parent, plt);
        b.addBehavior(new Seek(1f));
        b.addBehavior(new Wander(1f));
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
        // Função em que aplica os comportamentos "Wander" e "Seek" e envia a minBoidTargetDistance
        b.applyBehaviors(dt);
        // Determina a distância entre o "Boid" e o "Target"
        float distanceToTarget = b.distanceToTarget(target);
        // Se a distância entre o "Boid" e o "Target" for menor que a minBoidTargetDistance,
        // a posição do "Target" será alterada
        if (distanceToTarget < minBoidTargetDistance) {
            target.setPos(new PVector(random((float)window[0], (float)window[1]),
                    random((float)window[2], (float)window[3])));
        }
        // Display do Boid e do Target
        b.display(parent, plt);
        target.display(parent, plt);
    }

    // Método para fazer algo quando uma determinada tecla é pressionada
    @Override
    public void keyPressed(PApplet parent) {}

    // Método para fazer algo quando o rato é pressionado
    @Override
    public void mousePressed(PApplet parent) {}

    // Método para fazer algo quando o rato é arrastado
    @Override
    public void mouseDragged(PApplet parent) {}
}
