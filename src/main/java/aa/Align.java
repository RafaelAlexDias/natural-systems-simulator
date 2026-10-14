package aa;

import physics.Body;
import processing.core.PVector;

/**
 * Steers a boid toward the average velocity of nearby flockmates.
 * This implements Reynolds' alignment rule.
 */
public class Align extends Behavior {
    /**
     * Creates a new alignment behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public Align(float weight) {
        super(weight);
    }

    /**
     * Returns the desired velocity that aligns the boid with nearby flockmates.
     * Computes the average velocity of visible neighbors and uses it as the target.
     */
    @Override
    public PVector getDesiredVelocity(Boid me) {
        PVector vd = me.getVel().copy();
        for (Body b : me.eye.getFarSight()) {
            vd.add(b.getVel());
        }
        return vd.div(me.eye.getFarSight().size()+1);
    }
}
