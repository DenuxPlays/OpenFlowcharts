package dev.denux;

import dev.denux.config.DrawerConfig;
import dev.denux.shape.MovableRectangle;
import dev.denux.util.Constants;
import dev.denux.util.LoggerSetup;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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

        Group group = new Group();
        Scene scene = new Scene(group);
        scene.setFill(Constants.GREY);

        MovableRectangle rectangle = new MovableRectangle(50, 50, 40, 25);

        group.getChildren().add(rectangle);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
