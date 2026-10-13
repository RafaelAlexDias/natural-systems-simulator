package aa;

import physics.Body;
import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.List;

public class DebuggingApp implements IProcessingApp {

    private Boid boid;
    private Flock flock;
    private float[] sacWeights  = {1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] view = {0, 0, 1, 1};
    private SubPlot plt;

    @Override
    public void setup(PApplet parent) {
        // Inicialização do "SubPlot" e do "Flock"
        plt = new SubPlot(window, view, parent.width, parent.height);
        flock = new Flock(200, 0.1f, 0.3f, parent.color(163, 13, 229), sacWeights, parent, plt);
        // Obtenção de um "Boid" do "Flock"
        boid = flock.getBoid(4);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Desenho de um rectângulo semi-transparente para um efeito de "rasto"
        float[] bb = plt.getBoundingBox();
        parent.fill(255, 128);
        parent.rect(bb[0], bb[1], bb[2], bb[3]);

        // Atualização do "olho" de cada "Boid"
        for (Boid b : flock.getBoids()) {
            b.getEye().look();
        }

        // Lista dos "boids" dentro do campo de visão do "Boid" escolhido
        List<Body> boidsInSight = boid.getEye().getBoidsInSight();
        for (Boid b : flock.getBoids()) {
            // Caso um "Boid" esteja no campo de visão do escolhido, irá mudar a cor
            if (boidsInSight.contains(b)) {
                b.setShape(parent, plt, parent.color(229, 0, 33));
            // Caso um "Boid" esteja no campo de visão do escolhido, irá manter a sua cor padrão
            } else {
                b.setShape(parent, plt, parent.color(163, 13, 229));
            }
        }

        // Aplica os comportamentos ao "Flock"
        flock.applyBehavior(dt);
        // Display do "Flock" e do "Eye"
        flock.display(parent, plt);
        boid.getEye().display(parent, plt);
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
