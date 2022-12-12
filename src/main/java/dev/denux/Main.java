package dev.denux;

import dev.denux.utils.LoggerSetup;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.annotation.Nonnull;

/**
 * The main class and the starting point of this application.
 */
public class Main extends Application {

    /**
     * Prepares and then starts the application.
     * @param args The given arguments.
     */
    public static void main(String[] args) {
        LoggerSetup.configureLogger();
        launch(args);
    }

    @Override
    public void start(@Nonnull Stage primaryStage) {
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, Color.GRAY);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
