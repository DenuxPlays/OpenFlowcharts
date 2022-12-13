package dev.denux.config;

import dev.denux.util.LoggerSetup;

/**
 * Basic config classes.
 */
public class DrawerConfig {

    private boolean debugMode = false;

    /**
     * Gets you the debug mode.
     * @return true if the debug mode is enabled.
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * Updates the debug mode.
     * @param debugMode True if you want to enable it, otherwise false.
     */
    public void setDebugMode(boolean debugMode) {
        LoggerSetup.setDebugLoggerMode(debugMode);
        this.debugMode = debugMode;
    }
}
