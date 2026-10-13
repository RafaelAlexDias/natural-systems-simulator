package setup;

import processing.core.PApplet;

/**
 * Contract implemented by every Processing simulation.
 *
 * <p>{@link #setup(PApplet)} and {@link #draw(PApplet, float)} are mandatory.
 * The input handlers are intentionally optional: implementations only override
 * the ones they actually use, which keeps the simulation classes free of empty
 * boilerplate methods.
 */
public interface IProcessingApp {

    void setup(PApplet p);

    void draw(PApplet p, float dt);

    default void mousePressed(PApplet p) {
    }

    default void mouseDragged(PApplet p) {
    }

    default void keyPressed(PApplet p) {
    }
}
