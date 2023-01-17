package dev.denux.drawer;

import dev.denux.drawer.config.DrawerConfig;
import dev.denux.drawer.shape.InteractiveRectangle;
import dev.denux.drawer.util.Constants;
import dev.denux.drawer.util.LoggerSetup;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

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

        final Pane root = new BorderPane();
        new InteractiveRectangle(50, 50, root);
        AtomicReference<Double> mouseX = new AtomicReference<>(0.0);
        AtomicReference<Double> mouseY = new AtomicReference<>(0.0);

        Scene scene = new Scene(root);
        root.setManaged(false);
        root.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.INSERT)) {
                new InteractiveRectangle(mouseX.get() - 25, mouseY.get() - 25, 50, 50, root);
            }
            event.consume();
        });
        //keeps track of the mouse position
        root.setOnMouseMoved(event -> {
            mouseX.set(event.getSceneX());
            mouseY.set(event.getSceneY());
            event.consume();
        });

        scene.setFill(Constants.GREY);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        root.requestFocus();
    }
}
