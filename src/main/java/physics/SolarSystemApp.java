package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

/**
 * Newtonian simulation of the Sun and the eight planets: every planet orbits
 * under the gravitational attraction of the Sun. The zoom level can be changed
 * with the 1, 2 and 3 keys.
 */
public class SolarSystemApp implements IProcessingApp {

    private float lastTime;
    private float dt;

    // Distances from each planet to the Sun (m): Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune.
    private float[] distancesToSun = {5.791e10f, 1.082e11f, 1.496e11f, 2.22794e11f, 7.7833e11f, 1.4294e12f, 2.87099e12f,
                                        4.5043e12f};

    // Orbital speed of each planet (m/s): Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune.
    private float[] planetSpeed = {4.787e4f, 3.502e4f, 3e4f, 2.413e4f, 1.307e4f, 9.67e3f, 6.81e3f, 5.45e3f};

    // Mass of each planet (kg): Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune.
    private float[] planetMass = {3.303e23f, 4.869e24f, 5.97e24f, 6.421e24f, 1.9e27f, 5.688e26f, 8.686e25f, 1.024e26f};

    // Radius of each planet (m): Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune.
    private float[] planetSize = {2.4397e9f, 6.0518e9f, 6.37814e9f, 3.3972e9f, 6.9911e10f, 5.8232e10f, 2.5362e10f, 2.4622e10f};

    // Earth's radius (m).
    private float earthRadius = 6.37814e6f;

    // Sun data.
    private float sunMass = 1.989e30f;

    private float radiusSunSize = 8.5634e9f;

    private float distEarthSun = 1.496e11f;

    // Viewport and window.
    // The window size uses the largest planet-to-Sun distance scaled by 1.2;
    // here that is Neptune's distance to the Sun.
    private float smallWindow = 1.496e11f;
    private float mediumWindow = 7.7833e11f;
    private float bigWindow = 4.5043e12f;
    private float[] viewport = {0, 0, 1, 1};
    private double[] window = {-1.2 * bigWindow, 1.2 * bigWindow, -1.2 * bigWindow, 1.2 * bigWindow};
    private SubPlot plt;
    private Body sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune;
    private PSControl psc;
    private List<ParticleSystem> pss;

    // One real second corresponds to one month in the simulation.
    private float speedUp = 60 * 60 * 24 * 30;

    // ParticleSystem parameters.
    private float[] velParams = {PApplet.radians(90), PApplet.radians(360), 1, 3};
    private float[] lifetimeParams = {1e5f, 1e6f};
    private float[] radiusParams = {1e8f, 1e9f};
    private float flow = 5;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, parent.color(255, 128, 0));

        lastTime = parent.millis();

        // Create the celestial bodies.
        sun = new Body(new PVector(), new PVector(), sunMass, radiusSunSize, parent.color(255, 128, 0));
        mercury = new Body(new PVector(0, distancesToSun[0]), new PVector(planetSpeed[0], 0),
                planetMass[0], planetSize[0], parent.color(119, 115, 114));
        venus = new Body(new PVector(0, distancesToSun[1]), new PVector(planetSpeed[1], 0),
                planetMass[1], planetSize[1], parent.color(194, 100, 0));
        earth = new Body(new PVector(0, distancesToSun[2]), new PVector(planetSpeed[2], 0),
                planetMass[2], planetSize[2], parent.color(0, 180, 120));
        mars = new Body(new PVector(0, distancesToSun[3]), new PVector(planetSpeed[3], 0),
                planetMass[3], planetSize[3], parent.color(253, 134, 94));
        jupiter = new Body(new PVector(0, distancesToSun[4]), new PVector(planetSpeed[4], 0),
                planetMass[4], planetSize[4], parent.color(163, 117, 93));
        saturn = new Body(new PVector(0, distancesToSun[5]), new PVector(planetSpeed[5], 0),
                planetMass[5], planetSize[5], parent.color(219, 188, 121));
        uranus = new Body(new PVector(0, distancesToSun[6]), new PVector(planetSpeed[6], 0),
                planetMass[6], planetSize[6], parent.color(80, 175, 220));
        neptune = new Body(new PVector(0, distancesToSun[7]), new PVector(planetSpeed[7], 0),
                planetMass[7], planetSize[7], parent.color(64, 101, 251));

        // Particle systems (currently unused).
        pss = new ArrayList<ParticleSystem>();
    }

    @Override
    public void draw(PApplet parent, float dt) {

        float currentTime = parent.millis();
        this.dt = (currentTime - lastTime) / 1000.0f; // Convert to seconds
        lastTime = currentTime;

        float[] pp = plt.getBoundingBox();
        parent.fill(14, 12, 17);
        parent.rect(pp[0], pp[1], pp[2], pp[3]);

        // Draw the Sun.
        sun.display(parent, plt);

        /*
        PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, parent.color(255, 128, 0));
        ParticleSystem ps = new ParticleSystem(new PVector(), new PVector() , 1f,0.2f, psc);
        pss.add(ps);

        // Apply a constant force to every particle system.
        for (ParticleSystem parSys : pss) {
            ps.applyForce(new PVector(0, 0));
        }
        // Move and draw every particle system.
        for (ParticleSystem parSys : pss) {
            ps.move(dt);
            ps.display(parent, plt);
        }
         */

        // Mercury.
        PVector fMercury = sun.attraction(mercury);
        mercury.applyForce(fMercury);
        mercury.move(dt * speedUp);
        mercury.display(parent, plt);

        // Venus.
        PVector fVenus = sun.attraction(venus);
        venus.applyForce(fVenus);
        venus.move(dt * speedUp);
        venus.display(parent, plt);

        // Earth.
        PVector fEarth = sun.attraction(earth);
        earth.applyForce(fEarth);
        earth.move(dt * speedUp);
        earth.display(parent, plt);

        // Mars.
        PVector fMars = sun.attraction(mars);
        mars.applyForce(fMars);
        mars.move(dt * speedUp);
        mars.display(parent, plt);

        // Jupiter.
        PVector fJupiter = sun.attraction(jupiter);
        jupiter.applyForce(fJupiter);
        jupiter.move(dt * speedUp);
        jupiter.display(parent, plt);

        // Saturn.
        PVector fSaturn = sun.attraction(saturn);
        saturn.applyForce(fSaturn);
        saturn.move(dt * speedUp);
        saturn.display(parent, plt);

        // Uranus.
        PVector fUranus = sun.attraction(uranus);
        uranus.applyForce(fUranus);
        uranus.move(dt * speedUp);
        uranus.display(parent, plt);

        // Neptune.
        PVector fNeptune = sun.attraction(neptune);
        neptune.applyForce(fNeptune);
        neptune.move(dt * speedUp);
        neptune.display(parent, plt);
    }

    // Called when a key is pressed: 1, 2 and 3 change the zoom level.
    @Override
    public void keyPressed(PApplet parent) {
        if (parent.key == '1') {
            window = new double[]{-1.2 * smallWindow, 1.2 * smallWindow, -1.2 * smallWindow, 1.2 * smallWindow};
            plt = new SubPlot(window, viewport, parent.width, parent.height);
        }
        if (parent.key == '2') {
            window = new double[]{-1.2 * mediumWindow, 1.2 * mediumWindow, -1.2 * mediumWindow, 1.2 * mediumWindow};
            plt = new SubPlot(window, viewport, parent.width, parent.height);
        }
        if (parent.key == '3') {
            window = new double[]{-1.1 * bigWindow, 1.1 * bigWindow, -1.1 * bigWindow, 1.1 * bigWindow};
            plt = new SubPlot(window, viewport, parent.width, parent.height);
        }
    }

    @Override
    public void mousePressed(PApplet parent) {}

    @Override
    public void mouseDragged(PApplet parent) {}
}
