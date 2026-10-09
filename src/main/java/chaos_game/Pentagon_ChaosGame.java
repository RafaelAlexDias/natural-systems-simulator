package chaos_game;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

import java.util.ArrayList;

public class Pentagon_ChaosGame implements IProcessingApp {

    private float x, y;
    private int lastVertexIndex;  // Armazena o índice do vértice escolhido na iteração anterior
    private ArrayList<PVector> points;
    private PApplet p;

    @Override
    public void setup(PApplet p) {
        this.p = p;
        initializeAndDrawInitialRandomPoint();
        initializeAndDrawInitialShape();
        // Inicialização com um valor impossível para indicar que nenhum vértice foi escolhido ainda
        lastVertexIndex = -1;
    }

    @Override
    public void draw(PApplet p, float dt) {
        // Loop para desenhar cem pontos em cada frame (para desenhar mais rápido)
        for (int i = 0; i < 100; i++) {
            int r;
            do {
                // Escolhe aleatoriamente um vértice do pentágono, garantindo que seja diferente do vértice anterior
                r = (int) Math.floor(p.random(points.size()));
            } while (r == lastVertexIndex);
            // Mover 0.5 da distância do ponto atual para o ponto escolhido
            x = PApplet.lerp(x, points.get(r).x, 0.5f);
            y = PApplet.lerp(y, points.get(r).y, 0.5f);
            // Atualiza o índice do vértice escolhido na iteração anterior
            lastVertexIndex = r;
            // Desenha um ponto na nova posição
            p.point(x, y);
        }
    }

    // Inicializa e desenha um ponto aleatório inicial
    private void initializeAndDrawInitialRandomPoint() {
        PVector randomInitialPoint = new PVector(p.random(p.width), p.random(p.height));
        p.point(randomInitialPoint.x, randomInitialPoint.y);
    }

    // Inicializa e desenha a forma inicial do pentágono
    private void initializeAndDrawInitialShape() {
        points = new ArrayList<>();
        float angleOff = PApplet.TWO_PI / 5;
        float radius = 200;
        // Cria pontos para um pentágono
        for (int i = 0; i < 5; i++) {
            float angle = i * angleOff;
            float hx = p.width / 2 + radius * PApplet.cos(angle);
            float hy = p.height / 2 + radius * PApplet.sin(angle);
            points.add(new PVector(hx, hy));
        }
        // Desenha os pontos do pentágono
        for (PVector p1 : points) {
            p.point(p1.x, p1.y);
        }
    }

    // Métodos de eventos não utilizados nesta implementação
    @Override
    public void mousePressed(PApplet p) {}

    @Override
    public void mouseDragged(PApplet p) {}

    @Override
    public void keyPressed(PApplet p) {}
}
