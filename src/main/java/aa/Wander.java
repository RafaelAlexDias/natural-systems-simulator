package aa;

import processing.core.PVector;

/**
 * Produces a smooth, random wandering path by steering toward a point on a
 * circle projected ahead of the boid.
 */
public class Wander extends Behavior {
    /**
     * Creates a new wander behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public Wander(float weight) {
        super(weight);
    }

    /**
     * Returns the desired velocity toward a randomly displaced point on the
     * wander circle, advancing the wander angle each step.
     */
    @Override
    public PVector getDesiredVelocity(Boid me) {
        PVector center = me.getPos().copy();
        center.add(me.getVel().copy().mult(me.dna.deltaTWander));
        PVector target = new PVector(me.dna.radiusWander * (float)Math.cos(me.phiWander),
                me.dna.radiusWander * (float)Math.sin(me.phiWander));
        target.add(center);
        me.phiWander += 2*(Math.random()-0.5)*me.dna.deltaPhiWander;
        return PVector.sub(target, me.getPos());
    }
}
