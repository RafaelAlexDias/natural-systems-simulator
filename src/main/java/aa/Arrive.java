package aa;

import processing.core.PVector;

/**
 * Steers a boid toward a target, slowing down as it approaches.
 * This implements an arrival behavior with a deceleration radius.
 */
public class Arrive extends Behavior {
    /**
     * Creates a new arrive behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public Arrive(float weight) {
        super(weight);
    }

    /**
     * Returns the desired velocity that moves the boid toward its target.
     * Velocity is scaled down when within the arrival radius defined by the boid's DNA.
     */
    @Override
    public PVector getDesiredVelocity(Boid me) {
        PVector vd = PVector.sub(me.eye.target.getPos(), me.getPos());
        float dist = vd.mag();
        float R = me.dna.radiusArrive;
        if (dist < R) {
            vd.mult(dist/R);
        }
        return vd;
    }
}
