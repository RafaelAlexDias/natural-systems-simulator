package chaos_game;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

import java.util.ArrayList;

public class XSides_ChaosGame implements IProcessingApp {

    private float x, y;
    private ArrayList<PVector> points;
    private PApplet p;

    private int sides = 6;
    private float R = 0.667f;

    @Override
    public void setup(PApplet p) {
        p.background(255);
        this.p = p;
        initializeAndDrawInitialRandomPoint();
        initializeAndDrawInitialShape();
    }

    @Override
    public void draw(PApplet p, float dt) {
        // Para cada método de desenho, desenha cem pontos (propósito: desenhar mais rápido)
        for (int i = 0; i < 100; i++) {
            int r = (int) Math.floor(p.random(points.size()));
            // Mover R da distância do ponto atual para o ponto escolhido
            x = PApplet.lerp(x, points.get(r).x, R);
            y = PApplet.lerp(y, points.get(r).y, R);
            p.point(x, y);
        }
    }

    private void initializeAndDrawInitialRandomPoint() {
        PVector randomInitialPoint = new PVector(p.random(p.width), p.random(p.height));
        p.point(randomInitialPoint.x, randomInitialPoint.y);
    }

    private void initializeAndDrawInitialShape() {
        points = new ArrayList<>();
        float angleOff = PApplet.TWO_PI / sides;
        float radius = 200;

        // Criar pontos para um hexágono
        for (int i = 0; i < sides; i++) {
            float angle = i * angleOff;
            float hx = p.width / 2 + radius * PApplet.cos(angle);
            float hy = p.height / 2 + radius * PApplet.sin(angle);
            points.add(new PVector(hx, hy));
        }

        // Desenhar os pontos do hexágono
        for (PVector p1 : points) {
            p.point(p1.x, p1.y);
        }
    }

    @Override
    public void mousePressed(PApplet p) {}

    @Override
    public void mouseDragged(PApplet p) {}

    @Override
    public void keyPressed(PApplet p) {
        if (p.key == '5') {
            p.background(255);
            sides = 5;
            R = 0.618f;
            initializeAndDrawInitialShape();
            initializeAndDrawInitialRandomPoint();
        }
        if (p.key == '6') {
            p.background(255);
            sides = 6;
            R = 0.667f;
            initializeAndDrawInitialShape();
            initializeAndDrawInitialRandomPoint();
        }
        if (p.key == '7') {
            p.background(255);
            sides = 7;
            R = 0.692f;
            initializeAndDrawInitialShape();
            initializeAndDrawInitialRandomPoint();
        }
        if (p.key == '8') {
            p.background(255);
            sides = 8;
            R = 0.707f;
            initializeAndDrawInitialShape();
            initializeAndDrawInitialRandomPoint();
        }
        if (p.key == '9') {
            p.background(255);
            sides = 9;
            R = 0.742f;
            initializeAndDrawInitialShape();
            initializeAndDrawInitialRandomPoint();
        }
    }
}
