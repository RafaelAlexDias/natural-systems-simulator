package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class FlockPredatorApp implements IProcessingApp {

    private Flock flock;
    private Boid predator;
    private float[] sacWeights  = {1f, 1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private List<Body> allTrackingBodies;
    private float minBoidTargetDistance = 2f;
    private float distanceToTarget;
    private SubPlot plt;

    @Override
    public void setup(PApplet parent) {
        // Inicialização do "SubPlot", de um "Flock" e de um "Boid" predador
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        flock = new Flock(10, 0.1f, 0.3f, parent.color(0, 100, 200), sacWeights, parent, plt);
        predator = new Boid(new PVector(), 1, 0.5f, parent.color(0), parent, plt);
        // Adiciona o comportamento "Seek" ao predador
        predator.addBehavior(new Seek(1f));
        // Inicializa a lista de presas
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(flock.getBoid(1));
        // Inicializa um "Eye" para o predador
        Eye eye = new Eye(predator, allTrackingBodies);
        predator.setEye(eye);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Desenho de um rectângulo semi-transparente para um efeito de "rasto"
        float[] bb = plt.getBoundingBox();
        parent.fill(255, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);

        for (int i = 0; i < flock.getNBoids(); i++) {
            // Determina a distância de um elemento do "Flock" ao predador
            distanceToTarget = predator.distanceToTarget(flock.getBoid(i));
            // Se a distância for menor à variável "minBoidTargetDistance", o predador vai passar
            // a seguir esse elemento
            if (distanceToTarget < minBoidTargetDistance) {
                allTrackingBodies.clear();
                allTrackingBodies.add(flock.getBoid(i));
            }
        }
        // Aplica os comportamentos do "Flock" e do predador
        flock.applyBehavior(dt);
        predator.applyBehaviors(dt);
        // Display do "Flock" e do predador
        flock.display(parent, plt);
        predator.display(parent, plt);
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
