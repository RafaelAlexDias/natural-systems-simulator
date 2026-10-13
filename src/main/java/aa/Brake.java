package aa;

import processing.core.PVector;

public class Brake extends Behavior {
    // Construtor do comportamento "Brake"
    public Brake(float weight) {
        super(weight);
    }

    // Método "getDesiredVelocity" dedicado para este comportamento
    @Override
    public PVector getDesiredVelocity(Boid me) {
        return new PVector();
    }
}
