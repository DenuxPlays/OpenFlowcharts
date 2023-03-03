package dev.denux.flowcharts.controls;

import dev.denux.flowcharts.util.Constants;
import javafx.scene.shape.Line;

public class Arrow extends Line {

	public Arrow(double startX, double startY, double endX, double endY) {
		super(startX, startY, endX, endY);
		this.setStroke(Constants.MUTED_WHITE);
	}
}
