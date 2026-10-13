package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

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
        // Inicialização de um "SubPlot" e uma lista de "ParticleSystem"
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        pss = new ArrayList<ParticleSystem>();
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Limpeza da window
        parent.background(255);
        // Aplica uma força constante a todos os sistemas de partículas
        for (ParticleSystem ps : pss) {
            ps.applyForce(new PVector(0, 0));
        }
        // Move e faz "display" a todos os sistemas de partículas
        for (ParticleSystem ps : pss) {
            ps.move(dt);
            ps.display(parent, plt);
        }
        // Atualiza os parâmetros de velocidade com base na posição do mouse
        velParams[0] = PApplet.map(parent.mouseX, 0, parent.width,
                PApplet.radians(0), PApplet.radians(360));
        // Atualiza os parâmetros de velocidade para todos os sistemas de partículas
        for (ParticleSystem ps : pss) {
            PSControl psc = ps.getPSControl();
            psc.setVelParams(velParams);
        }
    }

    // Método para fazer algo quando uma determinada tecla é pressionada
    @Override
    public void keyPressed(PApplet parent) {}

    // Método para fazer algo quando o rato é pressionado
    @Override
    public void mousePressed(PApplet parent) {
        // Adiciona um novo sistema de partículas ao clicar com o mouse
        double[] ww = plt.getWorldCoord(parent.mouseX, parent.mouseY);
        int color = parent.color(parent.random(255), parent.random(255), parent.random(255));
        PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, color);
        ParticleSystem ps = new ParticleSystem(new PVector((float)ww[0], (float)ww[1]), new PVector() , 1f,
                0.2f, psc);
        pss.add(ps);
    }

    // Método para fazer algo quando o rato é arrastado
    @Override
    public void mouseDragged(PApplet parent) {}
}
