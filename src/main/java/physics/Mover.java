package physics;

import processing.core.PVector;

public abstract class Mover {
    protected PVector pos;
    protected PVector acc;
    protected PVector vel;
    protected float mass;
    private static double G = 6.67e-11;

    // Construtor de um "Mover"
    protected Mover(PVector pos, PVector vel, float mass) {
        this.pos = pos.copy();
        this.vel = vel;
        this.mass = mass;
        acc = new PVector();
    }

    // Método para calcular a força de atração entre dois objetos
    public PVector attraction(Mover m) {
        PVector r = PVector.sub(pos, m.pos);
        float dist = r.mag();
        float strenght = (float) (G * mass * m.mass / Math.pow(dist, 2));

        return r.normalize().mult(strenght);
    }

    // Método para aplicar uma força ao objeto
    public void applyForce(PVector force) {
        acc.add(PVector.div(force, mass));
    }

    // Método para atualizar a posição e velocidade do objeto com o tempo
    public void move(float dt) {
        vel.add(acc.mult(dt));
        pos.add(PVector.mult(vel, dt));
        acc.mult(0);
    }

    // Métodos para definir a posição do objeto
    public void setPos(PVector pos) {
        this.pos = pos;
    }

    // Métodos para definir a velocidade do objeto
    public void setVel(PVector vel) {
        this.vel = vel;
    }

    // Métodos para obter a posição do objeto
    public PVector getPos() {
        return pos;
    }

    // Métodos para obter a velocidade do objeto
    public PVector getVel() {
        return vel;
    }
}
