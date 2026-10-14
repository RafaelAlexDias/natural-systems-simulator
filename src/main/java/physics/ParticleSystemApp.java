package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactive demo where clicking spawns a {@link ParticleSystem} and the mouse
 * x-position sets the launch angle.
 */
public class ParticleSystemApp implements IProcessingApp {

    private List<ParticleSystem> pss;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private float[] velParams = {PApplet.radians(90), PApplet.radians(360), 1, 3};
    private float[] lifetimeParams = {0.5f, 2};
    private float[] radiusParams = {1f, 3f};
    private float flow = 500;

    @Override
    public void setup(PApplet parent) {
        // Create the subplot and start with an empty list of systems.
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        pss = new ArrayList<ParticleSystem>();
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Clear the window.
        parent.background(255);
        // Apply a constant (zero) force to every particle system.
        for (ParticleSystem ps : pss) {
            ps.applyForce(new PVector(0, 0));
        }
        // Move and draw every particle system.
        for (ParticleSystem ps : pss) {
            ps.move(dt);
            ps.display(parent, plt);
        }
        // Update the launch angle from the mouse x-position.
        velParams[0] = PApplet.map(parent.mouseX, 0, parent.width,
                PApplet.radians(0), PApplet.radians(360));
        // Propagate the new velocity parameters to every system.
        for (ParticleSystem ps : pss) {
            PSControl psc = ps.getPSControl();
            psc.setVelParams(velParams);
        }
    }

    @Override
    public void keyPressed(PApplet parent) {}

    @Override
    public void mousePressed(PApplet parent) {
        // Spawn a new particle system where the mouse was clicked.
        double[] ww = plt.getWorldCoord(parent.mouseX, parent.mouseY);
        int color = parent.color(parent.random(255), parent.random(255), parent.random(255));
        PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, color);
        ParticleSystem ps = new ParticleSystem(new PVector((float)ww[0], (float)ww[1]), new PVector() , 1f,
                0.2f, psc);
        pss.add(ps);
    }

    @Override
    public void mouseDragged(PApplet parent) {}
}
