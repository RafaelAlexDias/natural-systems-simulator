package aa;

import physics.Body;
import processing.core.PVector;

/**
 * Basic obstacle avoidance behavior for a boid.
 * Attempts to steer away from obstacles detected in the boid's field of view.
 */
public class AvoidObstacle extends Behavior {

    /**
     * Creates a new obstacle avoidance behavior with the specified weight.
     *
     * @param weight the steering weight applied to this behavior
     */
    public AvoidObstacle(float weight) {
        super(weight);
    }

    /**
     * Returns a desired velocity to avoid an obstacle if one is detected.
     * If no obstacle is detected, returns the current velocity.
     */
    @Override
    public PVector getDesiredVelocity(Boid me) {
        float s = hasObstacle(me);
        if(s==0) {
            return me.getVel().copy();
        }
        PVector vd = new PVector(me.getVel().y, -me.getVel().x);
        if(s>0) {
            vd.mult(-1);
        }
        return vd;
    }

    /**
     * Checks for obstacles in the boid's field of view and returns a sign
     * indicating which side to turn to avoid them.
     *
     * @param me the boid to check for obstacles
     * @return 0 if no obstacle is detected, a positive or negative value to indicate side
     */
    private float hasObstacle(Boid me) {
        for(Body body : me.eye.getFarSight()) {
            PVector r = PVector.sub(body.getPos(), me.getPos());
            PVector vd = new PVector(me.getVel().y, -me.getVel().x);
            return PVector.dot(vd, r);
        }
        return 0;
    }
}
