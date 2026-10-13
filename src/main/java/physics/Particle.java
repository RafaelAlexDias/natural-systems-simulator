package physics;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Particle extends Body{

    private float lifespan;
    private float timer;

    // Construtor de uma "Particle"
    protected Particle(PVector pos, PVector vel, float radius, int color, float lifespan) {
        super(pos, vel, 0f, radius, color);
        this.lifespan = lifespan;
        timer = 0;
    }

    // Método para mover uma "Particle"
    @Override
    public void move(float dt) {
        super.move(dt);
        timer += dt;
    }

    // Método para verificar se uma "Particle" está morta
    public boolean isDead() {
        return timer > lifespan;
    }

    // Método "display" de uma "Particle"
    @Override
    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        float alpha = PApplet.map(timer, 0, lifespan, 255, 0);
        p.fill(color, alpha);
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        float[] r = plt.getVectorCoord(radius, radius);
        p.noStroke();
        p.circle(pp[0], pp[1], 2*r[0]);
        p.popStyle();
    }
}
