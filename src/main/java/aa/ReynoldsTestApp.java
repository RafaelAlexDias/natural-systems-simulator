package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class ReynoldsTestApp implements IProcessingApp {

    private Boid wander, seeker, pursuiter, boid;
    private Flock flock;
    private float[] sacWeights  = {1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] view1 = {0.02f, 0.51f, 0.96f, 0.47f};
    private float[] view2 = {0.02f, 0.02f, 0.47f, 0.47f};
    private float[] view3 = {0.51f, 0.02f, 0.47f, 0.47f};
    private SubPlot plt1, plt2, plt3;
    private Body target;
    private int ix;

    @Override
    public void setup(PApplet parent) {
        // Inicialização dos 3 "Subplot" e de um "Flock"
        plt1 = new SubPlot(window, view1, parent.width, parent.height);
        plt2 = new SubPlot(window, view2, parent.width, parent.height);
        plt3 = new SubPlot(window, view3, parent.width, parent.height);
        flock = new Flock(200, 0.1f, 0.3f, parent.color(0, 100, 200), sacWeights, parent, plt1);
        // Obtenção de um "Boid" do "Flock"
        boid = flock.getBoid(4);
        // Inicialização de um "Boid" wander e adiciona-se um comportamento "Wander" ao mesmo
        wander = new Boid(new PVector(parent.random((float)window[0], (float)window[1]),
                parent.random((float)window[2], (float)window[3])), 0.5f,
                0.5f, parent.color(255, 0, 0), parent, plt2);
        wander.addBehavior(new Wander(1f));
        // Inicialização de um "Boid" pursuiter e adiciona-se um comportamento "Pursuit" ao mesmo
        pursuiter = new Boid(new PVector(parent.random((float)window[0], (float)window[1]),
                parent.random((float)window[2], (float)window[3])), 0.5f,
                0.5f, parent.color(0, 255, 0), parent, plt2);
        pursuiter.addBehavior(new Pursuit(1f));
        // Inicialização da lista "allTrackingBodies" a adição do wander a essa mesma lista
        List<Body> allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(wander);
        // Inicialização de um "Eye" para o pursuiter
        pursuiter.setEye(new Eye(pursuiter, allTrackingBodies));
        // Inicialização de um "Body" target, de um "Boid" seeker e adiciona-se a este os comportamentos "Seek" e "Flee"
        target = new Body(new PVector(), new PVector(), 1f, 0.3f, parent.color(0));
        seeker = new Boid(new PVector(parent.random((float)window[0], (float)window[1]),
                parent.random((float)window[2], (float)window[3])), 0.5f,
                0.5f, parent.color(0, 0, 255), parent, plt3);
        seeker.addBehavior(new Seek(1f));
        seeker.addBehavior(new Flee(1f));
        // Inicialização da lista "allTrackingBodies" a adição do target a essa mesma lista
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target);
        // Inicialização de um "Eye" para o seeker
        seeker.setEye(new Eye(seeker, allTrackingBodies));
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Limpeza da window
        parent.background(255);
        // Desenho de um rectângulo semi-transparente para um efeito de "rasto" no topo da window
        float[] bb = plt1.getBoundingBox();
        parent.fill(255, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);
        // Desenho de um rectângulo semi-transparente para um efeito de "rasto" no lado inferior esquerdo da window
        bb = plt2.getBoundingBox();
        parent.fill(190, 170, 45, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);
        // Desenho de um rectângulo semi-transparente para um efeito de "rasto" no lado inferior direito da window
        bb = plt3.getBoundingBox();
        parent.fill(120, 170, 150, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);
        // Aplica-se os comportamentos a cada objeto
        flock.applyBehavior(dt);
        wander.applyBehaviors(dt);
        pursuiter.applyBehaviors(dt);
        seeker.applyBehavior(ix, dt);
        // Display a cada objeto
        flock.display(parent, plt1);
        // boid.getEye().display(parent, plt1);
        wander.display(parent, plt2);
        pursuiter.display(parent, plt2);
        seeker.display(parent, plt3);
        // seeker.getEye().display(parent, plt3);
        target.display(parent, plt3);
    }

    // Método para fazer algo quando uma determinada tecla é pressionada
    @Override
    public void keyPressed(PApplet parent) {
        // Se a tecla 't' for pressionada, o seeker no plt3 trocará o seu comportamento entre o "Seek" e o "Flee"
        if (parent.key == 't') {
            ix = (ix + 1) % 2;
        }
    }

    // Método para fazer algo quando o rato é pressionado
    @Override
    public void mousePressed(PApplet parent) {
        // Verifica se o rato está dentro do plt3 e se tiver pode alterar a posição do target
        if (plt3.isInside(parent.mouseX, parent.mouseY)) {
            double[] w = plt3.getWorldCoord(parent.mouseX, parent.mouseY);
            target.setPos(new PVector((float)w[0], (float)w[1]));
        }
    }

    // Método para fazer algo quando o rato é arrastado
    @Override
    public void mouseDragged(PApplet parent) {}
}
