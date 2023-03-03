package dev.denux.flowcharts.controls;

import dev.denux.flowcharts.util.Constants;
import javafx.scene.Group;
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

	private final Group group;

	public GlowingCircle(double centerX, double centerY, double radius, Group group) {
		super(centerX, centerY, radius);
		this.setEffect(new Glow(2D));
		this.setFill(Constants.GREY);
		this.setStroke(Color.GREENYELLOW);
		this.group = group;
		addEventHandlers();
	}

	private void addEventHandlers() {
		this.setOnMouseDragged(this::onMouseDragged);
		this.setOnMouseEntered(this::onMouseEntered);
		this.setOnMouseExited(this::onMouseExited);
	}

	private void onMouseEntered(@Nonnull MouseEvent event) {
		this.requestFocus();
		event.consume();
	}

	private void onMouseExited(@Nonnull MouseEvent event) {
		this.setFocused(false);
		event.consume();
	}

	private void onMouseDragged(@Nonnull MouseEvent event) {
		log.info("Mouse dragged");
		Arrow arrow = new Arrow(this.getCenterX(), this.getCenterY(), event.getX(), event.getY());
		group.getChildren().add(arrow);
		event.consume();
	}
}
