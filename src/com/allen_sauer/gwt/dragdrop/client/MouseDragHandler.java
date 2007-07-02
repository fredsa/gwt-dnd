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
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

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
  private Widget movableWidget;
  private boolean mouseDown;
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
      throw new RuntimeException("dragHandle must implement SourcesMouseEvents to be draggable");
    }
  }

  public void makeNotDraggable(Widget dragHandle) {
    if (dragHandleMap.remove(dragHandle) == null) {
      throw new RuntimeException("dragHandle was not draggable");
    }
    ((SourcesMouseEvents) dragHandle).removeMouseListener(this);
  }

  public void onMouseDown(Widget sender, int x, int y) {
    mouseDown = true;
    if (dragging) {
      // Ignore additional mouse buttons depressed while still dragging
      return;
    }
    initialMouseX = x;
    initialMouseY = y;
  }

  public void onMouseEnter(Widget sender) {
  }

  public void onMouseLeave(Widget sender) {
  }

  public void onMouseMove(Widget sender, int x, int y) {
    if (!dragging) {
      if (mouseDown) {
        startDragging(sender);
      }
      if (!dragging) {
        return;
      }
    }
    try {
      deferredMoveCommand.scheduleOrExecute(x, y);
    } catch (RuntimeException ex) {
      cancelDrag();
      throw ex;
    }
  }

  public void onMouseUp(Widget sender, int x, int y) {
    mouseDown = false;
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
        dropController.onPreviewDrop(movableWidget, draggable, dragController);
      } catch (VetoDropException ex) {
        cancelDrag();
        return;
      }

      // remove movable panel, cleanup styles, etc.
      dragController.dragEnd(draggable, dropController.getDropTarget());
      
      // use state information from earlier onPreviewDrop to attached draggable to dropTarget
      DragEndEvent dragEndEvent = dropController.onDrop(movableWidget, draggable, dragController);
      
      // notify listeners
      dragController.notifyDragEnd(dragEndEvent);

    } catch (RuntimeException ex) {
      // cleanup in case anything goes wrong
      cancelDrag();
      throw ex;
    } finally {
      dropController = null;
    }
  }

  public void startDragging(Widget w) {
    dragController.resetCache();
    capturingWidget = w;
    draggable = (Widget) dragHandleMap.get(capturingWidget);

    try {
      dragController.previewDragStart(draggable);
    } catch (VetoDragException ex) {
      return;
    }
    dragController.dragStart(draggable);

    movableWidget = dragController.getMovableWidget();

    Location location = new WidgetLocation(capturingWidget, boundaryPanel);
    Location altLocation = new WidgetLocation(movableWidget, boundaryPanel);
    offsetX = altLocation.getLeft() - location.getLeft();
    offsetY = altLocation.getTop() - location.getTop();

    DOM.setCapture(capturingWidget.getElement());
    dragging = true;
    try {
      actualMove(initialMouseX, initialMouseY);
    } catch (RuntimeException ex) {
      cancelDrag();
      throw ex;
    }
  }

  void actualMove(int x, int y) {
    Location location = new WidgetLocation(capturingWidget, boundaryPanel);
    int desiredLeft = location.getLeft() + offsetX + (x - initialMouseX);
    int desiredTop = location.getTop() + offsetY + (y - initialMouseY);
    boundaryPanel.setWidgetPosition(movableWidget, desiredLeft, desiredTop);

    DropController newDropController = dragController.getIntersectDropController(movableWidget);
    if (dropController != newDropController) {
      if (dropController != null) {
        dropController.onLeave(draggable, dragController);
      }
      dropController = newDropController;
      if (dropController != null) {
        dropController.onEnter(movableWidget, draggable, dragController);
      }
    }

    if (dropController != null) {
      dropController.onMove(movableWidget, draggable, dragController);
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
    DragEndEvent dragEndEvent = new DragEndEvent(draggable, null);
    dragController.notifyDragEnd(dragEndEvent);
  }
}
