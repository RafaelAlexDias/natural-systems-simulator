package aa;

import physics.Body;
import processing.core.PVector;

public class Flee extends Behavior{
    // Construtor do comportamento "Flee"
    public Flee(float weight) {
        super(weight);
    }

    // Método "getDesiredVelocity" dedicado para este comportamento
    @Override
    public PVector getDesiredVelocity(Boid me) {
        Body bodyTarget = me.eye.target;
        PVector vd = PVector.sub(bodyTarget.getPos(), me.getPos());
        return vd.mult(-1);
    }
}
