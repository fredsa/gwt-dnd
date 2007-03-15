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
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.drop.VetoDropException;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

import java.util.HashMap;

/**
 * Helper class which handles mouse events for all
 * draggable widgets for a give {@link DropController}.
 */
public class MouseDragHandler implements MouseListener {

  private HashMap dragHandleMap = new HashMap();
  private AbsolutePanel boundaryPanel;
  private Widget capturingWidget;
  private Widget draggable;
  private DragController dragController;
  private Widget moveableWidget;
  private boolean dragging;
  private DropController dropController;
  private int initialMouseX;
  private int initialMouseY;
  private int offsetX;
  private int offsetY;
  private DeferredMoveCommand deferredMoveCommand = new DeferredMoveCommand(this);

  public MouseDragHandler(DragController dragController) {
    this.dragController = dragController;
    boundaryPanel = dragController.getBoundaryPanel();
  }

  public void makeDraggable(Widget draggable, Widget dragHandle) {
    if (dragHandle instanceof SourcesMouseEvents) {
      ((SourcesMouseEvents) dragHandle).addMouseListener(this);
      dragHandleMap.put(dragHandle, draggable);
    } else {
      throw new RuntimeException("widget must implement SourcesMouseEvents to be draggable");
    }
  }

  public void onMouseDown(Widget sender, int x, int y) {
    if (dragging) {
      // Ignore additional mouse buttons depressed while still dragging
      return;
    }
    capturingWidget = sender;
    draggable = (Widget) dragHandleMap.get(capturingWidget);
    initialMouseX = x;
    initialMouseY = y;

    try {
      dragController.previewDragStart(draggable);
    } catch (VetoDragException ex) {
      return;
    }
    dragController.dragStart(draggable);

    moveableWidget = dragController.getMovableWidget();

    Location location = new Location(capturingWidget, boundaryPanel);
    Location altLocation = new Location(moveableWidget, boundaryPanel);
    offsetX = altLocation.getLeft() - location.getLeft();
    offsetY = altLocation.getTop() - location.getTop();

    DOM.setCapture(capturingWidget.getElement());
    dragging = true;
    try {
      actualMove(x, y);
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
      deferredMoveCommand.scheduleOrExecute(x, y);
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

      actualMove(x, y);
      dragging = false;

      // Is there a DropController willing to handle our request?
      if (dropController == null) {
        cancelDrag();
        return;
      }

      // Does the DragController allow the drop?
      try {
        dragController.previewDragEnd(draggable, dropController.getDropTarget());
      } catch (VetoDragException ex) {
        cancelDrag();
        return;
      }

      // Does the DropController allow the drop?
      try {
        dropController.onPreviewDrop(moveableWidget, draggable, dragController);
      } catch (VetoDropException ex) {
        cancelDrag();
        return;
      }

      dragController.dragEnd(draggable, dropController.getDropTarget());
      dropController.onDrop(moveableWidget, draggable, dragController);
      dragController.notifyDragEnd(capturingWidget, dropController.getDropTarget());

    } catch (RuntimeException ex) {
      // cleanup in case anything goes wrong
      cancelDrag();
      throw ex;
    } finally {
      dropController = null;
    }
  }

  void actualMove(int x, int y) {
    Location location = new Location(capturingWidget, boundaryPanel);
    int desiredLeft = location.getLeft() + offsetX + (x - initialMouseX);
    int desiredTop = location.getTop() + offsetY + (y - initialMouseY);
    boundaryPanel.setWidgetPosition(moveableWidget, desiredLeft, desiredTop);

    DropController newDropController = dragController.getIntersectDropController(moveableWidget);
    if (dropController != newDropController) {
      if (dropController != null) {
        dropController.onLeave(draggable, dragController);
      }
      dropController = newDropController;
      if (dropController != null) {
        dropController.onEnter(moveableWidget, draggable, dragController);
      }
    }

    if (dropController != null) {
      dropController.onMove(moveableWidget, draggable, dragController);
    }
  }

  private void cancelDrag() {
    // Do this first so it always happens
    DOM.releaseCapture(capturingWidget.getElement());
    dragging = false;

    if (dropController != null) {
      dropController.onLeave(draggable, dragController);
    }
    dropController = null;

    dragController.dragEnd(draggable, null);
    dragController.notifyDragEnd(draggable, null);
  }
}
