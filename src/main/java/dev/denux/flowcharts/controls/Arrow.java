package dev.denux.flowcharts.controls;

import dev.denux.flowcharts.util.Constants;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Line;

import javax.annotation.Nonnull;

/**
 * An arrow that can be drawn anywhere on the screen.
 */
public class Arrow extends Line {

	/**
	 * The callback that will be called when the user presses the delete key.
	 */
	private Runnable deleteCallback = () -> {};

	/**
	 * The group that contains the arrow.
	 */
	private final Group group;

	/**
	 * The two lines that make up the arrow head.
	 */
	private final Line arrow1, arrow2;

	/**
	 * Creates a new arrow.
	 * @param startX The x coordinate of the start point.
	 * @param startY The y coordinate of the start point.
	 * @param endX The x coordinate of the end point.
	 * @param endY The y coordinate of the end point.
	 * @param group The group that contains the arrow.
	 */
	public Arrow(double startX, double startY, double endX, double endY, @Nonnull Group group) {
		super(startX, startY, endX, endY);
		this.group = group;
		this.setStroke(Constants.MUTED_WHITE);
		arrow1 = new Line();
		arrow2 = new Line();
		arrow1.setStroke(Constants.MUTED_WHITE);
		arrow2.setStroke(Constants.MUTED_WHITE);
		updateArrowHead();
		setEventHandlers();
		toBack();
	}

	/**
	 * Sets the {@link Runnable} that will be called when the user presses the delete key.
	 * @param deleteCallback The {@link Runnable}.
	 */
	public void setDeleteCallback(@Nonnull Runnable deleteCallback) {
		this.deleteCallback = deleteCallback;
	}

	/**
	 * Sets all the necessary event handlers.
	 */
	private void setEventHandlers() {
		this.setOnMouseClicked(event -> requestFocus());
		this.setOnMouseEntered(event -> requestFocus());
		this.setOnMouseExited(event -> setFocused(false));
		this.setOnKeyPressed(this::onKeyPressed);
	}

	/**
	 * Handles the {@link KeyEvent} when the user presses a key.
	 * @param event The {@link KeyEvent} instance.
	 */
	private void onKeyPressed(@Nonnull KeyEvent event) {
		if (event.getCode().equals(KeyCode.DELETE)) {
			delete();
		}
	}

	/**
	 * Creates or updates the arrow head.
	 */
	protected void updateArrowHead() {
		double startX = this.getStartX();
		double startY = this.getStartY();
		double endX = this.getEndX();
		double endY = this.getEndY();
		double slope = (startY - endY) / (startX - endX);
		double lineAngle = Math.atan(slope);

		double arrowAngle = startX > endX ? Math.toRadians(45) : -Math.toRadians(225);

		final double arrowLength = 12D;

		// create the arrow legs
		arrow1.setStartX(this.getEndX());
		arrow1.setStartY(this.getEndY());
		arrow1.setEndX(this.getEndX() + arrowLength * Math.cos(lineAngle - arrowAngle));
		arrow1.setEndY(this.getEndY() + arrowLength * Math.sin(lineAngle - arrowAngle));

		arrow2.setStartX(this.getEndX());
		arrow2.setStartY(this.getEndY());
		arrow2.setEndX(this.getEndX() + arrowLength * Math.cos(lineAngle + arrowAngle));
		arrow2.setEndY(this.getEndY() + arrowLength * Math.sin(lineAngle + arrowAngle));
	}

	/**
	 * Adds the arrow head to the group.
	 */
	protected void addArrowHead() {
		group.getChildren().addAll(arrow1, arrow2);
	}

	/**
	 * Removes the arrow from the group.
	 */
	public void delete() {
		deleteCallback.run();
		group.getChildren().removeAll(this, arrow1, arrow2);
	}
}
