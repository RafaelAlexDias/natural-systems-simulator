package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Flock {
    private List<Boid> boids;
    private int nboids;

    // Construtor de um "Flock"
    public Flock(int nboids, float mass, float radius, int color, float[] sacWeights,
                 PApplet p, SubPlot plt) {
        this.nboids = nboids;
        double[] w = plt.getWindow();
        boids = new ArrayList<Boid>();
        for (int i = 0; i < nboids; i++) {
            float x = p.random((float)w[0], (float)w[1]);
            float y = p.random((float)w[2], (float)w[3]);
            Boid b = new Boid(new PVector(x, y), mass, radius, color, p, plt);
            b.addBehavior(new Separate(sacWeights[0]));
            b.addBehavior(new Align(sacWeights[1]));
            b.addBehavior(new Cohesion(sacWeights[2]));
            boids.add(b);
        }
        List<Body> bodies = boidList2BodyList(boids);
        for (Boid b : boids) {
            b.setEye(new Eye(b, bodies));
        }
    }

    // Método para obter a lista de "Boids"
    public List<Boid> getBoids() {
        return this.boids;
    }

    // Método que converte uma lista de "Boids" em uma lista de "Bodies"
    private List<Body> boidList2BodyList(List<Boid> boids) {
        List<Body> bodies = new ArrayList<Body>();
        for(Boid b : boids) {
            bodies.add(b);
        }
        return bodies;
    }

    // Método para aplicar comportamentos em um "Flock"
    public void applyBehavior(float dt) {
        for(Boid b : boids) {
            b.applyBehaviors(dt);
        }
    }


    // Método que obtém o número total de "Boids" em um "Flock"
    public int getNBoids() {
        return this.nboids;
    }

    // Método que obtém um "Boid" específico de uma lista
    public Boid getBoid(int i) {
        return boids.get(i);
    }

    // Método "display" de um "Flock"
    public void display(PApplet p, SubPlot plt) {
        for (Boid b :boids) {
            b.display(p, plt);
        }
    }
}
