package aa;

import physics.Body;
import processing.core.PVector;

/**
 * Steers a boid away from a moving target by predicting its future position.
 * This is the inverse of the pursuit behavior.
 */
public class Evade extends Behavior {
    /**
     * Creates a new evade behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public Evade(float weight) {
        super(weight);
    }

    /**
     * Returns the desired velocity that moves the boid away from the predicted
     * future position of the target.
     */
    @Override
    public PVector getDesiredVelocity(Boid me) {
        Body bodyTarget = me.eye.target;
        PVector d = bodyTarget.getVel().mult(me.dna.deltaTPursuit);
        PVector target = PVector.add(bodyTarget.getPos(), d);
        PVector vd = PVector.sub(target, me.getPos());
        return vd.mult(-1);
    }
}
