package aa;

import physics.Body;
import processing.core.PVector;

/**
 * Steers a boid toward the predicted future position of a moving target.
 */
public class Pursuit extends Behavior {
    /**
     * Creates a new pursuit behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public Pursuit(float weight) {
        super(weight);
    }

    /**
     * Returns the desired velocity that intercepts the target's predicted position.
     */
    @Override
    public PVector getDesiredVelocity(Boid me) {
        Body bodyTarget = me.eye.target;
        PVector d = bodyTarget.getVel().mult(me.dna.deltaTPursuit);
        PVector target = PVector.add(bodyTarget.getPos(), d);
        return PVector.sub(target, me.getPos());
    }
}
