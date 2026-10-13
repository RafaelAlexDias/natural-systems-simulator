package ui.processing;

import core.Log;
import core.Simulations;
import processing.core.PApplet;

import java.lang.System.Logger;

/**
 * Entry point for the child JVM that runs a single Processing simulation.
 *
 * <p>The simulation identifier is passed as a command line argument and
 * forwarded to the sketch through Processing's own {@code args} mechanism,
 * avoiding shared mutable static state.
 */
public final class ProcessingMain {

    private static final Logger LOG = Log.get(ProcessingMain.class);

    private ProcessingMain() {
    }

    public static void main(String[] args) {
        String simulationId = args.length > 0 ? args[0] : null;

        if (simulationId == null || Simulations.byId(simulationId).isEmpty()) {
            LOG.log(
                    Logger.Level.ERROR,
                    "Missing or unknown simulation id: {0}",
                    simulationId
            );
            return;
        }

        PApplet.main(ProcessingSketch.class, simulationId);
    }
}
