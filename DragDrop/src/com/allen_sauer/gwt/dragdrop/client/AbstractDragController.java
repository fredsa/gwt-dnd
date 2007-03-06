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

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;

import java.util.HashMap;

/**
 * Abstract DragController which performs the bare essentials such as
 * adding/removing styles, maintaining collections, adding mouse
 * listeners, etc.
 * 
 * <p>Extend this class when you do not require actual widgets to be picked
 * up a moved around (see {@link PickupDragController}), e.g. for resizing
 * table columns or a panel.</p>
 */
public abstract class AbstractDragController implements DragController {

  protected static final String STYLE_DRAGGING = "dragdrop-dragging";
  private static final String STYLE_DRAGGABLE = "dragdrop-draggable";

  private static HashMap widgetControllers = new HashMap();

  // TODO remove this method as it is barely used
  public static DragController getDragController(Widget widget) {
    return (DragController) widgetControllers.get(widget);
  }

  private MouseDragHandler mouseDragHandler;
  private BoundaryDropController boundaryDropController;
  private AbsolutePanel boundaryPanel;
  private DragHandlerCollection dragHandlers;
  private DropControllerCollection dropControllerCollection = new DropControllerCollection();

  /**
   * Create a new drag-and-drop controller. Drag operations will be limited to the
   * specified boundary panel.
   * 
   * <p>Note: An implicit {@link BoundaryDropController} is created and 
   * registered automatically.</p>
   * 
   * @param boundaryPanel the desired boundary panel or null if entire page is to be included
   */
  public AbstractDragController(AbsolutePanel boundaryPanel) {
    this.boundaryPanel = boundaryPanel != null ? boundaryPanel : RootPanel.get();
    boundaryDropController = newBoundaryDropController(boundaryPanel);
    registerDropController(boundaryDropController);
    mouseDragHandler = new MouseDragHandler(this);
  }

  public final void addDragHandler(DragHandler handler) {
    if (dragHandlers == null) {
      dragHandlers = new DragHandlerCollection();
    }
    dragHandlers.add(handler);
  }

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @param dropTarget widget on which draggable was dropped. 
   *        <code>null</code> if drag was canceled
   */
  public void dragEnd(Widget draggable, Widget dropTarget) {
    draggable.removeStyleName(STYLE_DRAGGING);
  }

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   */
  public void dragStart(Widget draggable) {
    if (dragHandlers != null) {
      dragHandlers.fireDragStart(draggable);
    }
    draggable.addStyleName(STYLE_DRAGGING);
  }

  public final AbsolutePanel getBoundaryPanel() {
    return boundaryPanel;
  }

  public DropController getIntersectDropController(Widget widget) {
    DropController dropController = dropControllerCollection.getIntersectDropController(widget, boundaryPanel);
    return dropController != null ? dropController : boundaryDropController;
  }

  public void makeDraggable(Widget widget) {
    if (widget instanceof SourcesMouseEvents) {
      ((SourcesMouseEvents) widget).addMouseListener(mouseDragHandler);
    } else {
      throw new RuntimeException("widget must implement SourcesMouseEvents to be draggable");
    }
    widget.addStyleName(STYLE_DRAGGABLE);
    widgetControllers.put(widget, this);
  }

  public BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel) {
    return new BoundaryDropController(boundaryPanel, true);
  }

  public void notifyDragEnd(Widget draggable, Widget dropTarget) {
    if (dragHandlers != null) {
      dragHandlers.fireDragEnd(draggable, dropTarget);
    }
  }

  public void previewDragEnd(Widget draggable, Widget dropTarget) throws VetoDragException {
    if (dragHandlers != null) {
      dragHandlers.firePreviewDragEnd(draggable, dropTarget);
    }
  }

  public void previewDragStart(Widget draggable) throws VetoDragException {
    if (dragHandlers != null) {
      dragHandlers.firePreviewDragStart(draggable);
    }
  }

  public final void registerDropController(DropController dropController) {
    dropControllerCollection.add(dropController);
  }

  public final void removeDragHandler(DragHandler handler) {
    if (dragHandlers != null) {
      dragHandlers.remove(handler);
    }
  }
}
