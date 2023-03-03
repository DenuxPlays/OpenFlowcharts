package dev.denux.flowcharts.controls;

import dev.denux.flowcharts.util.Constants;
import javafx.geometry.Point2D;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

//TODO java docs
@Slf4j
public class GlowingCircle extends Circle {

	private final Pane pane;

	protected Arrow arrow = null;

	public GlowingCircle(double centerX, double centerY, double radius, @Nonnull Pane pane) {
		super(centerX, centerY, radius);
		this.setEffect(new Glow(2D));
		this.setFill(Constants.GREY);
		this.setStroke(Color.GREENYELLOW);
		this.pane = pane;
		setEventListeners();
		toFront();
	}

	private void setEventListeners() {
		this.setOnMouseDragged(this::onMouseDragged);
		this.setOnMouseEntered(this::onMouseEntered);
		this.setOnMouseExited(this::onMouseExited);
	}

	/**
	 * Updates the coordinates of the given circle.
	 * @param point The {@link Point2D} that contains the new coordinates.
	 */
	public void update(@Nonnull Point2D point) {
		this.setCenterX(point.getX());
		this.setCenterY(point.getY());
		updateArrowStart(point);
		toFront();
	}


	public void updateArrowStart(@Nonnull Point2D point) {
		if (arrow == null) return;
		arrow.setStartX(point.getX());
		arrow.setStartY(point.getY());
		arrow.updateArrowHead();
		arrow.toBack();
	}

	private void onMouseEntered(@Nonnull MouseEvent event) {
		this.requestFocus();
		this.setVisible(true);
		event.consume();
	}

	private void onMouseExited(@Nonnull MouseEvent event) {
		this.setFocused(false);
		this.setVisible(false);
		event.consume();
	}

	private void onMouseDragged(@Nonnull MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			if (arrow == null) {
				arrow = new Arrow(this.getCenterX(), this.getCenterY(), event.getX(), event.getY(), pane);
				arrow.setDeleteCallback(() -> arrow = null);
				arrow.addArrowHead();
				pane.getChildren().addAll(arrow);
			} else {
				arrow.setEndX(event.getX());
				arrow.setEndY(event.getY());
			}
		}
		arrow.updateArrowHead();
		arrow.toBack();
		event.consume();
	}
}
