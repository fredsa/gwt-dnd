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
public class MouseHandler implements MouseListener {

  private DragContext dragContext;
  private DropController dropController;
  private boolean inDrag;
  private Location initialDraggableLocation;
  private Widget initialDraggableParent;
  private int initialMouseX;
  private int initialMouseY;
  private Widget draggableWidget;
  private DragController dragController;

  public MouseHandler(Widget draggableWidget, DragController dragController) {
    this.draggableWidget = draggableWidget;
    this.dragController = dragController;
  }

  public void onMouseDown(Widget sender, int x, int y) {
    this.initialMouseX = x;
    this.initialMouseY = y;
    this.dragContext = new DragContext(draggableWidget, dragController);

    Widget draggable = this.draggableWidget;
    if (this.dragController.getDragAndDropListeners().firePreventDragStart(
        draggable)) {
      return;
    }
    this.dragController.getDragAndDropListeners().fireDragStart(draggable);
    draggable.addStyleName("dragdrop-dragging");

    // Store initial draggable parent and coordinates in case we have to abort
    this.initialDraggableParent = draggable.getParent();
    this.initialDraggableLocation = new Location(draggable,
        this.dragController.getBoundryPanel());
    this.dragController.getBoundryPanel().add(draggable,
        this.initialDraggableLocation.getLeft(),
        this.initialDraggableLocation.getTop());

    DOM.setCapture(sender.getElement());
    this.inDrag = true;
    try {
      move(sender, x, y);
    } catch (RuntimeException ex) {
      this.inDrag = false;
      DOM.releaseCapture(sender.getElement());
      throw ex;
    }
  }

  public void onMouseEnter(Widget sender) {
  }

  public void onMouseLeave(Widget sender) {
  }

  public void onMouseMove(Widget sender, int x, int y) {
    if (!this.inDrag) {
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
    if (!this.inDrag) {
      return;
    }
    try {
      DOM.releaseCapture(sender.getElement());

      move(sender, x, y);
      this.inDrag = false;
      this.draggableWidget.removeStyleName("dragdrop-dragging");

      // Determine the interested controller at our present location
      DropController newDropController = DropControllerCollection.singleton().getIntersectDropController(
          sender, this.dragController.getBoundryPanel());

      // Is the controller at this location different than the last one?
      if (this.dropController != newDropController) {
        if (this.dropController != null) {
          this.dropController.onLeave(this.dragContext);
        }
        this.dropController = newDropController;
      }

      // Is there a controller willing to handle our request?
      if (this.dropController == null) {
        cancelDrag();
        return;
      }

      // Does anyone wish to veto this request?
      if (this.dragController.getDragAndDropListeners().firePreventDrop(
          this.draggableWidget, this.dropController.getDropTargetPanel())) {
        cancelDrag();
        return;
      }

      // Does the controller allow the drop?
      if (!this.dropController.onDrop(this.dragContext)) {
        cancelDrag();
        return;
      }

      // Notify listeners that drop occurred
      this.dragController.getDragAndDropListeners().fireDrop(
          this.draggableWidget, this.dropController.getDropTargetPanel());

    } catch (RuntimeException ex) {
      // cleanup in case anything goes wrong
      cancelDrag();
      throw ex;
    } finally {
      this.dropController = null;
    }
  }

  private void cancelDrag() {
    // Do this first so it always happens
    DOM.releaseCapture(this.draggableWidget.getElement());

    if (this.dropController != null) {
      this.dropController.onLeave(this.dragContext);
    }

    this.inDrag = false;
    if (this.initialDraggableParent instanceof AbsolutePanel) {
      Location parentLocation = new Location(this.initialDraggableParent,
          this.dragController.getBoundryPanel());
      ((AbsolutePanel) this.initialDraggableParent).add(this.draggableWidget,
          this.initialDraggableLocation.getLeft() - parentLocation.getLeft(),
          this.initialDraggableLocation.getTop() - parentLocation.getTop());
    } else {
      this.dragController.getBoundryPanel().add(this.draggableWidget,
          this.initialDraggableLocation.getLeft(),
          this.initialDraggableLocation.getTop());
    }
    this.dropController = null;
  }

  private void move(Widget sender, int x, int y) {
    Widget draggable = this.draggableWidget;
    Location senderLocation = new Location(draggable,
        this.dragController.getBoundryPanel());

    int desiredLeft = (x - this.initialMouseX) + senderLocation.getLeft();
    int desiredTop = (y - this.initialMouseY) + senderLocation.getTop();

    this.dragController.getBoundryPanel().setWidgetPosition(draggable,
        desiredLeft, desiredTop);

    DropController newDropController = DropControllerCollection.singleton().getIntersectDropController(
        draggable, this.dragController.getBoundryPanel());
    if (this.dropController == newDropController) {
      if (this.dropController != null) {
        this.dropController.onMove(this.dragContext);
      }
    } else {
      if (this.dropController != null) {
        this.dropController.onLeave(this.dragContext);
      }
      this.dropController = newDropController;
      if (this.dropController != null) {
        this.dropController.onEnter(this.dragContext);
        this.dropController.onMove(this.dragContext);
      }
    }
  }
}
