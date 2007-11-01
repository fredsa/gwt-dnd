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
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;

import java.util.HashMap;

/**
 * {@link DragController} which performs the bare essentials such as
 * adding/removing styles, maintaining collections, adding mouse listeners, etc.
 * 
 * <p>
 * Extend this class to implement specialized drag capabilities such table
 * column or panel resizing. For classic drag-and-drop functionality, i.e. the
 * ability to pickup, move around and drop widgets, use
 * {@link PickupDragController} instead.
 * </p>
 */
public abstract class AbstractDragController implements DragController {
  protected static final String CSS_DRAGGABLE = "dragdrop-draggable";
  protected static final String CSS_DRAGGING = "dragdrop-dragging";
  protected static final String CSS_HANDLE = "dragdrop-handle";

  private static HashMap dragHandles = new HashMap();

  private BoundaryDropController boundaryDropController;
  private AbsolutePanel boundaryPanel;
  private Widget draggable;
  private DragHandlerCollection dragHandlers;
  private DropControllerCollection dropControllerCollection = new DropControllerCollection();
  private MouseDragHandler mouseDragHandler;

  /**
   * Create a new drag-and-drop controller. Drag operations will be limited to
   * the specified boundary panel.
   * 
   * <p>
   * Note: An implicit {@link BoundaryDropController} is created and registered
   * automatically.
   * </p>
   * 
   * @param boundaryPanel the desired boundary panel or null if entire page is
   *            to be included
   * @param allowDroppingOnBoundaryPanel whether or not boundary panel should
   *            allow dropping
   */
  public AbstractDragController(AbsolutePanel boundaryPanel, boolean allowDroppingOnBoundaryPanel) {
    this.boundaryPanel = boundaryPanel != null ? boundaryPanel : RootPanel.get();
    boundaryDropController = newBoundaryDropController(this.boundaryPanel, allowDroppingOnBoundaryPanel);
    registerDropController(boundaryDropController);
    mouseDragHandler = new MouseDragHandler(this);
  }

  public final void addDragHandler(DragHandler handler) {
    if (dragHandlers == null) {
      dragHandlers = new DragHandlerCollection();
    }
    dragHandlers.add(handler);
  }

  public void dragEnd(Widget draggable, Widget dropTarget) {
    draggable.removeStyleName(CSS_DRAGGING);
  }

  public void dragStart(Widget draggable) {
    this.draggable = draggable;
    resetCache();
    if (dragHandlers != null) {
      dragHandlers.fireDragStart(draggable);
    }
    draggable.addStyleName(CSS_DRAGGING);
  }

  public final AbsolutePanel getBoundaryPanel() {
    return boundaryPanel;
  }

  public DropController getIntersectDropController(int x, int y) {
    DropController dropController = dropControllerCollection.getIntersectDropController(x, y, boundaryPanel);
    return dropController != null ? dropController : boundaryDropController;
  }

  /**
   * Attaches a {@link MouseDragHandler} (which is a
   * {@link com.google.gwt.user.client.ui.MouseListener}) to the widget,
   * applies the {@link #CSS_DRAGGABLE} style to the draggable, applies the
   * {@link #CSS_HANDLE} style to the handle.
   * 
   * @see #makeDraggable(Widget, Widget)
   * @see HasDragHandle
   * 
   * @param widget the widget to be made draggable
   */
  public void makeDraggable(Widget widget) {
    if (widget instanceof HasDragHandle) {
      makeDraggable(widget, ((HasDragHandle) widget).getDragHandle());
    } else {
      makeDraggable(widget, widget);
    }
  }

  /**
   * Similar to {@link #makeDraggable(Widget)}, but allow separate, child to be
   * specified as the drag handle by which the first widget can be dragged.
   * 
   * @param draggable the widget to be made draggable
   * @param dragHandle the widget by which widget can be dragged
   */
  public void makeDraggable(Widget draggable, Widget dragHandle) {
    mouseDragHandler.makeDraggable(draggable, dragHandle);
    draggable.addStyleName(CSS_DRAGGABLE);
    dragHandle.addStyleName(CSS_HANDLE);
    dragHandles.put(draggable, dragHandle);
  }

  /**
   * Performs the reverse of {@link #makeDraggable(Widget)}, detaching the
   * {@link MouseDragHandler} from the widget and removing any styling which was
   * applied when making the widget draggable.
   * 
   * @param draggable the widget to no longer be draggable
   */
  public void makeNotDraggable(Widget draggable) {
    Widget dragHandle = (Widget) dragHandles.remove(draggable);
    mouseDragHandler.makeNotDraggable(dragHandle);
    draggable.removeStyleName(CSS_DRAGGABLE);
    dragHandle.removeStyleName(CSS_HANDLE);
  }

  public void notifyDragEnd(DragEndEvent dragEndEvent) {
    if (dragHandlers != null) {
      dragHandlers.fireDragEnd(dragEndEvent);
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

  /**
   * Reset the internal drop controller (drop target) cache which was setup during
   * {{@link #dragStart(Widget)}. This method should be called when a drop target's
   * size and/or location changes, or when drop target eligibility changes.
   */
  public void resetCache() {
    dropControllerCollection.resetCache(boundaryPanel, draggable);
  }

  public void setConstrainWidgetToBoundaryPanel(boolean constrainWidgetToBoundaryPanel) {
    mouseDragHandler.setConstrainWidgetToBoundaryPanel(constrainWidgetToBoundaryPanel);
  }

  public void unregisterDropController(DropController dropController) {
    dropControllerCollection.remove(dropController);
  }

  /**
   * Allow subclasses ability to override implementing
   * {@link DropControllerCollection} to support custom intersection logic.
   * 
   * @return a new DropControllerCollection
   * @deprecated No longer part of gwt-dnd 2.x API. Override {@link #registerDropController(DropController)}, {@link #unregisterDropController(DropController)}, {@link #resetCache()}, etc. instead
  */
  protected final DropControllerCollection getDropControllerCollection() {
    throw new UnsupportedOperationException();
  }

  /**
   * Create a new BoundaryDropController to manage our boundary panel as a drop
   * target. To ensure that draggable widgets can only be dropped on registered
   * drop targets, set <code>allowDropping</code> to <code>false</code>.
   * 
   * @param boundaryPanel the panel to which our drag-and-drop operations are constrained
   * @param allowDropping whether or not dropping is allowed on the boundary panel
   * @return the new BoundaryDropController
   */
  protected BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel, boolean allowDropping) {
    return new BoundaryDropController(boundaryPanel, allowDropping);
  }

  /**
   * @deprecated Method has been moved down to {@link PickupDragController#restoreDraggableLocation(Widget)}
   */
  protected void restoreDraggableLocation(Widget draggable) {
    throw new UnsupportedOperationException();
  }

  /**
   * @deprecated Method has been moved down to {@link PickupDragController#restoreDraggableStyle(Widget)}
   */
  protected void restoreDraggableStyle(Widget draggable) {
    throw new UnsupportedOperationException();
  }

  /**
   * @deprecated Method has been moved down to {@link PickupDragController#saveDraggableLocationAndStyle(Widget)}
   */
  protected void saveDraggableLocationAndStyle(Widget draggable) {
    throw new UnsupportedOperationException();
  }
}