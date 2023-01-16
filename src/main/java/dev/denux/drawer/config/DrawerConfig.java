package dev.denux.drawer.config;

import dev.denux.drawer.util.LoggerSetup;
import lombok.Getter;

/**
 * Basic config classes.
 */
@Getter
public class DrawerConfig {

    private boolean debugMode = false;

    /**
     * Updates the debug mode.
     * @param debugMode True if you want to enable it, otherwise false.
     */
    public void setDebugMode(boolean debugMode) {
        LoggerSetup.setDebugLoggerMode(debugMode);
        this.debugMode = debugMode;
    }
}
