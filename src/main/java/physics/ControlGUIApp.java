package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class ControlGUIApp implements IProcessingApp {

    private RigidBody rb;
    private MotionControl mc;
    private float mass = 1f;
    private RigidBody.ControlType ct = RigidBody.ControlType.POSITION;

    @Override
    public void setup(PApplet parent) {
        // Inicialização de um "RigidBody" e um "MotionControl"
        rb = new RigidBody(mass);
        mc = new MotionControl(ct, rb);
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Limpeza da window
        parent.background(255);

        parent.translate(parent.width/2, parent.height/2);
        // Movimento do "RigidBody"
        rb.move(dt, ct);
        // Display do "RigidBody" e do "MotionControl"
        rb.display(parent);
        mc.display(parent);
    }

    // Método para fazer algo quando uma determinada tecla é pressionada
    @Override
    public void keyPressed(PApplet parent) {
        // Altera o método de controlo do "RigidBody" de acordo com uma certa tecla
        if (parent.key == 'p') {
            ct = RigidBody.ControlType.POSITION;
        }
        if (parent.key == 'v') {
            ct = RigidBody.ControlType.VELOCITY;
        }
        if (parent.key == 'f') {
            ct = RigidBody.ControlType.FORCE;
        }
        rb = new RigidBody(mass);
        mc = new MotionControl(ct, rb);
    }

    // Método para fazer algo quando o rato é pressionado
    @Override
    public void mousePressed(PApplet parent) {
        // Posicionamento do vetor
        float x = parent.mouseX - parent.width/2;
        float y = parent.mouseY - parent.height/2;
        mc.setVector(new PVector(x, y));
    }

    // Método para fazer algo quando o rato é arrastado
    @Override
    public void mouseDragged(PApplet parent) {}
}
