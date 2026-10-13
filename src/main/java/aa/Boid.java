package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Boid extends Body {

    private SubPlot plt;
    protected String shapeString;
    protected PShape shape;
    protected DNA dna;
    protected Eye eye;
    protected List<Behavior> behaviors;
    protected float phiWander;
    private double[] window;
    private float sumWeights;
    private boolean dead = false;

    // Construtor de um "Boid"
    protected Boid(PVector pos, float mass, float radius,
                   int color, PApplet p, SubPlot plt) {
        super(pos, new PVector(), mass, radius, color);
        dna = new DNA();
        behaviors = new ArrayList<Behavior>();
        this.plt = plt;
        window = plt.getWindow();
        setShape(p, plt);
    }

    protected Boid(PVector pos, float mass, float radius,
                   String shapeString, PApplet p, SubPlot plt) {
        super(pos, new PVector(), mass, radius, shapeString);
        dna = new DNA();
        behaviors = new ArrayList<Behavior>();
        this.shapeString = shapeString;
        this.shape = p.loadShape(shapeString);
        this.plt = plt;
        window = plt.getWindow();
        // setShape(p, plt);
    }

    public void mutateBehaviors() {
        for(Behavior behavior : behaviors) {
            if(behavior instanceof AvoidObstacle) {
                behavior.weight += DNA.random(-0.5f, 0.5f);
                behavior.weight = Math.max(0, behavior.weight);
            }
        }
        updateSumWeights();
    }

    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    public DNA getDNA() {
        return dna;
    }

    // Método setter para um Eye
    public void setEye(Eye eye) {
        this.eye = eye;
    }

    // Método getter para um Eye
    public Eye getEye() {
        return eye;
    }

    // Método getter para um radius
    public float getRadius() {
        return radius;
    }

    // Método para configurar uma determinada forma de um "Boid" com uma cor
    public void setShape(PApplet p, SubPlot plt, int color) {
        this.color = color;
        setShape(p, plt);
    }

    // Método para configurar a forma de um "Boid"
    public void setShape(PApplet p, SubPlot plt) {
        float[] rr = plt.getVectorCoord(radius, radius);
        shape = p.createShape();
        shape.beginShape();
        shape.noStroke();
        shape.fill(color);
        shape.vertex(-rr[0], rr[0]/2);
        shape.vertex(rr[0], 0);
        shape.vertex(-rr[0], -rr[0]/2);
        shape.vertex(-rr[0], 0);
        shape.endShape(PConstants.CLOSE);
    }

    // Método para atualizar a soma do peso dos comportamentos
    private void updateSumWeights() {
        sumWeights = 0;
        for (Behavior behavior : behaviors) {
            sumWeights += behavior.getWeight();
        }
    }

    // Método para adicionar um comportamento à lista
    public void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
        updateSumWeights();
    }

    // Método para remover um comportamento à lista
    public void removeBehavior(Behavior behavior) {
        if (behaviors.contains(behavior)) {
            behaviors.remove(behavior);
        }
        updateSumWeights();
    }

    // Método para aplicar um certo comportamento
    public void applyBehavior(int i, float dt) {
        if (eye!= null) eye.look();
        Behavior behavior = behaviors.get(i);
        PVector vd = behavior.getDesiredVelocity(this);
        move(dt, vd);
    }

    // Método para aplicar todos os comportamentos da lista
    public void applyBehaviors(float dt) {
        if (eye!= null) eye.look();

        PVector vd = new PVector();
        for (Behavior behavior : behaviors) {
            PVector vdd = behavior.getDesiredVelocity(this);
            vdd.mult(behavior.getWeight()/sumWeights);
            vd.add(vdd);
        }
        move(dt, vd);
    }

    // Método para determinar a distância de um "Boid" a um certo target
    public float distanceToTarget(Body target) {
        return PVector.dist(this.pos, target.getPos());
    }

    // Método para aumentar a velocidade de um "Boid"
    public void increaseSpeed() {
        dna.maxSpeed += 1;
    }

    // Método para diminuir a velocidade de um "Boid"
    public void decreaseSpeed() {
        dna.maxSpeed -= 1;
        if (dna.maxSpeed < 0) {
            dna.maxSpeed = 0;
        }
    }

    // Método "move" de um "Boid"
    private void move(float dt, PVector vd) {
        vd.normalize().mult(dna.maxSpeed);
        PVector fs = PVector.sub(vd, vel);
        applyForce(fs.limit(dna.maxForce));
        super.move(dt);
        if (pos.x < window[0]) {
            pos.x += window[1] - window[0];
        }
        if (pos.y < window[2]) {
            pos.y += window[3] - window[2];
        }
        if (pos.x >= window[1]) {
            pos.x -= window[1] - window[0];
        }
        if (pos.y >= window[3]) {
            pos.y -= window[3] - window[2];
        }
    }

    // Método "display" de um "Boid"
    @Override
    public void display(PApplet p, SubPlot plt) {
        if (!dead) {
            p.pushMatrix();
            float[] pp = plt.getPixelCoord(pos.x, pos.y);
            // p.translate(pp[0], pp[1]);
            // p.rotate(-vel.heading());
            p.shape(shape, pp[0] - 10f, pp[1] - 10f, 20f, 20f);
            p.popMatrix();
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean b) {
        dead = b;
    }

}
