/*
 * Copyright 2007 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dragdrop.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.drop.VetoDropException;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

/**
 * Helper class which handles mouse events for all
 * draggable widgets for a give {@link DropController}.
 */
public class MouseDragHandler implements MouseListener {

  private AbsolutePanel boundaryPanel;
  private Widget capturingWidget;
  private DragController dragController;
  private Widget movableWidget;
  private boolean dragging;
  private DropController dropController;
  private int initialMouseX;
  private int initialMouseY;

  public MouseDragHandler(DragController dragController) {
    this.dragController = dragController;
    boundaryPanel = dragController.getBoundaryPanel();
  }

  public void onMouseDown(Widget sender, int x, int y) {
    if (dragging) {
      // Ignore additional mouse buttons depressed while still dragging
      return;
    }
    capturingWidget = sender;
    initialMouseX = x;
    initialMouseY = y;

    try {
      dragController.previewDragStart(capturingWidget);
    } catch (VetoDragException ex) {
      return;
    }
    dragController.dragStart(capturingWidget);

    movableWidget = dragController.getMovableWidget();

    DOM.setCapture(capturingWidget.getElement());
    dragging = true;
    try {
      move(x, y);
    } catch (RuntimeException ex) {
      cancelDrag();
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

      // Is there a DropController willing to handle our request?
      if (dropController == null) {
        cancelDrag();
        return;
      }

      // Does the DragController allow the drop?
      try {
        dragController.previewDragEnd(capturingWidget, dropController.getDropTarget());
      } catch (VetoDragException ex) {
        cancelDrag();
        return;
      }

      // Does the DropController allow the drop?
      try {
      dropController.onPreviewDrop(movableWidget, capturingWidget, dragController);
      } catch (VetoDropException ex) {
        cancelDrag();
        return;
      }

      dragController.dragEnd(capturingWidget, dropController.getDropTarget());
      dropController.onDrop(movableWidget, capturingWidget, dragController);
      dragController.notifyDragEnd(capturingWidget, dropController.getDropTarget());

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
    dragging = false;

    if (dropController != null) {
      dropController.onLeave(capturingWidget, dragController);
    }
    dropController = null;

    dragController.dragEnd(capturingWidget, null);
    dragController.notifyDragEnd(capturingWidget, null);
  }

  private void move(int x, int y) {
    Location senderLocation = new Location(capturingWidget, boundaryPanel);

    int desiredLeft = (x - initialMouseX) + senderLocation.getLeft();
    int desiredTop = (y - initialMouseY) + senderLocation.getTop();

    boundaryPanel.setWidgetPosition(movableWidget, desiredLeft, desiredTop);

    DropController newDropController = dragController.getIntersectDropController(movableWidget);
    if (dropController != newDropController) {
      if (dropController != null) {
        dropController.onLeave(capturingWidget, dragController);
      }
      dropController = newDropController;
      if (dropController != null) {
        dropController.onEnter(movableWidget, capturingWidget, dragController);
      }
    }

    if (dropController != null) {
      dropController.onMove(movableWidget, capturingWidget, dragController);
    }
  }
}
