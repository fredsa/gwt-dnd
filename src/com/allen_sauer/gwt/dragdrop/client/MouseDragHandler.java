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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.drop.VetoDropException;
import com.allen_sauer.gwt.dragdrop.client.util.DOMUtil;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

import java.util.HashMap;

/**
 * Implementation helper class which handles mouse events for all
 * draggable widgets for a given {@link DropController}.
 */
class MouseDragHandler implements MouseListener {
  private int boundaryOffsetX;
  private int boundaryOffsetY;
  private FocusPanel capturingWidget;
  private boolean constrainedToBoundaryPanel = false;
  private final DragContext context;
  private DeferredMoveCommand deferredMoveCommand = new DeferredMoveCommand(this);
  private boolean dragging;
  private HashMap dragHandleMap = new HashMap();
  private int maxLeft;
  private int maxTop;
  private boolean mouseDown;
  private int mouseDownOffsetX;
  private int mouseDownOffsetY;
  private Widget mouseDownWidget;

  MouseDragHandler(DragContext context) {
    this.context = context;
    initCapturingWidget();
  }

  public void onMouseDown(Widget sender, int x, int y) {
    if (dragging) {
      // Ignore additional mouse buttons depressed while still dragging
      return;
    }

    Event event = DOM.eventGetCurrentEvent();
    int button = DOM.eventGetButton(event);
    // TODO remove Event.UNDEFINED after GWT Issue 1535 is fixed
    if (button != Event.BUTTON_LEFT && button != Event.UNDEFINED) {
      return;
    }

    // mouse down (not first mouse move) determines draggable widget
    mouseDownWidget = sender;
    context.draggable = (Widget) dragHandleMap.get(mouseDownWidget);
    assert context.draggable != null;

    if (!DOM.eventGetCtrlKey(event) && !context.selectedWidgets.contains(mouseDownWidget)) {
      context.dragController.clearSelection();
    }
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        DOMUtil.cancelAllDocumentSelections();
      }
    });

    mouseDown = true;
    DOM.eventPreventDefault(event);

    mouseDownOffsetX = x;
    mouseDownOffsetY = y;
  }

  public void onMouseEnter(Widget sender) {
  }

  public void onMouseLeave(Widget sender) {
  }

  public void onMouseMove(Widget sender, int x, int y) {
    if (dragging) {
      // TODO remove Safari workaround after GWT issue 1807 fixed
      if (sender != capturingWidget) {
        // In Safari 1.3.2 MAC, other mouse events continue to arrive even when capturing
        return;
      }
    } else {
      if (mouseDown) {
        DOMUtil.cancelAllDocumentSelections();
        if (!context.selectedWidgets.contains(context.draggable)) {
          context.dragController.toggleSelection(context.draggable);
        }
        startDragging();

        // adjust (x,y) to be relative to capturingWidget at (0,0)
        Location location = new WidgetLocation(mouseDownWidget, null);
        x += location.getLeft();
        y += location.getTop();
      }
      if (!dragging) {
        return;
      }
    }
    // proceed with the actual drag
    DOM.eventPreventDefault(DOM.eventGetCurrentEvent());
    try {
      deferredMoveCommand.scheduleOrExecute(x, y);
    } catch (RuntimeException ex) {
      cancelDrag();
      throw ex;
    }
  }

  public void onMouseUp(Widget sender, int x, int y) {
    int button = DOM.eventGetButton(DOM.eventGetCurrentEvent());
    // TODO Remove Event.UNDEFINED after GWT Issue 1535 is fixed
    if (button != Event.BUTTON_LEFT && button != Event.UNDEFINED) {
      return;
    }
    mouseDown = false;
    if (!dragging) {
      Widget widget = (Widget) dragHandleMap.get(sender);
      context.dragController.toggleSelection(widget);
      DOMUtil.cancelAllDocumentSelections();
      return;
    }
    // TODO Remove Safari workaround after GWT issue 1807 fixed
    if (sender != capturingWidget) {
      // In Safari 1.3.2 MAC does not honor capturing widget for mouse up
      Location location = new WidgetLocation(sender, null);
      x += location.getLeft();
      y += location.getTop();
    }
    // Proceed with the drop
    drop(x, y);
  }

  void actualMove(int x, int y) {
    int desiredLeft = x - boundaryOffsetX - mouseDownOffsetX;
    int desiredTop = y - boundaryOffsetY - mouseDownOffsetY;

    if (constrainedToBoundaryPanel) {
      desiredLeft = Math.max(0, Math.min(desiredLeft, maxLeft));
      desiredTop = Math.max(0, Math.min(desiredTop, maxTop));
    }

    DOMUtil.fastSetElementPosition(context.movableWidget.getElement(), desiredLeft, desiredTop);

    DropController newDropController = context.dragController.getIntersectDropController(context.movableWidget, x, y);
    if (context.dropController != newDropController) {
      if (context.dropController != null) {
        context.dropController.onLeave(context);
      }
      context.dropController = newDropController;
      if (context.dropController != null) {
        context.dropController.onEnter(context);
      }
    }

    if (context.dropController != null) {
      context.mouseX = x;
      context.mouseY = y;
      context.dropController.onMove(context);
    }
  }

  boolean isConstrainedToBoundaryPanel() {
    return constrainedToBoundaryPanel;
  }

  void makeDraggable(Widget draggable, Widget dragHandle) {
    if (dragHandle instanceof SourcesMouseEvents) {
      ((SourcesMouseEvents) dragHandle).addMouseListener(this);
      dragHandleMap.put(dragHandle, draggable);
    } else {
      throw new RuntimeException("dragHandle must implement SourcesMouseEvents to be draggable");
    }
  }

  void makeNotDraggable(Widget dragHandle) {
    if (dragHandleMap.remove(dragHandle) == null) {
      throw new RuntimeException("dragHandle was not draggable");
    }
    ((SourcesMouseEvents) dragHandle).removeMouseListener(this);
  }

  void setConstrainedToBoundaryPanel(boolean constrainedToBoundaryPanel) {
    this.constrainedToBoundaryPanel = constrainedToBoundaryPanel;
  }

  private void cancelDrag() {
    try {
      if (context.dropController != null) {
        context.dropController.onLeave(context);
      }

      context.dropController = null;
      context.dragController.dragEnd();

      DragEndEvent canceledDragEndEvent = new DragEndEvent(context);
      context.dragController.notifyDragEnd(canceledDragEndEvent);
    } finally {
      cleanup();
    }
  }

  private void cleanup() {
    DOM.releaseCapture(capturingWidget.getElement());
    dragging = false;
    context.dropController = null;
  }

  private void drop(int x, int y) {
    try {
      actualMove(x, y);
      dragging = false;

      // Is there a DropController willing to handle our request?
      if (context.dropController == null) {
        cancelDrag();
        return;
      }

      // Does the DragController allow the drop?
      try {
        context.dragController.previewDragEnd();
      } catch (VetoDragException ex) {
        cancelDrag();
        return;
      }

      // Does the DropController allow the drop?
      try {
        context.dropController.onPreviewDrop(context);
      } catch (VetoDropException ex) {
        cancelDrag();
        return;
      }

      // remove movable panel, cleanup styles, etc.
      context.dragController.dragEnd();

      // use state information from earlier onPreviewDrop to attached draggable
      // to dropTarget
      DragEndEvent dragEndEvent = context.dropController.onDrop(context);

      // notify listeners
      context.dragController.notifyDragEnd(dragEndEvent);
    } finally {
      cleanup();
    }
  }

  private void initCapturingWidget() {
    capturingWidget = new FocusPanel();
    capturingWidget.setPixelSize(0, 0);
    RootPanel.get().add(capturingWidget, 0, 0);
    capturingWidget.addMouseListener(this);
    DOM.setStyleAttribute(capturingWidget.getElement(), "visibility", "hidden");
    DOM.setStyleAttribute(capturingWidget.getElement(), "backgroundColor", "yellow");
  }

  private void startDragging() {
    try {
      context.dragController.previewDragStart();
    } catch (VetoDragException ex) {
      return;
    }
    context.movableWidget = context.dragController.dragStart();

    // adjust mouse down offsets in case mouseDownWidget shares movableWidget with multiple draggables
    WidgetLocation mouseDownLocation = new WidgetLocation(mouseDownWidget, context.movableWidget);
    mouseDownOffsetX += mouseDownLocation.getLeft();
    mouseDownOffsetY += mouseDownLocation.getTop();

    // calculate the max (x, y) for use during dragging
    maxLeft = DOMUtil.getClientWidth(context.boundaryPanel.getElement()) - context.movableWidget.getOffsetWidth();
    maxTop = DOMUtil.getClientHeight(context.boundaryPanel.getElement()) - context.movableWidget.getOffsetHeight();

    // one time calculation of boundary panel location for efficiency during dragging
    Location location = new WidgetLocation(context.boundaryPanel, null);
    boundaryOffsetX = location.getLeft() + DOMUtil.getBorderLeft(context.boundaryPanel.getElement());
    boundaryOffsetY = location.getTop() + DOMUtil.getBorderTop(context.boundaryPanel.getElement());

    DOM.setCapture(capturingWidget.getElement());
    dragging = true;
  }
}
