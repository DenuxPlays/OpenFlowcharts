package dev.denux.flowchart;

import dev.denux.flowchart.config.DrawerConfig;
import dev.denux.flowchart.controls.InteractiveRectangle;
import dev.denux.flowchart.pane.DrawerPane;
import dev.denux.flowchart.util.Constants;
import dev.denux.flowchart.util.LoggerSetup;
import javafx.application.Application;
import javafx.scene.Scene;
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

        final Pane root = new DrawerPane();
        InteractiveRectangle.create(50, 50, root);
        Scene scene = new Scene(root);

        scene.setFill(Constants.GREY);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        root.requestFocus();
    }
}
