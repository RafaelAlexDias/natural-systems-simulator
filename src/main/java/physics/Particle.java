package physics;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

/**
 * A short-lived body that fades out as it ages and is removed once its lifespan
 * has elapsed.
 */
public class Particle extends Body{

    private float lifespan;
    private float timer;

    // Creates a particle with the given lifespan.
    protected Particle(PVector pos, PVector vel, float radius, int color, float lifespan) {
        super(pos, vel, 0f, radius, color);
        this.lifespan = lifespan;
        timer = 0;
    }

    // Moves the particle and advances its age timer.
    @Override
    public void move(float dt) {
        super.move(dt);
        timer += dt;
    }

    // Returns whether the particle has outlived its lifespan.
    public boolean isDead() {
        return timer > lifespan;
    }

    // Draws the particle, fading its alpha as it ages.
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
