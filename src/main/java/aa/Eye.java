package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

/**
 * Vision system for a boid. Tracks candidate bodies, computes which of them
 * fall within far and near sight ranges, and exposes the current target.
 */
public class Eye {
    private List<Body> allTrackingBodies;
    private List<Body> farSight;
    private List<Body> nearSight;
    private Boid me;
    protected Body target;

    /**
     * Creates an eye for the given boid, tracking the provided bodies.
     * The first body in the list becomes the initial target.
     *
     * @param me the boid that owns this eye
     * @param allTrackingBodies the bodies this eye can observe
     */
    public Eye(Boid me, List<Body> allTrackingBodies) {
        this.me = me;
        this.allTrackingBodies = allTrackingBodies;
        target = allTrackingBodies.get(0);
    }

    /**
     * Creates a new eye for a boid by copying the tracked bodies and target
     * from another eye.
     *
     * @param me the boid that owns this new eye
     * @param eye the eye to copy tracking data from
     */
    public Eye(Boid me, Eye eye) {
        allTrackingBodies = eye.allTrackingBodies;
        this.me = me;
        target = eye.target;
    }

    /**
     * Returns the list of all bodies this eye tracks.
     *
     * @return the tracked bodies
     */
    public List<Body> getAllTrackingBodies() {
        return this.allTrackingBodies;
    }

    /**
     * Sets the list of bodies this eye tracks.
     *
     * @param allTrackingBodies the new list of tracked bodies
     */
    public void setAllTrackingBodies(List<Body> allTrackingBodies) {
        this.allTrackingBodies = allTrackingBodies;
    }

    /**
     * Returns the bodies currently seen within far vision range.
     *
     * @return the list of bodies in far sight
     */
    public List<Body> getFarSight() {
        return farSight;
    }

    /**
     * Returns the bodies currently seen within near (safe) vision range.
     *
     * @return the list of bodies in near sight
     */
    public List<Body> getNearSight() {
        return nearSight;
    }

    /**
     * Updates the far and near sight lists based on the boid's current vision.
     */
    public void look() {
        farSight = new ArrayList<Body>();
        nearSight = new ArrayList<Body>();
        for (Body b : allTrackingBodies) {
            if (farSight(b.getPos())) {
                farSight.add(b);
            }
            if (nearSight(b.getPos())) {
                nearSight.add(b);
            }
        }
    }

    /**
     * Checks whether a point is within the given distance and angle of view.
     *
     * @param t the point to test
     * @param maxDistance the maximum viewing distance
     * @param maxAngle the maximum viewing angle
     * @return true if the point is visible
     */
    public boolean inSight(PVector t, float maxDistance, float maxAngle) {
        PVector r = PVector.sub(t, me.getPos());
        float d = r.mag();
        float angle = PVector.angleBetween(r, me.getVel());
        return ((d > 0) && (d < maxDistance) && (angle < maxAngle));
    }

    /**
     * Checks whether a point is within far vision range.
     *
     * @param t the point to test
     * @return true if the point is in far sight
     */
    private boolean farSight(PVector t) {
        return inSight(t, me.dna.visionDistance, me.dna.visionAngle);
    }

    /**
     * Checks whether a point is within near (safe) vision range.
     *
     * @param t the point to test
     * @return true if the point is in near sight
     */
    private boolean nearSight(PVector t) {
        return inSight(t, me.dna.visionSafeDistance, (float)Math.PI);
    }

    /**
     * Returns the closest body currently in sight and sets it as the target.
     *
     * @return the closest visible body, or null if none
     */
    public Body getClosestBoid() {
        Body closestBoid = null;
        float closestDistance = 1000;
        for(Body b : getBoidsInSight()) {
            float distance = PVector.dist(me.getPos(), b.getPos());
            if(distance <= closestDistance) {
                closestDistance = distance;
                closestBoid = b;
                target = b;
            }
        }
        return closestBoid;
    }

    /**
     * Selects the nearest tracked body and sets it as the current target.
     *
     * @return the nearest body, or null if there are no tracked bodies
     */
    public Body nextTarget(){
        if (allTrackingBodies.size() == 0){
            return null;
        }
        // Start with the first body as the reference candidate.
        Body body = allTrackingBodies.get(0);
        // Reference distance to compare against.
        float dist = PVector.sub(me.getPos(), body.getPos()).mag();
        for (Body aux : allTrackingBodies){
            float newdist =  PVector.sub(me.getPos(),aux.getPos()).mag();
            // If a closer body is found, update the reference and candidate.
            if (newdist < dist){
                dist =  newdist;
                body = aux;
            }
        }
        target = body;
        return body;
    }

    /**
     * Returns the bodies currently within the boid's field of vision.
     *
     * @return the list of visible bodies
     */
    public List<Body> getBoidsInSight() {
        List<Body> boidsInSight = new ArrayList<>();
        for (Body b : allTrackingBodies) {
            if (inSight(b.getPos(), me.dna.visionDistance, me.dna.visionAngle)) {
                boidsInSight.add(b);
            }
        }
        return boidsInSight;
    }

    /**
     * Draws the boid's field of vision, direction, and safe distance.
     */
    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        p.pushMatrix();
        float[] pp = plt.getPixelCoord(me.getPos().x, me.getPos().y);
        p.translate(pp[0], pp[1]);
        p.rotate(-me.getVel().heading());
        p.stroke(0, 255, 0);
        p.strokeWeight(2);
        float velLineLength = 20;
        p.line(0, 0, velLineLength, 0);
        p.noFill();
        p.stroke(255, 0, 0);
        p.strokeWeight(3);
        float[] dd = plt.getVectorCoord(me.dna.visionDistance, me.dna.visionSafeDistance);
        p.rotate(me.dna.visionAngle);
        p.line(0, 0, dd[0], 0);
        p.rotate(-2 * me.dna.visionAngle);
        p.line(0, 0, dd[0], 0);
        p.rotate(me.dna.visionAngle);
        p.arc(0, 0, 2 * dd[0], 2 * dd[0], -me.dna.visionAngle, me.dna.visionAngle);
        p.stroke(255, 0, 255);
        p.circle(0, 0, 2 * dd[1]);
        p.popMatrix();
        p.popStyle();
    }
}