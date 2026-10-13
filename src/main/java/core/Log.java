package core;

import java.lang.System.Logger;

/**
 * Small factory for loggers.
 *
 * <p>Centralising logger creation keeps the log channel names consistent and
 * makes it trivial to later swap the JDK logging backend for another one
 * (e.g. SLF4J/Logback) without touching every class.
 */
public final class Log {

    private Log() {
    }

    public static Logger get(Class<?> type) {
        return System.getLogger(type.getName());
    }
}
