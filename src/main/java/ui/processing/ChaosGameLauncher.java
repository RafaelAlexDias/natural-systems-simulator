
package ui.processing;

import java.io.File;
import java.io.IOException;

public final class ChaosGameLauncher {

    private ChaosGameLauncher() {
    }

    public static void launch(String simulationId) {
        String javaExecutable =
                System.getProperty("java.home")
                        + File.separator + "bin"
                        + File.separator
                        + (isWindows() ? "java.exe" : "java");

        String classpath = System.getProperty("java.class.path");

        ProcessBuilder processBuilder = new ProcessBuilder(
                javaExecutable,
                "-cp",
                classpath,
                ChaosGameProcessMain.class.getName(),
                simulationId
        );

        processBuilder.inheritIO();

        try {
            processBuilder.start();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name")
                .toLowerCase()
                .contains("win");
    }
}