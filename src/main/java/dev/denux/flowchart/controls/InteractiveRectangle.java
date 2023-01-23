package dev.denux.flowchart.controls;

import dev.denux.flowchart.util.Constants;
import javafx.scene.Cursor;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.annotation.Nonnull;

/**
 * A class that creates a {@link Rectangle} that is draggable and resizable.
 */
public class InteractiveRectangle extends Rectangle {

    /**
     * Describes how "big" the area around the edge is that can be used to resize the rectangle.
     */
    private final int MARGIN = 5;
    /**
     * The minimum width of the rectangle.
     */
    private static final double MIN_W = 30;
    /**
     * The minimum height of the rectangle.
     */
    private static final double MIN_H = 20;

    /**
     * Coordinates of the rectangle, the mouse and the width and height of the rectangle.
     */
    private double clickX, clickY, x, y, width, height;

    /**
     * The {@link Pane} that contains the rectangle.
     */
    private final Pane pane;

    /**
     * The {@link State} the rectangle currently holds.
     */
    private State state = State.DEFAULT;

    /**
     * Create a new interactive rectangle!
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param pane The {@link Pane} that contains the rectangle.
     */
    private InteractiveRectangle(double width, double height, @Nonnull Pane pane) {
        this(0, 0, width, height, pane);
    }

    /**
     * Create a new interactive rectangle!
     * @param x The x coordinate of the rectangle.
     * @param y The y coordinate of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param pane The {@link Pane} that contains the rectangle.
     */
    private InteractiveRectangle(double x, double y, double width, double height, @Nonnull Pane pane) {
        super(x, y, width, height);
        this.pane = pane;
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Constants.MUTED_WHITE);
        this.setStrokeWidth(1);
        setListeners();
        pane.getChildren().add(this);
    }

    /**
     * Create a new interactive rectangle and adds it to the given {@link Pane}.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param pane The {@link Pane} that contains the rectangle.
     */
    public static void create(double width, double height, @Nonnull Pane pane) {
        new InteractiveRectangle(width, height, pane);
    }

    /**
     * Create a new interactive rectangle and adds it to the given {@link Pane}.
     * @param x The x coordinate of the rectangle.
     * @param y The y coordinate of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param pane The {@link Pane} that contains the rectangle.
     */
    public static void create(double x, double y, double width, double height, @Nonnull Pane pane) {
        new InteractiveRectangle(x, y, width, height, pane);
    }

    /**
     * Sets all then necessary listeners that makes the rectangle interactive.
     */
    private void setListeners() {
        this.setOnKeyPressed(this::keyPressed);
        this.setOnMousePressed(this::mousePressed);
        this.setOnMouseDragged(this::mouseDragged);
        this.setOnMouseMoved(this::mouseMoved);
        this.setOnMouseEntered(this::mouseEntered);
        this.setOnMouseExited(this::mouseExited);
        this.setOnMouseReleased(this::mouseReleased);
    }

    /**
     * A method that is fired when the rectangle is being dragged or resized.
     * @param x The new x coordinate.
     * @param y The new y coordinate.
     * @param height The new height.
     * @param width The new width.
     */
    private void onDragOrResize(double x, double y, double height, double width) {
        setNodeSize(x, y, height, width);
    }

    /**
     * Updates the position and size of the rectangle.
     * @param x The new x coordinate.
     * @param y The new y coordinate.
     * @param height The new height.
     * @param width The new width.
     */
    private void setNodeSize(double x, double y, double height, double width) {
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setWidth(width);
        this.setHeight(height);
    }

    /**
     * A state that describes what currently happens to the rectangle.
     */
    private enum State {
        DEFAULT,
        DRAG,
        NW_RESIZE,
        SW_RESIZE,
        NE_RESIZE,
        SE_RESIZE,
        E_RESIZE,
        W_RESIZE,
        N_RESIZE,
        S_RESIZE
    }

    /**
     * A method that gets you the current state of the rectangle.
     * @param event The {@link MouseEvent} instance.
     * @return the current {@link State}.
     */
    @Nonnull
    private State currentSate(@Nonnull MouseEvent event) {
        State state = State.DEFAULT;
        boolean left = isLeftResizeZone(event);
        boolean right = isRightResizeZone(event);
        boolean top = isTopResizeZone(event);
        boolean bottom = isBottomResizeZone(event);

        if (left && top) {
            state = State.NW_RESIZE;
        } else if (left && bottom) {
            state = State.SW_RESIZE;
        } else if (right && top) {
            state = State.NE_RESIZE;
        } else if (right && bottom) {
            state = State.SE_RESIZE;
        } else if (right) {
            state = State.E_RESIZE;
        } else if (left) {
            state = State.W_RESIZE;
        } else if (top) {
            state = State.N_RESIZE;
        } else if (bottom) {
            state = State.S_RESIZE;
        } else if (isInDragZone(event)) {
            state = State.DRAG;
        }
        return state;
    }

    /**
     * A method that gets you the current {@link Cursor} for the current {@link State}.
     * @param state The {@link State}.
     * @return the current {@link Cursor}.
     */
    @Nonnull
    private Cursor getCursorForState(@Nonnull State state) {
        return switch (state) {
            case NW_RESIZE -> Cursor.NW_RESIZE;
            case SW_RESIZE -> Cursor.SW_RESIZE;
            case NE_RESIZE -> Cursor.NE_RESIZE;
            case SE_RESIZE -> Cursor.SE_RESIZE;
            case E_RESIZE -> Cursor.E_RESIZE;
            case W_RESIZE -> Cursor.W_RESIZE;
            case N_RESIZE -> Cursor.N_RESIZE;
            case S_RESIZE -> Cursor.S_RESIZE;
            default -> Cursor.DEFAULT;
        };
    }

    /**
     * A method that deletes the rectangle if the delete key is pressed on the node.
     * @param event The {@link KeyEvent} instance.
     */
    private void keyPressed(@Nonnull KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            pane.getChildren().remove(this);
            pane.requestFocus();
            event.consume();
        }
    }

    /**
     * A method that is fired when the mouse has entered the rectangle.
     * @param event The {@link MouseEvent} instance.
     */
    private void mouseEntered(@Nonnull MouseEvent event) {
        this.setEffect(new Glow(5D));
    }

    /**
     * A method that is fired when the mouse has exited the rectangle.
     * @param event The {@link MouseEvent} instance.
     */
    private void mouseExited(@Nonnull MouseEvent event) {
        System.out.println("Mouse exited");
        this.setEffect(null);
    }

    /**
     * A function that is being executed when the mouse is being pressed on the object.
     * @param event The {@link MouseEvent} instance.
     */
    private void mousePressed(@Nonnull MouseEvent event) {
        this.requestFocus();
        if (isInResizeZone(event)) {
            setNewInitialEventCoordinates(event);
            state = currentSate(event);
        } else if (isInDragZone(event)) {
            setNewInitialEventCoordinates(event);
            state = State.DRAG;
        } else {
            state = State.DEFAULT;
        }
        event.consume();
    }

    /**
     * A function that is being executed when the mouse is being dragged on or from the object.
     * @param event The {@link MouseEvent} instance.
     */
    private void mouseDragged(@Nonnull MouseEvent event) {
        double mouseX = getParentX(event.getX() - this.getX());
        double mouseY = getParentY(event.getY() - this.getY());
        if (state == State.DRAG) {
            onDragOrResize(mouseX - clickX, mouseY - clickY, height, width);
        } else if (state != State.DEFAULT) {
            //resizing
            double newX = x;
            double newY = y;
            double newH = height;
            double newW = width;

            // Right Resize
            if (state == State.E_RESIZE || state == State.NE_RESIZE || state == State.SE_RESIZE) {
                newW = mouseX - x;
            }
            // Left Resize
            if (state == State.W_RESIZE || state == State.NW_RESIZE || state == State.SW_RESIZE) {
                newX = mouseX;
                newW = width + x - newX;
            }

            // Bottom Resize
            if (state == State.S_RESIZE || state == State.SE_RESIZE || state == State.SW_RESIZE) {
                newH = mouseY - y;
            }
            // Top Resize
            if (state == State.N_RESIZE || state == State.NW_RESIZE || state == State.NE_RESIZE) {
                newY = mouseY;
                newH = height + y - newY;
            }

            //min valid rect Size Check
            if (newW < MIN_W) {
                if (state == State.W_RESIZE || state == State.NW_RESIZE || state == State.SW_RESIZE)
                    newX = newX - MIN_W + newW;
                newW = MIN_W;
            }

            if (newH < MIN_H) {
                if (state == State.N_RESIZE || state == State.NW_RESIZE || state == State.NE_RESIZE)
                    newY = newY + newH - MIN_H;
                newH = MIN_H;
            }

            onDragOrResize(newX, newY, newH, newW);
            event.consume();
        }
    }

    /**
     * A function that is being executed when the mouse is being moved in any relation to the object.
     * @param event The {@link MouseEvent} instance.
     */
    private void mouseMoved(@Nonnull MouseEvent event) {
        State state = currentSate(event);
        Cursor cursor = getCursorForState(state);
        this.setCursor(cursor);
        event.consume();
    }

    /**
     * A function that is being executed when the mouse is being released and another {@link MouseEvent} was fired
     * before.
     * @param event The {@link MouseEvent} instance.
     */
    private void mouseReleased(@Nonnull MouseEvent event) {
        this.setCursor(Cursor.DEFAULT);
        state = State.DEFAULT;
        event.consume();
    }

    /**
     * Updates the event coordinates.
     * @param event The {@link MouseEvent} instance.
     */
    private void setNewInitialEventCoordinates(@Nonnull MouseEvent event) {
        x = getNodeX();
        y = getNodeY();
        height = getNodeH();
        width = getNodeW();
        clickX = event.getX() - this.getX();
        clickY = event.getY() - this.getY();
    }

    /**
     * A method that determines if the mouse is inside the resize zone.
     * @param event The {@link MouseEvent} instance.
     * @return true if the mouse is inside it, otherwise false.
     */
    private boolean isInResizeZone(@Nonnull MouseEvent event) {
        return isLeftResizeZone(event) || isRightResizeZone(event)
                || isBottomResizeZone(event) || isTopResizeZone(event);
    }

    /**
     * A method that determines if the mouse is inside the draggable zone.
     * @param event The {@link MouseEvent} instance.
     * @return true if the mouse is inside it, otherwise false.
     */
    private boolean isInDragZone(@Nonnull MouseEvent event) {
        double xPos = getParentX(event.getX() - this.getX());
        double yPos = getParentY(event.getY() - this.getY());
        double nodeX = getNodeX() + MARGIN;
        double nodeY = getNodeY() + MARGIN;
        double nodeX0 = getNodeX() + getNodeW() - MARGIN;
        double nodeY0 = getNodeY() + getNodeH() - MARGIN;

        return (xPos > nodeX && xPos < nodeX0) && (yPos > nodeY && yPos < nodeY0);
    }

    /**
     * @see InteractiveRectangle#isInResizeZone(MouseEvent)
     */
    private boolean isLeftResizeZone(@Nonnull MouseEvent event) {
        return intersect(0, event.getX() - this.getX());
    }

    /**
     * @see InteractiveRectangle#isInResizeZone(MouseEvent)
     */
    private boolean isRightResizeZone(@Nonnull MouseEvent event) {
        return intersect(getNodeW(), event.getX() - this.getX());
    }

    /**
     * @see InteractiveRectangle#isInResizeZone(MouseEvent)
     */
    private boolean isTopResizeZone(@Nonnull MouseEvent event) {
        return intersect(0, event.getY() - this.getY());
    }

    /**
     * @see InteractiveRectangle#isInResizeZone(MouseEvent)
     */
    private boolean isBottomResizeZone(@Nonnull MouseEvent event) {
        return intersect(getNodeH(), event.getY() - this.getY());
    }

    /**
     * @see InteractiveRectangle#isInResizeZone(MouseEvent)
     */
    private boolean intersect(double side, double point) {
        return side + MARGIN > point && side - MARGIN < point;
    }

    /**
     * Gets you the current x-coordinate inside the parents-bound.
     * @return the coordinate.
     */
    private double getNodeX() {
        return this.getBoundsInParent().getMinX() - this.getX();
    }

    /**
     * Gets you the current y-coordinate inside the parents-bound.
     * @return the coordinate.
     */
    private double getNodeY() {
        return this.getBoundsInParent().getMinY() - this.getY();
    }

    /**
     * Gets you the current width inside the parents-bound.
     * @return the width.
     */
    private double getNodeW() {
        return this.getBoundsInParent().getWidth();
    }

    /**
     * Gets you the current height inside the parents-bound.
     * @return the height.
     */
    private double getNodeH() {
        return this.getBoundsInParent().getHeight();
    }

    /**
     * Gets you parted x-coordinate.
     * @return the coordinate.
     */
    private double getParentX(double localX) {
        return getNodeX() + localX;
    }

    /**
     * Gets you parted y-coordinate.
     * @return the coordinate.
     */
    private double getParentY(double localY) {
        return getNodeY() + localY;
    }
}