package dev.denux.shape;

import dev.denux.util.Constants;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.annotation.Nonnull;

public class MovableRectangle extends Rectangle {

    double orgSceneX;
    double orgSceneY;

    public MovableRectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.setOnMousePressed(this::onMousePressed);
        this.setOnMouseDragged(this::onMouseDragged);
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Constants.MUTED_WHITE);
        this.setStrokeWidth(1);
    }

    private void onMousePressed(@Nonnull MouseEvent event) {
        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();

        Rectangle rectangle = (Rectangle) (event.getSource());
        rectangle.toFront();
    }

    private void onMouseDragged(@Nonnull MouseEvent event) {
        double offsetX = event.getSceneX() - orgSceneX;
        double offsetY = event.getSceneY() - orgSceneY;

        Rectangle c = (Rectangle) (event.getSource());

        c.setX(c.getX() + offsetX);
        c.setY(c.getY() + offsetY);

        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();
    }
}
