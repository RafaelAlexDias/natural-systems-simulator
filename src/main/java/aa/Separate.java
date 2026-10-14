package aa;

import physics.Body;
import processing.core.PVector;

/**
 * Steers a boid away from nearby flockmates to avoid crowding.
 * This implements Reynolds' separation rule.
 */
public class Separate extends Behavior {
    /**
     * Creates a new separation behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public Separate(float weight) {
        super(weight);
    }

    /**
     * Returns the desired velocity that pushes the boid away from very close
     * neighbors, weighted by inverse square distance.
     */
    @Override
    public PVector getDesiredVelocity(Boid me) {
        PVector vd = new PVector();
        for (Body b : me.eye.getNearSight()) {
            PVector r = PVector.sub(me.getPos(), b.getPos());
            float d = r.mag();
            r.div(d*d);
            vd.add(r);
        }
        return vd;
    }
}
