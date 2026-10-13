package ui.util;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.Optional;

/**
 * Helper for loading images from the classpath.
 *
 * <p>Image resources may legitimately be missing while a simulation is being
 * developed, so loading never throws and callers can simply show a placeholder.
 */
public final class Images {

    private Images() {
    }

    public static Optional<Image> load(Class<?> context, String resourcePath) {
        if (resourcePath == null) {
            return Optional.empty();
        }

        URL resource = context.getResource(resourcePath);

        return resource == null
                ? Optional.empty()
                : Optional.of(new Image(resource.toExternalForm()));
    }
}
