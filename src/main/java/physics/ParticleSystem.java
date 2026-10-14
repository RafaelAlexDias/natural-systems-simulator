package physics;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

/**
 * A moving emitter that spawns {@link Particle}s over time according to its
 * {@link PSControl} parameters.
 */
public class ParticleSystem extends Body{

    private List<Particle> particles;
    private PSControl psc;

    // Creates a particle system at the given position with the given control.
    protected ParticleSystem(PVector pos, PVector vel, float mass,
                             float radius, PSControl psc) {
        super(pos, vel, mass, radius, 0);
        this.psc = psc;
        this.particles = new ArrayList<Particle>();
    }

    // Returns the control parameters of this system.
    public PSControl getPSControl() {
        return psc;
    }

    // Moves the emitter, spawns new particles and reaps the dead ones.
    @Override
    public void move(float dt) {
        super.move(dt);
        addParticles(dt);
        for(int i = particles.size()-1; i>=0; i--) {
            Particle p = particles.get(i);
            p.move(dt);
            if (p.isDead()) {
                particles.remove(i);
            }
        }
    }

    // Spawns as many particles as the flow rate requires for this time step.
    public void addParticles(float dt) {
        float particlesPerFrame = psc.getFlow() * dt;
        int n = (int) particlesPerFrame;
        float f = particlesPerFrame - n;
        for (int i=0; i<n; i++) {
            addOneParticle();
        }
        if (Math.random() < f) {
            addOneParticle();
        }
    }

    // Spawns a single particle with random velocity, radius and lifetime.
    private void addOneParticle() {
        Particle particle = new Particle(
                new PVector(),
                PVector.random2D().mult(5),  // Random velocity
                psc.getRndRadius(),
                psc.getColor(),
                psc.getRndLifetime()
        );
        particles.add(particle);
    }

    // Draws all live particles.
    @Override
    public void display(PApplet p, SubPlot plt) {
        for (Particle particle : particles) {
            particle.display(p, plt);
        }
    }

}
