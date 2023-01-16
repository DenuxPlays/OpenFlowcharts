package dev.denux.drawer;

import dev.denux.drawer.config.DrawerConfig;
import dev.denux.drawer.shape.InteractiveRectangle;
import dev.denux.drawer.util.Constants;
import dev.denux.drawer.util.LoggerSetup;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

        BorderPane root = new BorderPane();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.BASELINE_LEFT);

        Rectangle rectangle = new InteractiveRectangle(50, 50);
        root.getChildren().addAll(rectangle, vBox);

        Scene scene = new Scene(root);
        scene.setFill(Constants.GREY);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
