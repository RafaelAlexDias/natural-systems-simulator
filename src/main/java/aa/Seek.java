package aa;

import physics.Body;
import processing.core.PVector;

/**
 * Steers a boid directly toward its current target.
 */
public class Seek extends Behavior{
    /**
     * Creates a new seek behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public Seek(float weight) {
        super(weight);
    }

    /**
     * Returns the desired velocity that moves the boid toward the target.
     */
    @Override
    public PVector getDesiredVelocity(Boid me) {
        Body bodyTarget = me.eye.target;
        return PVector.sub(bodyTarget.getPos(), me.getPos());
    }
}
