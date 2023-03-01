package dev.denux.flowcharts.controls;

import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

//TODO java docs
public class GlowingCircle extends Circle {

	public GlowingCircle(double centerX, double centerY, double radius) {
		super(centerX, centerY, radius);
		this.setEffect(new Glow(2D));
		this.setFill(Color.TRANSPARENT);
		this.setStroke(Color.GREENYELLOW);
	}
}
