package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

/**
 * A ball falling under gravity through air and then water, showing the effect of
 * each fluid's drag. The fall time is printed once it reaches the bottom.
 */
public class FallingBodyApp implements IProcessingApp {
    public static final float dimX = 20;
    public static final float dimY = 15;
    private static final float g = -9.8f;
    private static final float mass = 80;
    private static final float radius = 0.5f;
    private final double window[] = {0, dimX, 0, dimY};
    private float viewport[] = {0f, 0f, 1f, 1f};
    private SubPlot plt;
    private Body ball;
    private Water water;
    private Air air;
    private float speedUp = 0.5f;
    private float timer;

    @Override
    public void setup(PApplet parent) {
        // Create the subplot, the ball, the two fluids and the timer.
        plt = new SubPlot(window, viewport, parent.width, parent.height);
        ball = new Body(new PVector(0, 12), new PVector(8, 6),
                    mass, radius, parent.color(255, 255, 0));
        water = new Water(4, parent.color(0, 255, 255));
        air = new Air();
        timer = 0.0f;
    }

    @Override
    public void draw(PApplet parent, float dt) {
        // Apply gravity to the ball.
        PVector f = new PVector(0, mass*g);
        ball.applyForce(f);
        //  Check whether the ball is inside the water.
        if (water.isInside(ball)) {
            // Inside the water, apply the water drag.
            f = water.drag(ball);
        }
        else {
            // Outside the water, apply the air drag.
            f = air.drag(ball);
        }
        ball.applyForce(f);
        // Move the ball.
        ball.move(speedUp*dt);
        // Draw the water and the ball.
        water.display(parent, plt);
        ball.display(parent, plt);
        // Advance the timer.
        timer += (speedUp * dt);
        // When the ball reaches the bottom, stop and print the elapsed time.
        if (ball.pos.y < 0) {
            parent.noLoop();
            System.out.println(timer);
        }
    }

    @Override
    public void keyPressed(PApplet parent) {}

    @Override
    public void mousePressed(PApplet parent) {}

    @Override
    public void mouseDragged(PApplet parent) {}
}
