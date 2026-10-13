package aa;

import physics.Body;
import processing.core.PVector;

public class Seek extends Behavior{
    // Construtor do comportamento "Seek"
    public Seek(float weight) {
        super(weight);
    }

    // Método "getDesiredVelocity" dedicado para este comportamento
    @Override
    public PVector getDesiredVelocity(Boid me) {
        Body bodyTarget = me.eye.target;
        return PVector.sub(bodyTarget.getPos(), me.getPos());
    }
}
