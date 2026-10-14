package aa;

import physics.Body;
import processing.core.PVector;

/**
 * Steers a boid directly away from its current target.
 */
public class Flee extends Behavior{
    /**
     * Creates a new flee behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public Flee(float weight) {
        super(weight);
    }

    /**
     * Returns the desired velocity that moves the boid away from the target.
     */
    @Override
    public PVector getDesiredVelocity(Boid me) {
        Body bodyTarget = me.eye.target;
        PVector vd = PVector.sub(bodyTarget.getPos(), me.getPos());
        return vd.mult(-1);
    }
}
