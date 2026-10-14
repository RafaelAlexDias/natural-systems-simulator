package physics;

import processing.core.PVector;

/**
 * Parameters controlling how a {@link ParticleSystem} emits particles: their
 * velocity range, lifetime range, radius range, colour and flow rate.
 */
public class PSControl {
    private float averageAngle;
    private float dispersionAngle;
    private float minVelocity;
    private float maxVelocity;
    private float minLifetime;
    private float maxLifetime;
    private float minRadius;
    private float maxRadius;
    private float flow;
    private int color;

    // Creates a control block from velocity, lifetime and radius parameters.
    public PSControl(float[] velControl, float[] lifetime, float[] radius,
                     float flow, int color) {
        setVelParams(velControl);
        setLifetimeParams(lifetime);
        setRadiusParams(radius);
        setFlow(flow);
        setColor(color);
    }

    // Sets the velocity parameters {average angle, dispersion, min, max}.
    public void setVelParams (float[] velControl) {
        averageAngle = velControl[0];
        dispersionAngle = velControl[1];
        minVelocity = velControl[2];
        maxVelocity = velControl[3];
    }

    // Sets the emission flow (particles per unit time).
    public void setFlow(float flow) {
        this.flow = flow;
    }

    // Sets the particle colour.
    public void setColor(int color) {
        this.color = color;
    }

    // Returns the emission flow.
    public float getFlow() {
        return flow;
    }

    // Returns the particle colour.
    public int getColor() {
        return color;
    }

    // Returns a random radius within the configured range.
    public float getRndRadius() {
        return getRnd(minRadius, maxRadius);
    }

    // Returns a random lifetime within the configured range.
    public float getRndLifetime() {
        return getRnd(minLifetime, maxLifetime);
    }

    // Sets the radius range {min, max}.
    public void setRadiusParams(float[] radius) {
        minRadius = radius[0];
        maxRadius = radius[1];
    }

    // Sets the lifetime range {min, max}.
    public void setLifetimeParams(float[] lifetime) {
        minLifetime = lifetime[0];
        maxLifetime = lifetime[1];
    }

    // Returns a random velocity inside the configured angle and speed range.
    public PVector getRndVel() {
        float angle = getRnd(averageAngle - dispersionAngle/2, averageAngle + dispersionAngle/2);
        PVector v = PVector.fromAngle(angle);
        return v.mult(getRnd(minVelocity, maxVelocity));
    }

    // Returns a random number in [min, max).
    public static float getRnd(float min, float max) {
        return min + (float) (Math.random() * (max - min));
    }
}
