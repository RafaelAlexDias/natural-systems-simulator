package ui.processing;

import core.Log;
import core.Simulation;
import core.Simulations;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;

/**
 * Launches a Processing simulation in a separate JVM so that a misbehaving
 * sketch cannot take down the JavaFX interface.
 */
public final class ProcessingLauncher {

    private static final Logger LOG = Log.get(ProcessingLauncher.class);

    private ProcessingLauncher() {
    }

    public static void launch(String simulationId) {
        boolean runnable = Simulations.byId(simulationId)
                .filter(Simulation::isRunnable)
                .isPresent();

        if (!runnable) {
            LOG.log(
                    Logger.Level.ERROR,
                    "Cannot launch unknown simulation: {0}",
                    simulationId
            );
            return;
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                javaExecutable(),
                "-cp",
                System.getProperty("java.class.path"),
                ProcessingMain.class.getName(),
                simulationId
        );

        processBuilder.inheritIO();

        try {
            Process process = processBuilder.start();
            LOG.log(
                    Logger.Level.DEBUG,
                    "Launched simulation {0} in process {1}",
                    simulationId,
                    process.pid()
            );
        } catch (IOException exception) {
            LOG.log(
                    Logger.Level.ERROR,
                    "Failed to launch simulation " + simulationId,
                    exception
            );
        }
    }

    private static String javaExecutable() {
        String executable = isWindows() ? "java.exe" : "java";

        return System.getProperty("java.home")
                + File.separator + "bin"
                + File.separator + executable;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name")
                .toLowerCase()
                .contains("win");
    }
}
