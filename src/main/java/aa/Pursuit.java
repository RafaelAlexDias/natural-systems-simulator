package aa;

import physics.Body;
import processing.core.PVector;

public class Pursuit extends Behavior {
    // Construtor do comportamento "Pursuit"
    public Pursuit(float weight) {
        super(weight);
    }

    // Método "getDesiredVelocity" dedicado para este comportamento
    @Override
    public PVector getDesiredVelocity(Boid me) {
        Body bodyTarget = me.eye.target;
        PVector d = bodyTarget.getVel().mult(me.dna.deltaTPursuit);
        PVector target = PVector.add(bodyTarget.getPos(), d);
        return PVector.sub(target, me.getPos());
    }
}
