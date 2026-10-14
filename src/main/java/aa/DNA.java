package aa;

/**
 * Genetic parameters that define a boid's physical limits and behavior tuning,
 * such as speed, force, vision, pursuit, arrival, and wander settings.
 */
public class DNA {
    public float maxSpeed;
    public float maxForce;
    public float visionDistance;
    public float visionSafeDistance;
    public float visionAngle;
    public float deltaTPursuit;
    public float radiusArrive;
    public float deltaTWander;
    public float radiusWander;
    public float deltaPhiWander;

    /**
     * Creates a new DNA instance with randomized parameters.
     */
    public DNA() {
        maxSpeed = random(1f, 1.5f);
        maxForce = random(4f, 7f);
        visionDistance = random(1.5f, 2.5f);
        visionSafeDistance = 0.25f * visionDistance;
        visionAngle = (float)Math.PI * 0.3f;
        deltaTPursuit = random(0.5f, 1f);
        radiusArrive = random(3, 5);
        deltaTWander = random(1f, 1f);
        radiusWander = random(3f, 3f);
        deltaPhiWander = (float) Math.PI/8;
    }

    /**
     * Creates a new DNA instance by copying an existing one.
     *
     * @param dna the DNA to copy
     * @param mutate whether to apply random mutations
     */
    public DNA(DNA dna, boolean mutate) {
        maxSpeed = dna.maxSpeed;
        maxForce = dna.maxForce;

        visionDistance = dna.visionDistance;
        visionSafeDistance = dna.visionSafeDistance;
        visionAngle = dna.visionAngle;

        deltaTPursuit = dna.deltaTPursuit;
        radiusArrive = dna.radiusArrive;

        deltaTWander = dna.deltaTWander;
        deltaPhiWander = dna.deltaPhiWander;
        radiusWander = dna.radiusWander;
        if(mutate) mutate();
    }

    /**
     * Applies random mutations to the DNA parameters.
     */
    private void mutate() {
        maxSpeed += random(-0.2f, 0.2f);
        maxSpeed = Math.max(0, maxSpeed);
    }

    /**
     * Returns a random float value between min and max.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the random value
     */
    public static float random(float min, float max) {
        return (float)(min +(max - min) * Math.random());
    }
}
