package physics;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class ParticleSystem extends Body{

    private List<Particle> particles;
    private PSControl psc;

    // Construtor de um "ParticleSystem"
    protected ParticleSystem(PVector pos, PVector vel, float mass,
                             float radius, PSControl psc) {
        super(pos, vel, mass, radius, 0);
        this.psc = psc;
        this.particles = new ArrayList<Particle>();
    }

    // Método para obter o "PSControl" de um "ParticleSystem"
    public PSControl getPSControl() {
        return psc;
    }

    // Método "move" de um "ParticleSystem"
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

    // Método para adicionar várias "Particle"
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

    // Método para adicionar uma "Particle"
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

    // Método "display" de um "ParticleSystem"
    @Override
    public void display(PApplet p, SubPlot plt) {
        for (Particle particle : particles) {
            particle.display(p, plt);
        }
    }

}
