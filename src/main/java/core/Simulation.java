package core;

import setup.IProcessingApp;

import java.time.LocalDate;
import java.util.function.Supplier;

/**
 * Immutable description of a simulation offered by the application.
 *
 * <p>It is the single source of truth for a simulation's identity, its
 * presentation metadata and the factory used to instantiate the Processing
 * sketch. Keeping all of this together prevents the identifiers used by the
 * UI and by the Processing launcher from drifting apart.
 *
 * @param id          stable identifier shared with the Processing process
 * @param name        human readable name shown on the cards
 * @param category    grouping used by the filters
 * @param description short explanation shown on the cards
 * @param icon        short glyph used when no preview image is available
 * @param imagePath   classpath resource of the preview image, may be {@code null}
 * @param dateAdded   date used by the "recently added" ordering
 * @param factory     creates the Processing sketch, {@code null} when the
 *                    simulation is not runnable yet
 */
public record Simulation(
        String id,
        String name,
        String category,
        String description,
        String icon,
        String imagePath,
        LocalDate dateAdded,
        Supplier<IProcessingApp> factory
) {

    public Simulation {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Simulation id must not be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Simulation name must not be blank");
        }
    }

    /**
     * @return {@code true} when this simulation can actually be launched.
     */
    public boolean isRunnable() {
        return factory != null;
    }

    public IProcessingApp create() {
        if (factory == null) {
            throw new IllegalStateException(
                    "Simulation '" + id + "' does not provide a factory"
            );
        }
        return factory.get();
    }
}
