package dev.denux.flowcharts.controls;

import dev.denux.flowcharts.util.Constants;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Line;

import javax.annotation.Nonnull;

//TODO java docs
public class Arrow extends Line {

	private Runnable deleteCallback = () -> {};

	private final Group group;

	private final Line arrow1 = new Line();
	private final Line arrow2 = new Line();

	public Arrow(double startX, double startY, double endX, double endY, Group group) {
		super(startX, startY, endX, endY);
		this.group = group;
		this.setStroke(Constants.MUTED_WHITE);
		arrow1.setStroke(Constants.MUTED_WHITE);
		arrow2.setStroke(Constants.MUTED_WHITE);
		updateArrowHead();
		setEventHandlers();
		toBack();
	}

	public void setDeleteCallback(@Nonnull Runnable deleteCallback) {
		this.deleteCallback = deleteCallback;
	}

	private void setEventHandlers() {
		this.setOnMouseClicked(event -> requestFocus());
		this.setOnMouseEntered(event -> requestFocus());
		this.setOnMouseExited(event -> setFocused(false));
		this.setOnKeyPressed(this::onKeyPressed);
	}

	private void onKeyPressed(@Nonnull KeyEvent event) {
		System.out.println(event.getCode());
		if (event.getCode().equals(KeyCode.DELETE)) {
			delete();
		}
	}

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

	protected void addArrowHead() {
		group.getChildren().addAll(arrow1, arrow2);
	}

	public void delete() {
		deleteCallback.run();
		group.getChildren().removeAll(this, arrow1, arrow2);
	}
}
