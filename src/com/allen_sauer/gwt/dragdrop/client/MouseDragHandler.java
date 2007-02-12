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

  private AbsolutePanel boundryPanel;
  private transient Widget capturingWidget;
  private DragController dragController;
  private transient Widget draggableProxy;
  private boolean dragging;
  private transient DropController dropController;
  private transient Location initialDraggableBoundryPanelLocation;
  private transient Widget initialDraggableParent;
  private transient Location initialDraggableParentLocation;
  private int initialMouseX;
  private int initialMouseY;

  public MouseDragHandler(DragController dragController) {
    this.dragController = dragController;
    boundryPanel = dragController.getBoundryPanel();
  }

  public void onMouseDown(Widget sender, int x, int y) {
    capturingWidget = sender;
    initialMouseX = x;
    initialMouseY = y;

    if (!dragController.isDragAllowed(capturingWidget)) {
      return;
    }
    dragController.drag(capturingWidget);

    draggableProxy = dragController.getDraggableProxy();

    // Store initial draggable parent and coordinates in case we have to abort
    initialDraggableParent = capturingWidget.getParent();
    initialDraggableParentLocation = new Location(capturingWidget, initialDraggableParent);
    initialDraggableBoundryPanelLocation = new Location(capturingWidget, boundryPanel);
    boundryPanel.add(draggableProxy, initialDraggableBoundryPanelLocation.getLeft(), initialDraggableBoundryPanelLocation.getTop());

    DOM.setCapture(capturingWidget.getElement());
    dragging = true;
    try {
      move(x, y);
    } catch (RuntimeException ex) {
      dragging = false;
      DOM.releaseCapture(capturingWidget.getElement());
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
      move(x, y);
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
      DOM.releaseCapture(capturingWidget.getElement());

      move(x, y);
      dragging = false;

      // Determine the interested DropController at our present location
      DropController newDropController = DropControllerCollection.getIntersectDropController(draggableProxy, boundryPanel);

      // Is the DropController at this location different than the last one?
      if (dropController != newDropController) {
        if (dropController != null) {
          dropController.onLeave(capturingWidget, dragController);
        }
        dropController = newDropController;
      }

      // Is there a DropController willing to handle our request?
      if (dropController == null) {
        cancelDrag();
        return;
      }

      // Does the DragController allow the drop?
      if (!dragController.isDropAllowed(capturingWidget, dropController.getDropTarget())) {
        cancelDrag();
        return;
      }

      // Does the DropController allow the drop?
      if (!dropController.onDrop(draggableProxy, capturingWidget, dragController)) {
        // Notify DragController
        dragController.dropCanceled(capturingWidget, dropController.getDropTarget());
        cancelDrag();
        return;
      }

      // Notify DragController
      dragController.drop(capturingWidget, dropController.getDropTarget());

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
    DOM.releaseCapture(capturingWidget.getElement());

    if (dropController != null) {
      dropController.onLeave(capturingWidget, dragController);
    }

    dragging = false;
    moveDraggableToOriginalLocation();

    dropController = null;
  }

  private void move(int x, int y) {
    Location senderLocation = new Location(capturingWidget, boundryPanel);

    int desiredLeft = (x - initialMouseX) + senderLocation.getLeft();
    int desiredTop = (y - initialMouseY) + senderLocation.getTop();

    boundryPanel.setWidgetPosition(draggableProxy, desiredLeft, desiredTop);

    DropController newDropController = DropControllerCollection.getIntersectDropController(draggableProxy, boundryPanel);
    if (dropController == newDropController) {
      if (dropController != null) {
        dropController.onMove(draggableProxy, capturingWidget, dragController);
      }
    } else {
      if (dropController != null) {
        dropController.onLeave(capturingWidget, dragController);
      }
      dropController = newDropController;
      if (dropController != null) {
        dropController.onEnter(capturingWidget, dragController);
        dropController.onMove(draggableProxy, capturingWidget, dragController);
      }
    }
  }

  private void moveDraggableToOriginalLocation() {
    if (initialDraggableParent instanceof AbsolutePanel) {
      //      Location parentLocation = new Location(initialDraggableParent, boundryPanel);
      ((AbsolutePanel) initialDraggableParent).add(capturingWidget, initialDraggableParentLocation.getLeft(),
          initialDraggableParentLocation.getTop());
    } else {
      // TODO instead try to add to original parent panel in a different way
      boundryPanel.add(capturingWidget, initialDraggableBoundryPanelLocation.getLeft(),
          initialDraggableBoundryPanelLocation.getTop());
    }
  }
}
