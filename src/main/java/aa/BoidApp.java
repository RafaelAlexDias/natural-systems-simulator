package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class BoidApp implements IProcessingApp {

    private Boid b;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private Body target;
    private List<Body> allTrackingBodies;
    private int index = 2;

    @Override
    public void setup(PApplet parent) {
        // Inicialização do "SubPlot" e de um "Boid"
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        b = new Boid(new PVector(), 1, 0.5f, parent.color(0), parent, plt);
        // Adiciona os comportamentos
        b.addBehavior(new Seek(1f));
        b.addBehavior(new Flee(1f));
        b.addBehavior(new Wander(1f));
        // Inicialização de um target
        target = new Body(new PVector(), new PVector(), 1f, 0.2f, parent.color(255, 0, 0));
        allTrackingBodies = new ArrayList<Body>();
        // Adiciona o target a "allTrackingBodies"
        allTrackingBodies.add(target);
        // Inicialização de um "Eye" a aplicação do mesmo no "Boid"
        Eye eye = new Eye(b, allTrackingBodies);
        b.setEye(eye);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Limpeza da window
        parent.background(255);
        // Aplica o comportamento com um certo indíce
        b.applyBehavior(index, dt);
        // Display do "Boid"
        b.display(parent, plt);
    }

    // Método para fazer algo quando uma determinada tecla é pressionada
    @Override
    public void keyPressed(PApplet parent) {
    }

    // Método para fazer algo quando o rato é pressionado
    @Override
    public void mousePressed(PApplet parent) {
        // Alteração a posição do target para igualar a posição do rato
        double[] ww = plt.getWorldCoord(parent.mouseX, parent.mouseY);
        target.setPos(new PVector((float) ww[0], (float) ww[1]));
    }

    // Método para fazer algo quando o rato é arrastado
    @Override
    public void mouseDragged(PApplet parent) {}
}