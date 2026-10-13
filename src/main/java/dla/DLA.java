package dla;

import core.Log;
import processing.core.PApplet;
import setup.IProcessingApp;

import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * Diffusion-Limited Aggregation.
 *
 * <p>A cluster is seeded in the centre of the window. Wandering particles stick
 * to it on contact, producing the characteristic branching pattern. The number
 * of simulation steps executed per frame can be adjusted with the keyboard.
 */
public class DLA implements IProcessingApp {

    private static final Logger LOG = Log.get(DLA.class);

    private static final int NUM_WALKERS = 20;
    private static final int NUM_MAX = 100;

    private static final int INITIAL_STEPS_PER_FRAME = 100;
    private static final int MIN_STEPS_PER_FRAME = 20;
    private static final int MAX_STEPS_PER_FRAME = 300;
    private static final int STEPS_INCREMENT = 10;

    private static final Rgb WANDER_COLOR = new Rgb(50, 168, 149);
    private static final Rgb STOPPED_COLOR = new Rgb(119, 50, 168);

    private final List<Walker> walkers = new ArrayList<>();

    private boolean paused;
    private int stepsPerFrame = INITIAL_STEPS_PER_FRAME;
    private int stoppedWalkers;

    private record Rgb(int r, int g, int b) {
    }

    @Override
    public void setup(PApplet p) {
        walkers.clear();
        stoppedWalkers = 0;

        walkers.add(new Walker(p.width / 2, p.height / 2));

        for (int i = 0; i < NUM_WALKERS; i++) {
            walkers.add(new Walker(p));
        }

        LOG.log(Logger.Level.DEBUG, "DLA initialised with {0} walkers", walkers.size());
    }

    @Override
    public void draw(PApplet p, float dt) {
        for (int step = 0; step < stepsPerFrame; step++) {
            p.background(0);
            updateWalkers(p);
            displayWalkers(p);
        }

        growClusterIfNeeded(p);
    }

    private void updateWalkers(PApplet p) {
        if (paused) {
            return;
        }

        for (Walker walker : walkers) {
            walker.updateState(walkers);
            walker.wander(p);
        }
    }

    private void displayWalkers(PApplet p) {
        for (Walker walker : walkers) {
            Rgb color = walker.getState() == Walker.State.WANDER
                    ? WANDER_COLOR
                    : STOPPED_COLOR;

            p.fill(color.r(), color.g(), color.b());
            walker.display(p);
        }
    }

    private void growClusterIfNeeded(PApplet p) {
        int currentStopped = countStopped();

        if (paused || currentStopped == stoppedWalkers) {
            return;
        }

        boolean withinLimit = NUM_MAX != (stoppedWalkers + NUM_WALKERS);
        stoppedWalkers = currentStopped;

        if (withinLimit) {
            walkers.add(new Walker(p));
        }
    }

    private int countStopped() {
        int count = 0;

        for (Walker walker : walkers) {
            if (walker.getState() == Walker.State.STOPPED) {
                count++;
            }
        }

        return count;
    }

    @Override
    public void keyPressed(PApplet p) {
        switch (p.key) {
            case ' ' -> paused = !paused;
            case 'w' -> adjustSpeed(STEPS_INCREMENT);
            case 's' -> adjustSpeed(-STEPS_INCREMENT);
            default -> {
            }
        }
    }

    private void adjustSpeed(int delta) {
        stepsPerFrame = PApplet.constrain(
                stepsPerFrame + delta,
                MIN_STEPS_PER_FRAME,
                MAX_STEPS_PER_FRAME
        );

        LOG.log(Logger.Level.DEBUG, "Steps per frame: {0}", stepsPerFrame);
    }

    @Override
    public void mousePressed(PApplet p) {
        setup(p);
    }
}
