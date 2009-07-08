/*
 * Copyright 2009 Fred Sauer
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
package com.allen_sauer.gwt.dnd.client;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.allen_sauer.gwt.dnd.client.util.WidgetLocation;

import java.util.HashMap;

/*
 * Implementation helper class which handles mouse events for all draggable
 * widgets for a given {@link DragController}.
 */
class MouseDragHandler implements MouseMoveHandler, MouseDownHandler, MouseUpHandler,
    MouseOutHandler {

  private static class RegisteredDraggable {
    private final Widget dragable;
    private final HandlerRegistration mouseDownHandlerRegistration;
    private final HandlerRegistration mouseMoveHandlerRegistration;
    private final HandlerRegistration mouseOutHandlerRegistration;
    private final HandlerRegistration mouseUpHandlerRegistration;

    RegisteredDraggable(Widget dragable, HandlerRegistration mouseDownHandlerRegistration,
        HandlerRegistration mouseUpHandlerRegistration,
        HandlerRegistration mouseMoveHandlerRegistration,
        HandlerRegistration mouseOutHandlerRegistration) {
      this.dragable = dragable;
      this.mouseDownHandlerRegistration = mouseDownHandlerRegistration;
      this.mouseMoveHandlerRegistration = mouseMoveHandlerRegistration;
      this.mouseOutHandlerRegistration = mouseOutHandlerRegistration;
      this.mouseUpHandlerRegistration = mouseUpHandlerRegistration;
    }

    Widget getDragable() {
      return dragable;
    }

    HandlerRegistration getMouseDownHandlerRegistration() {
      return mouseDownHandlerRegistration;
    }

    public HandlerRegistration getMouseMoveHandlerRegistration() {
      return mouseMoveHandlerRegistration;
    }

    HandlerRegistration getMouseOutHandlerRegistration() {
      return mouseOutHandlerRegistration;
    }

    public HandlerRegistration getMouseUpHandlerRegistration() {
      return mouseUpHandlerRegistration;
    }
  }

  private static final int ACTIVELY_DRAGGING = 3;

  private static final int DRAGGING_NO_MOVEMENT_YET = 2;

  private static final int NOT_DRAGGING = 1;

  private FocusPanel capturingWidget;

  private final DragContext context;

  private int dragging = NOT_DRAGGING;

  private HashMap<Widget, RegisteredDraggable> dragHandleMap = new HashMap<Widget, RegisteredDraggable>();

  private boolean mouseDown;

  private int mouseDownOffsetX;

  private int mouseDownOffsetY;

  private Widget mouseDownWidget;

  MouseDragHandler(DragContext context) {
    this.context = context;
    initCapturingWidget();
  }

  void actualMove(int x, int y) {
    context.mouseX = x;
    context.mouseY = y;
    context.desiredDraggableX = x - mouseDownOffsetX;
    context.desiredDraggableY = y - mouseDownOffsetY;

    context.dragController.dragMove();
  }

  private void doSelectionToggle(MouseEvent<?> event) {
    Widget widget = dragHandleMap.get(mouseDownWidget).getDragable();
    assert widget != null;
    if (!toggleKey(event)) {
      context.dragController.clearSelection();
    }
    context.dragController.toggleSelection(widget);
  }

  private void dragEndCleanup() {
    DOM.releaseCapture(capturingWidget.getElement());
    dragging = NOT_DRAGGING;
    context.dragEndCleanup();
  }

  private void drop(int x, int y) {
    actualMove(x, y);

    // Does the DragController allow the drop?
    try {
      context.dragController.previewDragEnd();
    } catch (VetoDragException ex) {
      context.vetoException = ex;
    }

    context.dragController.dragEnd();
  }

  private void initCapturingWidget() {
    capturingWidget = new FocusPanel();
    capturingWidget.setPixelSize(0, 0);
    capturingWidget.addMouseMoveHandler(this);
    capturingWidget.addMouseUpHandler(this);
    capturingWidget.getElement().getStyle().setProperty("visibility", "hidden");
    capturingWidget.getElement().getStyle().setProperty("margin", "0px");
    capturingWidget.getElement().getStyle().setProperty("border", "none");
  }

  void makeDraggable(Widget draggable, Widget dragHandle) {
    try {
      RegisteredDraggable registeredDraggable = new RegisteredDraggable(draggable,
          ((HasMouseDownHandlers) dragHandle).addMouseDownHandler(this),
          ((HasMouseUpHandlers) dragHandle).addMouseUpHandler(this),
          ((HasMouseMoveHandlers) dragHandle).addMouseMoveHandler(this),
          ((HasMouseOutHandlers) dragHandle).addMouseOutHandler(this));
      dragHandleMap.put(dragHandle, registeredDraggable);
    } catch (Exception ex) {
      throw new RuntimeException(
          "dragHandle must implement HasMouseDownHandlers, HasMouseUpHandlers, HasMouseMoveHandlers and HasMouseOutHandlers to be draggable",
          ex);
    }
  }

  void makeNotDraggable(Widget dragHandle) {
    RegisteredDraggable registeredDraggable = dragHandleMap.remove(dragHandle);
    if (registeredDraggable == null) {
      throw new RuntimeException("dragHandle was not draggable");
    }
    registeredDraggable.getMouseDownHandlerRegistration().removeHandler();
    registeredDraggable.getMouseUpHandlerRegistration().removeHandler();
    registeredDraggable.getMouseOutHandlerRegistration().removeHandler();
  }

  public void onMouseDown(MouseDownEvent event) {
    Widget sender = (Widget) event.getSource();
    int x = event.getX();
    int y = event.getY();

    int button = event.getNativeButton();

    if (dragging == ACTIVELY_DRAGGING || dragging == DRAGGING_NO_MOVEMENT_YET) {
      // Ignore additional mouse buttons depressed while still dragging
      return;
    }

    if (button != NativeEvent.BUTTON_LEFT) {
      return;
    }

    if (mouseDownWidget != null) {
      // For multiple overlapping draggable widgets, ignore all but first onMouseDown
      return;
    }

    // mouse down (not first mouse move) determines draggable widget
    mouseDownWidget = sender;
    context.draggable = dragHandleMap.get(mouseDownWidget).getDragable();
    assert context.draggable != null;

    if (!toggleKey(event) && !context.selectedWidgets.contains(mouseDownWidget)) {
      context.dragController.clearSelection();
      context.dragController.toggleSelection(context.draggable);
    }
    if (context.dragController.getBehaviorCancelDocumentSelections()) {
      DeferredCommand.addCommand(new Command() {
        public void execute() {
          DOMUtil.cancelAllDocumentSelections();
        }
      });
    }

    mouseDown = true;
    event.preventDefault();

    mouseDownOffsetX = x;
    mouseDownOffsetY = y;
    WidgetLocation loc1 = new WidgetLocation(mouseDownWidget, null);
    if (mouseDownWidget != context.draggable) {
      WidgetLocation loc2 = new WidgetLocation(context.draggable, null);
      mouseDownOffsetX += loc1.getLeft() - loc2.getLeft();
      mouseDownOffsetY += loc1.getTop() - loc2.getTop();
    }
    if (context.dragController.getBehaviorDragStartSensitivity() == 0 && !toggleKey(event)) {
      // set context.mouseX/Y before startDragging() is called
      context.mouseX = x + loc1.getLeft();
      context.mouseY = y + loc1.getTop();
      startDragging();
      if (dragging == NOT_DRAGGING) {
        return;
      }
      actualMove(context.mouseX, context.mouseY);
    }
  }

  public void onMouseMove(MouseMoveEvent event) {
    Widget sender = (Widget) event.getSource();
    Element elem = sender.getElement();
    // TODO optimize for the fact that elem is at (0,0)
    int x = event.getRelativeX(elem);
    int y = event.getRelativeY(elem);

    if (dragging == ACTIVELY_DRAGGING || dragging == DRAGGING_NO_MOVEMENT_YET) {
      // TODO remove Safari workaround after GWT issue 1807 fixed
      if (sender != capturingWidget) {
        // In Safari 1.3.2 MAC, other mouse events continue to arrive even when capturing
        return;
      }
      dragging = ACTIVELY_DRAGGING;
    } else {
      if (mouseDown) {
        if (Math.max(Math.abs(x - mouseDownOffsetX), Math.abs(y - mouseDownOffsetY)) >= context.dragController.getBehaviorDragStartSensitivity()) {
          if (context.dragController.getBehaviorCancelDocumentSelections()) {
            DOMUtil.cancelAllDocumentSelections();
          }
          if (!context.selectedWidgets.contains(context.draggable)) {
            context.dragController.toggleSelection(context.draggable);
          }

          // set context.mouseX/Y before startDragging() is called
          Location location = new WidgetLocation(mouseDownWidget, null);
          context.mouseX = mouseDownOffsetX + location.getLeft();
          context.mouseY = mouseDownOffsetY + location.getTop();

          startDragging();
        } else {
          // prevent IE image drag when drag sensitivity > 5
          DOM.eventPreventDefault(DOM.eventGetCurrentEvent());
        }
      }
      if (dragging == NOT_DRAGGING) {
        return;
      }
    }
    // proceed with the actual drag
    DOM.eventPreventDefault(DOM.eventGetCurrentEvent());
    actualMove(x, y);
  }

  public void onMouseOut(MouseOutEvent event) {
    if (mouseDown && dragging == NOT_DRAGGING) {
      // TODO DOMUtil.cancelAllDocumentSelections(); ?

      // set context.mouseX/Y before startDragging() is called
      Location location = new WidgetLocation(mouseDownWidget, null);
      context.mouseX = mouseDownOffsetX + location.getLeft();
      context.mouseY = mouseDownOffsetY + location.getTop();

      startDragging();
    }
  }

  public void onMouseUp(MouseUpEvent event) {
    try {
      Widget sender = (Widget) event.getSource();
      Element elem = sender.getElement();
      // TODO optimize for the fact that elem is at (0,0)
      int x = event.getRelativeX(elem);
      int y = event.getRelativeY(elem);

      int button = event.getNativeButton();
      if (button != NativeEvent.BUTTON_LEFT) {
        return;
      }
      mouseDown = false;

      // in case mouse down occurred elsewhere
      if (mouseDownWidget == null) {
        return;
      }

      if (context.dragController.getBehaviorCancelDocumentSelections()) {
        DOMUtil.cancelAllDocumentSelections();
      }
      if (dragging == NOT_DRAGGING) {
        doSelectionToggle(event);
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
      try {
        drop(x, y);
        if (dragging != ACTIVELY_DRAGGING) {
          doSelectionToggle(event);
        }
      } finally {
        dragEndCleanup();
      }
    } finally {
      mouseDownWidget = null;
    }
  }

  private void startDragging() {
    context.dragStartCleanup();
    try {
      context.dragController.previewDragStart();
    } catch (VetoDragException ex) {
      context.vetoException = ex;
      mouseDown = false;
      return;
    }
    context.dragController.dragStart();

    // defend against issue 55
    if (!capturingWidget.isAttached()) {
      RootPanel.get().add(capturingWidget, 0, 0);
    }
    DOM.setCapture(capturingWidget.getElement());
    dragging = DRAGGING_NO_MOVEMENT_YET;
  }

  private boolean toggleKey(MouseEvent<?> event) {
    return event.isControlKeyDown() || event.isMetaKeyDown();
  }
}
