package dev.denux;

import dev.denux.utils.LoggerSetup;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.annotation.Nonnull;

public class Main extends Application {

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
