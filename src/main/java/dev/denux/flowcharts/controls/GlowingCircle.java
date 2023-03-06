package dev.denux.flowcharts.controls;

import dev.denux.flowcharts.util.Constants;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

/**
 * A {@link Circle} that glows when the mouse is over it.
 * Also, you can draw an {@link Arrow} from this circle.
 */
@Slf4j
public class GlowingCircle extends Circle {

	/**
	 * The {@link Group} that contains this {@link GlowingCircle}.
	 */
	private final Group group;

	/**
	 * The {@link Arrow} that is drawn from this {@link GlowingCircle}.
	 */
	protected Arrow arrow = null;

	/**
	 * Creates a new {@link GlowingCircle} with the given parameters.
	 * @param centerX The x coordinate of the center of the circle.
	 * @param centerY The y coordinate of the center of the circle.
	 * @param radius The radius of the circle.
	 * @param group The {@link Group} that contains this {@link GlowingCircle}.
	 */
	public GlowingCircle(double centerX, double centerY, double radius, @Nonnull Group group) {
		super(centerX, centerY, radius);
		this.setEffect(new Glow(2D));
		this.setFill(Constants.GREY);
		this.setStroke(Color.GREENYELLOW);
		this.group = group;
		setEventListeners();
		group.getChildren().add(this);
		toFront();
	}

	/**
	 * Sets the neccessary event listeners for this {@link GlowingCircle}.
	 */
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

	/**
	 * Updates the start coordinates of the {@link Arrow} that is drawn from this {@link GlowingCircle}.
	 * @param point The {@link Point2D} that contains the new coordinates.
	 */
	public void updateArrowStart(@Nonnull Point2D point) {
		if (arrow == null) return;
		arrow.setStartX(point.getX());
		arrow.setStartY(point.getY());
		arrow.updateArrowHead();
		arrow.toBack();
	}

	/**
	 * The {@link MouseEvent} that will be executed if the mouse enters this {@link GlowingCircle}.
	 * @param event The {@link MouseEvent} instance.
	 */
	private void onMouseEntered(@Nonnull MouseEvent event) {
		this.requestFocus();
		this.setVisible(true);
		toFront();
		event.consume();
	}

	/**
	 * The {@link MouseEvent} that will be executed if the mouse exits this {@link GlowingCircle}.
	 * @param event The {@link MouseEvent} instance.
	 */
	private void onMouseExited(@Nonnull MouseEvent event) {
		this.setFocused(false);
		this.setVisible(false);
		toFront();
		event.consume();
	}

	/**
	 * The {@link MouseEvent} that will be executed if the mouse is dragged over this {@link GlowingCircle}.
	 * @param event The {@link MouseEvent} instance.
	 */
	private void onMouseDragged(@Nonnull MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			if (arrow == null) {
				arrow = new Arrow(this.getCenterX(), this.getCenterY(), event.getX(), event.getY(), group);
				arrow.setDeleteCallback(() -> arrow = null);
				arrow.addArrowHead();
				group.getChildren().addAll(arrow);
			} else {
				arrow.setEndX(event.getX());
				arrow.setEndY(event.getY());
			}
		}
		arrow.updateArrowHead();
		arrow.toBack();
		toFront();
		event.consume();
	}
}
