package com.allen_sauer.gwt.dragdrop.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

/**
 * Helper class to deal with draggable widget mouse events.
 */
class MouseDragHandler implements MouseListener {

  private DropController dropController;
  private boolean dragging;
  private Location initialDraggableLocation;
  private Widget initialDraggableParent;
  private int initialMouseX;
  private int initialMouseY;
  private Widget draggable;
  private DragController dragController;
  private AbsolutePanel boundryPanel;

  public MouseDragHandler(Widget draggable, DragController dragController) {
    this.draggable = draggable;
    this.dragController = dragController;
    boundryPanel = dragController.getBoundryPanel();
  }

  public void onMouseDown(Widget sender, int x, int y) {
    initialMouseX = x;
    initialMouseY = y;

    if (!dragController.isDragAllowed(draggable)) {
      return;
    }
    dragController.drag(draggable);

    // Store initial draggable parent and coordinates in case we have to abort
    initialDraggableParent = draggable.getParent();
    initialDraggableLocation = new Location(draggable, boundryPanel);

    boundryPanel.add(draggable, initialDraggableLocation.getLeft(), initialDraggableLocation.getTop());

    DOM.setCapture(sender.getElement());
    dragging = true;
    try {
      move(sender, x, y);
    } catch (RuntimeException ex) {
      dragging = false;
      DOM.releaseCapture(sender.getElement());
      throw ex;
    }
  }

  public void onMouseEnter(Widget sender) {
  }

  public void onMouseLeave(Widget sender) {
  }

  public void onMouseMove(Widget sender, int x, int y) {
    if (!dragging) {
      return;
    }
    try {
      move(sender, x, y);
    } catch (RuntimeException ex) {
      cancelDrag();
      throw ex;
    }
  }

  public void onMouseUp(Widget sender, int x, int y) {
    if (!dragging) {
      return;
    }
    try {
      DOM.releaseCapture(sender.getElement());

      move(sender, x, y);
      dragging = false;

      // Determine the interested DropController at our present location
      DropController newDropController = DropControllerCollection.getIntersectDropController(sender, boundryPanel);

      // Is the DropController at this location different than the last one?
      if (dropController != newDropController) {
        if (dropController != null) {
          dropController.onLeave(draggable, dragController);
        }
        dropController = newDropController;
      }

      // Is there a DropController willing to handle our request?
      if (dropController == null) {
        cancelDrag();
        return;
      }

      // Does the DragController allow the drop?
      if (!dragController.isDropAllowed(draggable, dropController.getDropTarget())) {
        cancelDrag();
        return;
      }

      // Does the DropController allow the drop?
      if (!dropController.onDrop(draggable, dragController)) {
        cancelDrag();
        return;
      }

      // Notify DragController that drop occurred
      dragController.drop(draggable, dropController.getDropTarget());

    } catch (RuntimeException ex) {
      // cleanup in case anything goes wrong
      cancelDrag();
      throw ex;
    } finally {
      dropController = null;
    }
  }

  private void cancelDrag() {
    // Do this first so it always happens
    DOM.releaseCapture(draggable.getElement());

    if (dropController != null) {
      dropController.onLeave(draggable, dragController);
    }

    dragging = false;
    if (initialDraggableParent instanceof AbsolutePanel) {
      Location parentLocation = new Location(initialDraggableParent, boundryPanel);
      ((AbsolutePanel) initialDraggableParent).add(draggable, initialDraggableLocation.getLeft() - parentLocation.getLeft(),
          initialDraggableLocation.getTop() - parentLocation.getTop());
    } else {
      boundryPanel.add(draggable, initialDraggableLocation.getLeft(), initialDraggableLocation.getTop());
    }
    dropController = null;
  }

  private void move(Widget sender, int x, int y) {
    Location senderLocation = new Location(draggable, boundryPanel);

    int desiredLeft = (x - initialMouseX) + senderLocation.getLeft();
    int desiredTop = (y - initialMouseY) + senderLocation.getTop();

    boundryPanel.setWidgetPosition(draggable, desiredLeft, desiredTop);

    DropController newDropController = DropControllerCollection.getIntersectDropController(draggable, boundryPanel);
    if (dropController == newDropController) {
      if (dropController != null) {
        dropController.onMove(draggable, dragController);
      }
    } else {
      if (dropController != null) {
        dropController.onLeave(draggable, dragController);
      }
      dropController = newDropController;
      if (dropController != null) {
        dropController.onEnter(draggable, dragController);
        dropController.onMove(draggable, dragController);
      }
    }
  }
}
