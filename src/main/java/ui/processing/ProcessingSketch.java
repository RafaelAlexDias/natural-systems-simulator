package ui.processing;

import core.AppInfo;
import core.Log;
import core.Simulation;
import core.Simulations;
import processing.core.PApplet;
import processing.core.PImage;
import setup.IProcessingApp;

import java.io.IOException;
import java.io.InputStream;
import java.lang.System.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Host sketch that dispatches the input events to a single
 * {@link IProcessingApp} selected from {@link Simulations}.
 */
public class ProcessingSketch extends PApplet {

    private static final Logger LOG = Log.get(ProcessingSketch.class);

    private IProcessingApp simulation;

    @Override
    public void settings() {
        size(800, 700);
    }

    @Override
    public void setup() {
        String simulationId = args != null && args.length > 0 ? args[0] : null;

        Simulation selected = Simulations.byId(simulationId).orElse(null);

        if (selected == null || !selected.isRunnable()) {
            LOG.log(Logger.Level.ERROR, "Unknown simulation id: {0}", simulationId);
            exit();
            return;
        }

        simulation = selected.create();
        surface.setTitle("MSSN \u2014 " + selected.name());
        applyWindowIcon();

        background(255);
        simulation.setup(this);

        LOG.log(Logger.Level.DEBUG, "Started simulation: {0}", selected.id());
    }

    /**
     * Copies the bundled icon to a temporary file so it can be loaded through
     * Processing's file based {@code loadImage} and applied to the window.
     */
    private void applyWindowIcon() {
        try (InputStream stream = getClass().getResourceAsStream(AppInfo.ICON_RESOURCE)) {
            if (stream == null) {
                return;
            }

            Path iconFile = Files.createTempFile("mssn-icon", ".png");
            iconFile.toFile().deleteOnExit();
            Files.copy(stream, iconFile, StandardCopyOption.REPLACE_EXISTING);

            PImage icon = loadImage(iconFile.toAbsolutePath().toString());
            if (icon != null) {
                getSurface().setIcon(icon);
            }
        } catch (IOException | RuntimeException exception) {
            LOG.log(Logger.Level.DEBUG, "Could not set window icon", exception);
        }
    }

    @Override
    public void draw() {
        if (simulation == null) {
            return;
        }

        float dt = frameRate > 0 ? 1.0f / frameRate : 0.0f;
        simulation.draw(this, dt);
    }

    @Override
    public void mousePressed() {
        if (simulation != null) {
            simulation.mousePressed(this);
        }
    }

    @Override
    public void mouseDragged() {
        if (simulation != null) {
            simulation.mouseDragged(this);
        }
    }

    @Override
    public void keyPressed() {
        if (simulation != null) {
            simulation.keyPressed(this);
        }
    }
}
