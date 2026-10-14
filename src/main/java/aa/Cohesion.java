package aa;

import physics.Body;
import processing.core.PVector;

/**
 * Steers a boid toward the average position of nearby flockmates.
 * This implements Reynolds' cohesion rule.
 */
public class Cohesion extends Behavior {
    /**
     * Creates a new cohesion behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public Cohesion(float weight) {
        super(weight);
    }

    /**
     * Returns the desired velocity that moves the boid toward the center of mass
     * of nearby flockmates.
     */
    @Override
    public PVector getDesiredVelocity(Boid me) {
        PVector target = me.getPos().copy();
        for (Body b : me.eye.getFarSight()) {
            target.add(b.getPos());
        }
        target.div(me.eye.getFarSight().size()+1);
        return PVector.sub(target, me.getPos());
    }
}
