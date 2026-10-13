package aa;

import processing.core.PVector;

public abstract class Behavior {
    protected float weight;

    // Construtor de um "Behavior"
    public Behavior(float weight) {
        this.weight = weight;
    }

    // Métodos "base" para qualquer comportamento
    public PVector getDesiredVelocity(Boid me) {
        return null;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    public float getWeight() {
        return weight;
    }
}
