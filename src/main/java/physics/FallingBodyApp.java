package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class FallingBodyApp implements IProcessingApp {
    public static final float dimX = 20;
    public static final float dimY = 15;
    private static final float g = -9.8f;
    private static final float mass = 80;
    private static final float radius = 0.5f;
    private final double window[] = {0, dimX, 0, dimY};
    private float viewport[] = {0f, 0f, 1f, 1f};
    private SubPlot plt;
    private Body ball;
    private Water water;
    private Air air;
    private float speedUp = 0.5f;
    private float timer;

    @Override
    public void setup(PApplet parent) {
        // Inicialização de um "SubPlot", um "Body", dois tipos de fluído: "Water" e "Air" e um timer
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        ball = new Body(new PVector(0, 12), new PVector(8, 6),
                    mass, radius, parent.color(255, 255, 0));
        water = new Water(4, parent.color(0, 255, 255));
        air = new Air();
        timer = 0.0f;
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Aplica as forças à bola
        PVector f = new PVector(0, mass*g);
        ball.applyForce(f);
        //  Verificaca se a bola está dentro de água
        if (water.isInside(ball)) {
            // Se tiver, aplica a força de atrito da água
            f = water.drag(ball);
        }
        else {
            // Se não tiver, aplica a força de atrito do ar
            f = air.drag(ball);
        }
        ball.applyForce(f);
        // Move da bola
        ball.move(speedUp*dt);
        // Display da água e da bola
        water.display(parent, plt);
        ball.display(parent, plt);
        // Atualização do timer
        timer += (speedUp * dt);
        // Se a bola chegar ao fundo da água, para e exibe o timer
        if (ball.pos.y < 0) {
            parent.noLoop();
            System.out.println(timer);
        }
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
