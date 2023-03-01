package dev.denux.flowcharts;

import dev.denux.flowcharts.config.DrawerConfig;
import dev.denux.flowcharts.controls.GlowingCircle;
import dev.denux.flowcharts.controls.InteractiveRectangle;
import dev.denux.flowcharts.pane.DrawerPane;
import dev.denux.flowcharts.util.Constants;
import dev.denux.flowcharts.util.LoggerSetup;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;

import javax.annotation.Nonnull;

/**
 * The main class and the starting point of this application.
 */
public class Main extends Application {

    /**
     * The config that contains the current settings of the session.
     */
    @Getter
    private static final DrawerConfig config = new DrawerConfig();

    /**
     * Prepares and then starts the application.
     * @param args The given arguments.
     */
    public static void main(String[] args) {
        LoggerSetup.configureLogger();
        Main.launch(args);
    }

    @Override
    public void start(@Nonnull Stage primaryStage) {
        primaryStage.setTitle(Constants.APPLICATION_NAME + " - " + Constants.VERSION);

        Pane drawerPane = new DrawerPane();
        InteractiveRectangle.create(50, 50, drawerPane);

        Scene scene = new Scene(drawerPane);
        scene.setFill(Constants.GREY);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        drawerPane.requestFocus();
    }
}
