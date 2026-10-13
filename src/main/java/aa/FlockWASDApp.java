package aa;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class FlockWASDApp implements IProcessingApp {

    private Flock flock;
    private int numBoidControl = 0;
    private Boid boidControl;
    private float velocity = 3f;
    private float[] sacWeights  = {1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;

    @Override
    public void setup(PApplet parent) {
        // Inicialização de um "SubPlot" e de um "Flock"
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        flock = new Flock(10, 0.1f, 0.3f, parent.color(0, 100, 200), sacWeights, parent, plt);
        // Escolha de um "Boid" desse "Flock" para ser o elemento a ter controlo com as teclas
        boidControl = flock.getBoid(numBoidControl);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Desenho de um rectângulo semi-transparente para um efeito de "rasto"
        float[] bb = plt.getBoundingBox();
        parent.fill(255, 64);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);
        // Aplicação dos comportamentos ao "Flock"
        flock.applyBehavior(dt);
        // Display do "Flock"
        flock.display(parent, plt);
    }

    // Método para fazer algo quando uma determinada tecla é pressionada
    @Override
    public void keyPressed(PApplet parent) {
        // Mapeamento de controlos para as teclas WASD
        // Ao clicar em cada tecla, é aplicada uma força com uma certa "velocity" na direção desejada
        if (parent.key == 'w' || parent.key == 'W') {
            boidControl.applyForce(new PVector(0, velocity));
        }
        if (parent.key == 'a' || parent.key == 'A') {
            boidControl.applyForce(new PVector(-velocity, 0));
        }
        if (parent.key == 's' || parent.key == 'S') {
            boidControl.applyForce(new PVector(0, -velocity));
        }
        if (parent.key == 'd' || parent.key == 'D') {
            boidControl.applyForce(new PVector(velocity, 0));
        }
    }

    // Método para fazer algo quando o rato é pressionado
    @Override
    public void mousePressed(PApplet parent) {}

    // Método para fazer algo quando o rato é arrastado
    @Override
    public void mouseDragged(PApplet parent) {}
}
