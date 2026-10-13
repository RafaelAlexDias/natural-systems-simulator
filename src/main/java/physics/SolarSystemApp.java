package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class SolarSystemApp implements IProcessingApp {

    private float lastTime;
    private float dt;

    // Distances from planet to Sun {Mercúrio, Vénus, Terra, Marte, Júpiter, Saturno, Urano, Neptuno}
    private float[] distancesToSun = {5.791e10f, 1.082e11f, 1.496e11f, 2.22794e11f, 7.7833e11f, 1.4294e12f, 2.87099e12f,
                                        4.5043e12f};

    // Planet's Orbit Speed {Mercúrio, Vénus, Terra, Marte, Júpiter, Saturno, Urano, Neptuno}
    private float[] planetSpeed = {4.787e4f, 3.502e4f, 3e4f, 2.413e4f, 1.307e4f, 9.67e3f, 6.81e3f, 5.45e3f};

    // Planet's Mass {Mercúrio, Vénus, Terra, Marte, Júpiter, Saturno, Urano, Neptuno}
    private float[] planetMass = {3.303e23f, 4.869e24f, 5.97e24f, 6.421e24f, 1.9e27f, 5.688e26f, 8.686e25f, 1.024e26f};

    // Planet's radiusSize {Mercúrio, Vénus, Terra, Marte, Júpiter, Saturno, Urano, Neptuno}
    private float[] planetSize = {2.4397e9f, 6.0518e9f, 6.37814e9f, 3.3972e9f, 6.9911e10f, 5.8232e10f, 2.5362e10f, 2.4622e10f};

    // Tamanho Terra
    private float earthRadius = 6.37814e6f;

    // Dados para o Sol
    private float sunMass = 1.989e30f;

    private float radiusSunSize = 8.5634e9f;

    private float distEarthSun = 1.496e11f;

    // Viewport e Window
    // Para o tamanho da window, usámos a maior distância de um planeta ao sol e multiplicámos por 1.2
    // Neste caso o valor utilizado, é a distânica de Neptuno ao Sol
    private float smallWindow = 1.496e11f;
    private float mediumWindow = 7.7833e11f;
    private float bigWindow = 4.5043e12f;
    private float[] viewport = {0, 0, 1, 1};
    private double[] window = {-1.2 * bigWindow, 1.2 * bigWindow, -1.2 * bigWindow, 1.2 * bigWindow};
    private SubPlot plt;
    private Body sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune;
    private PSControl psc;
    private List<ParticleSystem> pss;

    // Cada segundo corresponde a um mês na simulação
    private float speedUp = 60 * 60 * 24 * 30;

    // Valores para ParticleSystem
    private float[] velParams = {PApplet.radians(90), PApplet.radians(360), 1, 3};
    private float[] lifetimeParams = {1e5f, 1e6f};
    private float[] radiusParams = {1e8f, 1e9f};
    private float flow = 5;

    @Override
    public void setup(PApplet parent) {
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, parent.color(255, 128, 0));

        lastTime = parent.millis();

        // Setup dos Corpos Celestiais
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

        // Sistema de partículas
        pss = new ArrayList<ParticleSystem>();
    }

    @Override
    public void draw(PApplet parent, float dt) {

        float currentTime = parent.millis();
        this.dt = (currentTime - lastTime) / 1000.0f; // Converte para segundos
        lastTime = currentTime;

        float[] pp = plt.getBoundingBox();
        parent.fill(14, 12, 17);
        parent.rect(pp[0], pp[1], pp[2], pp[3]);

        // Display Sol
        sun.display(parent, plt);

        /*
        PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, parent.color(255, 128, 0));
        ParticleSystem ps = new ParticleSystem(new PVector(), new PVector() , 1f,0.2f, psc);
        pss.add(ps);

        // Aplica uma força constante a todos os sistemas de partículas
        for (ParticleSystem parSys : pss) {
            ps.applyForce(new PVector(0, 0));
        }
        // Move e faz "display" a todos os sistemas de partículas
        for (ParticleSystem parSys : pss) {
            ps.move(dt);
            ps.display(parent, plt);
        }
         */

        // Display Mercúrio
        PVector fMercury = sun.attraction(mercury);
        mercury.applyForce(fMercury);
        mercury.move(dt * speedUp);
        mercury.display(parent, plt);

        // Display Vénus
        PVector fVenus = sun.attraction(venus);
        venus.applyForce(fVenus);
        venus.move(dt * speedUp);
        venus.display(parent, plt);

        // Display Terra
        PVector fEarth = sun.attraction(earth);
        earth.applyForce(fEarth);
        earth.move(dt * speedUp);
        earth.display(parent, plt);

        // Display Marte
        PVector fMars = sun.attraction(mars);
        mars.applyForce(fMars);
        mars.move(dt * speedUp);
        mars.display(parent, plt);

        // Display Jupiter
        PVector fJupiter = sun.attraction(jupiter);
        jupiter.applyForce(fJupiter);
        jupiter.move(dt * speedUp);
        jupiter.display(parent, plt);

        // Display Saturno
        PVector fSaturn = sun.attraction(saturn);
        saturn.applyForce(fSaturn);
        saturn.move(dt * speedUp);
        saturn.display(parent, plt);

        // Display Urano
        PVector fUranus = sun.attraction(uranus);
        uranus.applyForce(fUranus);
        uranus.move(dt * speedUp);
        uranus.display(parent, plt);

        // Display Neptune
        PVector fNeptune = sun.attraction(neptune);
        neptune.applyForce(fNeptune);
        neptune.move(dt * speedUp);
        neptune.display(parent, plt);
    }

    // Método para fazer algo quando uma determinada tecla é pressionada
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

    // Método para fazer algo quando o rato é pressionado
    @Override
    public void mousePressed(PApplet parent) {}

    // Método para fazer algo quando o rato é arrastado
    @Override
    public void mouseDragged(PApplet parent) {}
}
