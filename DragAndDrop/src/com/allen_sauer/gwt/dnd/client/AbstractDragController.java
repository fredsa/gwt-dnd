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
package com.allen_sauer.gwt.dnd.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;

import java.util.HashMap;
import java.util.Iterator;

/**
 * {@link DragController} which performs the bare essentials such as
 * adding/removing styles, maintaining collections, adding mouse listeners, etc.
 * 
 * <p>
 * Extend this class to implement specialized drag capabilities such table
 * column or panel resizing. For classic drag-and-drop functionality, i.e. the
 * ability to pickup, move around and drop widgets, use
 * {@link PickupDragController}.
 * </p>
 */
public abstract class AbstractDragController implements DragController {
  /**
   * @deprecated Instead selectively use your own CSS classes.
   */
  protected static final String CSS_DRAGGABLE;

  /**
   * @deprecated Instead selectively use your own CSS classes.
   */
  protected static final String CSS_DRAGGING;

  /**
   * @deprecated Instead selectively use your own CSS classes.
   */
  protected static final String CSS_HANDLE;

  private static final String CSS_SELECTED = "dragdrop-selected";
  private static HashMap dragHandles = new HashMap();
  private static final String PRIVATE_CSS_DRAGGABLE = "dragdrop-draggable";
  private static final String PRIVATE_CSS_DRAGGING = "dragdrop-dragging";

  private static final String PRIVATE_CSS_HANDLE = "dragdrop-handle";

  static {
    CSS_DRAGGABLE = PRIVATE_CSS_DRAGGABLE;
    CSS_DRAGGING = PRIVATE_CSS_DRAGGING;
    CSS_HANDLE = PRIVATE_CSS_HANDLE;
  }

  protected final DragContext context;
  AbsolutePanel boundaryPanel;
  private boolean constrainedToBoundaryPanel;
  private DragHandlerCollection dragHandlers;
  private int dragStartPixels;
  private MouseDragHandler mouseDragHandler;
  private boolean multipleSelectionAllowed = false;

  /**
   * Create a new drag-and-drop controller. Drag operations will be limited to
   * the specified boundary panel.
   * 
   * @param boundaryPanel the desired boundary panel or null if entire page is
   *            to be included
   */
  public AbstractDragController(AbsolutePanel boundaryPanel) {
    this.boundaryPanel = boundaryPanel != null ? boundaryPanel : RootPanel.get();
    context = new DragContext(this);
    mouseDragHandler = new MouseDragHandler(context);
  }

  public final void addDragHandler(DragHandler handler) {
    if (dragHandlers == null) {
      dragHandlers = new DragHandlerCollection();
    }
    dragHandlers.add(handler);
  }

  public void clearSelection() {
    for (Iterator iterator = context.selectedWidgets.iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();
      widget.removeStyleName(CSS_SELECTED);
      iterator.remove();
    }
  }

  public void dragEnd() {
    context.draggable.removeStyleName(PRIVATE_CSS_DRAGGING);
    if (dragHandlers != null) {
      dragHandlers.fireDragEnd(context);
    }
  }

  public final void dragEnd(Widget draggable, Widget dropTarget) {
    throw new UnsupportedOperationException();
  }

  public void dragStart() {
    resetCache();
    if (dragHandlers != null) {
      dragHandlers.fireDragStart(context);
    }
    context.draggable.addStyleName(PRIVATE_CSS_DRAGGING);
  }

  public final void dragStart(Widget draggable) {
    throw new UnsupportedOperationException();
  }

  public boolean getBehaviorConstrainedToBoundaryPanel() {
    return constrainedToBoundaryPanel;
  }

  public int getBehaviorDragStartSensitivity() {
    return dragStartPixels;
  }

  public boolean getBehaviorMultipleSelection() {
    return multipleSelectionAllowed;
  }

  public final AbsolutePanel getBoundaryPanel() {
    return boundaryPanel;
  }

  public final DropControllerCollection getDropControllerCollection() {
    throw new UnsupportedOperationException();
  }

  public final DropController getIntersectDropController(Widget widget) {
    throw new UnsupportedOperationException();
  }

  public final DropController getIntersectDropController(Widget widget, int x, int y) {
    throw new UnsupportedOperationException();
  }

  public final Widget getMovableWidget() {
    throw new UnsupportedOperationException();
  }

  /**
   * Attaches a {@link MouseDragHandler} (which is a
   * {@link com.google.gwt.user.client.ui.MouseListener}) to the widget,
   * applies the {@link #PRIVATE_CSS_DRAGGABLE} style to the draggable, applies the
   * {@link #PRIVATE_CSS_HANDLE} style to the handle.
   * 
   * @see #makeDraggable(Widget, Widget)
   * @see HasDragHandle
   * 
   * @param draggable the widget to be made draggable
   */
  public void makeDraggable(Widget draggable) {
    if (draggable instanceof HasDragHandle) {
      makeDraggable(draggable, ((HasDragHandle) draggable).getDragHandle());
    } else {
      makeDraggable(draggable, draggable);
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
    draggable.addStyleName(PRIVATE_CSS_DRAGGABLE);
    dragHandle.addStyleName(PRIVATE_CSS_HANDLE);
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
    draggable.removeStyleName(PRIVATE_CSS_DRAGGABLE);
    dragHandle.removeStyleName(PRIVATE_CSS_HANDLE);
  }

  public final void notifyDragEnd(DragEndEvent dragEndEvent) {
    throw new UnsupportedOperationException();
  }

  public void previewDragEnd() throws VetoDragException {
    if (dragHandlers != null) {
      dragHandlers.firePreviewDragEnd(context);
    }
  }

  public final void previewDragEnd(Widget draggable, Widget dropTarget) throws VetoDragException {
    throw new UnsupportedOperationException();
  }

  public void previewDragStart() throws VetoDragException {
    if (dragHandlers != null) {
      dragHandlers.firePreviewDragStart(context);
    }
  }

  public final void previewDragStart(Widget draggable) throws VetoDragException {
    throw new UnsupportedOperationException();
  }

  public final void removeDragHandler(DragHandler handler) {
    if (dragHandlers != null) {
      dragHandlers.remove(handler);
    }
  }

  public void resetCache() {
  }

  public void setBehaviorConstrainedToBoundaryPanel(boolean constrainedToBoundaryPanel) {
    this.constrainedToBoundaryPanel = constrainedToBoundaryPanel;
  }

  public void setBehaviorDragStartSensitivity(int pixels) {
    assert pixels >= 0;
    dragStartPixels = pixels;
  }

  public void setBehaviorMultipleSelection(boolean multipleSelectionAllowed) {
    this.multipleSelectionAllowed = multipleSelectionAllowed;
    for (Iterator iterator = context.selectedWidgets.iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();
      widget.removeStyleName(CSS_SELECTED);
      iterator.remove();
    }
  }

  public void setConstrainWidgetToBoundaryPanel(boolean constrainWidgetToBoundaryPanel) {
    setBehaviorConstrainedToBoundaryPanel(constrainWidgetToBoundaryPanel);
  }

  public void toggleSelection(Widget draggable) {
    assert draggable != null;
    if (context.selectedWidgets.remove(draggable)) {
      draggable.removeStyleName(CSS_SELECTED);
    } else if (multipleSelectionAllowed) {
      context.selectedWidgets.add(draggable);
      draggable.addStyleName(CSS_SELECTED);
    } else {
      context.selectedWidgets.clear();
      context.selectedWidgets.add(draggable);
    }
  }

  /**
   * @deprecated Use {@link PickupDragController#newBoundaryDropController(AbsolutePanel, boolean)} instead.
   */
  protected final BoundaryDropController newBoundaryDropController() {
    throw new UnsupportedOperationException();
  }

  /**
   * @deprecated Use {@link PickupDragController#newBoundaryDropController(AbsolutePanel, boolean)} instead.
   */
  protected BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel, boolean allowDropping) {
    throw new UnsupportedOperationException();
  }

  /**
  * @deprecated Use {@link PickupDragController#restoreSelectedWidgetsLocation()} instead.
  */
  protected final void restoreDraggableLocation(Widget draggable) {
    throw new UnsupportedOperationException();
  }

  /**
   * @deprecated Use {@link PickupDragController#restoreSelectedWidgetsStyle()} instead.
   */
  protected final void restoreDraggableStyle(Widget draggable) {
    throw new UnsupportedOperationException();
  }

  /**
   * @deprecated Use {@link PickupDragController#saveSelectedWidgetsLocationAndStyle()} instead.
   */
  protected final void saveDraggableLocationAndStyle(Widget draggable) {
    throw new UnsupportedOperationException();
  }
}