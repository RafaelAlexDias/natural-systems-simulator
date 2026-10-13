package core;

/**
 * Central application metadata (name, window title and icon location).
 *
 * <p>Both the JavaFX shell and the Processing sketches read from here so the
 * window title and icon stay in sync across every window.
 */
public final class AppInfo {

    private AppInfo() {
    }

    public static final String NAME = "Natural-Systems-Simulator";

    public static final String WINDOW_TITLE = NAME;

    public static final String ICON_RESOURCE = "/images/app-icon.png";
}
