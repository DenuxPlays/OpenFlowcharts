package dev.denux.drawer.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

/**
 * Set up the logging factory that is used in the whole project.
 */
@Slf4j
public class LoggerSetup {

    /**
     * Configures the logger to give us a nice and more usable output of itself.
     */
    public static void configureLogger() {
        LoggerContext ctx = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder ple = new PatternLayoutEncoder();
        ple.setPattern("%d{dd.MM.yyyy HH:mm:ss} %boldCyan(%-34.-34thread) %boldGreen(%-15.-15logger{0}) %highlight(%-6level) %msg%n");
        ple.setContext(ctx);
        ple.start();

        ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<>();
        ca.setEncoder(ple);
        ca.setContext(ctx);
        ca.start();

        Logger logger = ctx.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(ca);
        logger.setAdditive(false);
    }

    /**
     * Sets the logger into debug or info mode.
     * @param debugMode The debug mode.
     */
    public static void setDebugLoggerMode(boolean debugMode) {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(debugMode ? Level.DEBUG : Level.INFO);
        log.info("Debug mode set to: {}", debugMode);
    }
}
