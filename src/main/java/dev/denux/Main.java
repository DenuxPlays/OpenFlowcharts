package dev.denux;

import dev.denux.config.DrawerConfig;
import dev.denux.shape.InteractiveRectangle;
import dev.denux.util.Constants;
import dev.denux.util.LoggerSetup;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
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

        Pane pane = new Pane();

        Rectangle rectangle = new InteractiveRectangle(50, 50);
        pane.getChildren().add(rectangle);

        Scene scene = new Scene(pane);
        scene.setFill(Constants.GREY);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
