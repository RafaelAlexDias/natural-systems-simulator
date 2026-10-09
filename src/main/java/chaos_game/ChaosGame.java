package chaos_game;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

import java.util.ArrayList;


public class ChaosGame implements IProcessingApp {

    private float x, y;
    private ArrayList<PVector> points;
    private PApplet p;

    @Override
    public void setup(PApplet p) {
        this.p = p;
        initializeAndDrawInitialRandomPoint();
        initializeAndDrawInitialShape();
    }

    @Override
    public void draw(PApplet p, float dt) {
        // Loop para realizar 100 iterações do jogo do caos a cada frame
        for (int i = 0; i != 100; i++) {
            // Escolhe aleatoriamente um ponto do conjunto de pontos
            int r = (int) Math.floor(p.random(points.size()));
            // Atualiza a posição atual para a média do ponto escolhido e a posição atual
            x = PApplet.lerp(x, points.get(r).x, 0.5f);
            y = PApplet.lerp(y, points.get(r).y, 0.5f);
            p.point(x, y);
        }
    }

    // Inicializa e desenha um ponto aleatório inicial
    private void initializeAndDrawInitialRandomPoint() {
        PVector randomInitialPoint = new PVector(p.random(p.width), p.random(p.height));
        p.point(randomInitialPoint.x, randomInitialPoint.y);
    }

    // Inicializa e desenha a forma inicial com três pontos
    private void initializeAndDrawInitialShape() {
        points = new ArrayList<>();
        points.add(new PVector(p.width/2, 0));
        points.add(new PVector(0, p.height));
        points.add(new PVector(p.width, p.height));
        for(PVector p1 : points){
            p.point(p1.x, p1.y);
        }
    }

    @Override
    public void mousePressed(PApplet p) {}

    @Override
    public void mouseDragged(PApplet p) {}

    @Override
    public void keyPressed(PApplet p) {}
}