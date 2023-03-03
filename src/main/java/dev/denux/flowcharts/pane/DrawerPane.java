package dev.denux.flowcharts.pane;

import dev.denux.flowcharts.controls.InteractiveRectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.annotation.Nonnull;

/**
 * The main pane of the application.
 */
public class DrawerPane extends Pane {

	/**
	 * The current x coordinate of the mouse on the scene.
	 */
	private double mouseX = 0.0D;
	/**
	 * The current y coordinate of the mouse on the scene.
	 */
	private double mouseY = 0.0D;

	/**
	 * Creates a new instance of {@link DrawerPane}.
	 */
	public DrawerPane() {
		super();
		setListeners();
		this.setManaged(false);
	}

	/**
	 * Sets the listeners of this pane.
	 */
	private void setListeners() {
		this.setOnKeyPressed(this::onKeyPressed);
		this.setOnMouseMoved(this::onMouseMoved);
		this.setOnMousePressed(this::onMousePressed);
	}

	/**
	 * Called when a key is pressed. <br>
	 * If the key is {@link KeyCode#INSERT}, then a new {@link InteractiveRectangle} is created.
	 * @param event The {@link KeyEvent}.
	 */
	private void onKeyPressed(@Nonnull KeyEvent event) {
		if (event.getCode().equals(KeyCode.INSERT)) {
			InteractiveRectangle.create(mouseX - 25, mouseY - 25, 50, 50, this);
		}
		event.consume();
	}

	/**
	 * Called when the mouse is moved. <br>
	 * Keeps track of the current mouse coordinates.
	 * @param event The {@link MouseEvent}.
	 */
	private void onMouseMoved(@Nonnull MouseEvent event) {
		mouseX = event.getSceneX();
		mouseY = event.getSceneY();
		event.consume();
	}

	/**
	 * Used to the focus back to the pane when the mouse is pressed.
	 * @param event The {@link MouseEvent}.
	 */
	private void onMousePressed(@Nonnull MouseEvent event) {
		requestFocus();
		event.consume();
	}
}
