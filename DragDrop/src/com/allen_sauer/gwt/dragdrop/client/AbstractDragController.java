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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
  /**
   * @deprecated Use {@link #PRIVATE_CSS_DRAGGABLE} instead
   */
  protected static final String CSS_DRAGGABLE;

  /**
   * @deprecated Use {@link #PRIVATE_CSS_DRAGGING} instead
   */
  protected static final String CSS_DRAGGING;

  /**
   * @deprecated Use {@link #PRIVATE_CSS_HANDLE} instead
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

  private BoundaryDropController boundaryDropController;
  private AbsolutePanel boundaryPanel;
  private Widget draggable;
  private DragHandlerCollection dragHandlers;
  private DropControllerCollection dropControllerCollection;
  private ArrayList dropControllerList = new ArrayList();
  private MouseDragHandler mouseDragHandler;
  private boolean multipleSelectionAllowed;
  private final List selectedWidgetList = new ArrayList();
  private TargetSelectionMethod targetSelectionMethod;

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
    boundaryDropController = newBoundaryDropController(boundaryPanel, allowDroppingOnBoundaryPanel);
    registerDropController(boundaryDropController);
    mouseDragHandler = new MouseDragHandler(this);
    setBehaviorTargetSelection(TargetSelectionMethod.MOUSE_POSITION);
  }

  public final void addDragHandler(DragHandler handler) {
    if (dragHandlers == null) {
      dragHandlers = new DragHandlerCollection();
    }
    dragHandlers.add(handler);
  }

  public void clearSelection() {
    for (Iterator iterator = selectedWidgetList.iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();
      widget.removeStyleName(CSS_SELECTED);
      iterator.remove();
    }
  }

  public void dragEnd(Widget draggable, Widget dropTarget) {
    draggable.removeStyleName(PRIVATE_CSS_DRAGGING);
  }

  public Widget dragStart(Widget draggable) {
    this.draggable = draggable;
    resetCache();
    if (dragHandlers != null) {
      dragHandlers.fireDragStart(draggable);
    }
    draggable.addStyleName(PRIVATE_CSS_DRAGGING);
    return draggable;
  }

  public boolean getBehaviorBoundaryPanelDrop() {
    return boundaryDropController.getBehaviorBoundaryPanelDrop();
  }

  public boolean getBehaviorConstrainedToBoundaryPanel() {
    return mouseDragHandler.isConstrainedToBoundaryPanel();
  }

  public boolean getBehaviorMultipleSelection() {
    return multipleSelectionAllowed;
  }

  public TargetSelectionMethod getBehaviorTargetSelection() {
    return targetSelectionMethod;
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

  public DropController getIntersectDropController(Widget widget, int x, int y) {
    DropController dropController = dropControllerCollection.getIntersectDropController(widget, x, y, boundaryPanel);
    return dropController != null ? dropController : boundaryDropController;
  }

  public final Widget getMovableWidget() {
    throw new UnsupportedOperationException();
  }

  public Collection getSelectedWidgets() {
    return selectedWidgetList;
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
    dropControllerList.add(dropController);
  }

  public final void removeDragHandler(DragHandler handler) {
    if (dragHandlers != null) {
      dragHandlers.remove(handler);
    }
  }

  public void resetCache() {
    dropControllerCollection.resetCache(boundaryPanel, draggable);
  }

  public void setBehaviorBoundaryPanelDrop(boolean allowDroppingOnBoundaryPanel) {
    boundaryDropController.setBehaviorBoundaryPanelDrop(allowDroppingOnBoundaryPanel);
  }

  public void setBehaviorConstrainedToBoundaryPanel(boolean constrainedToBoundaryPanel) {
    mouseDragHandler.setConstrainedToBoundaryPanel(constrainedToBoundaryPanel);
  }

  public void setBehaviorMultipleSelection(boolean multipleSelectionAllowed) {
    this.multipleSelectionAllowed = multipleSelectionAllowed;
    clearSelection();
  }

  public void setBehaviorTargetSelection(TargetSelectionMethod targetSelectionMethod) {
    this.targetSelectionMethod = targetSelectionMethod;
    if (targetSelectionMethod == TargetSelectionMethod.WIDGET_CENTER) {
      dropControllerCollection = new WidgetCenterDropControllerCollection(dropControllerList);
    } else if (targetSelectionMethod == TargetSelectionMethod.MOUSE_POSITION) {
      dropControllerCollection = new MousePositionDropControllerCollection(dropControllerList);
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void setConstrainWidgetToBoundaryPanel(boolean constrainWidgetToBoundaryPanel) {
    setBehaviorConstrainedToBoundaryPanel(constrainWidgetToBoundaryPanel);
  }

  public void toggleSelection(Widget draggable) {
    if (selectedWidgetList.remove(draggable)) {
      draggable.removeStyleName(CSS_SELECTED);
    } else if (multipleSelectionAllowed) {
      selectedWidgetList.add(draggable);
      draggable.addStyleName(CSS_SELECTED);
    } else {
      clearSelection();
      selectedWidgetList.add(draggable);
    }
  }

  public void unregisterDropController(DropController dropController) {
    dropControllerList.remove(dropController);
  }

  /**
   * @deprecated Never a part of the released gwt-dnd API; use {@link #newBoundaryDropController(AbsolutePanel, boolean)} instead.
   */
  protected final BoundaryDropController newBoundaryDropController() {
    throw new UnsupportedOperationException();
  }

  /**
   * Create a new BoundaryDropController to manage our boundary panel as a drop
   * target. To ensure that draggable widgets can only be dropped on registered
   * drop targets, set <code>allowDroppingOnBoundaryPanel</code> to <code>false</code>.
   *
   * @param boundaryPanel the panel to which our drag-and-drop operations are constrained
   * @param allowDroppingOnBoundaryPanel whether or not dropping is allowed on the boundary panel
   * @return the new BoundaryDropController
   */
  protected BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel, boolean allowDroppingOnBoundaryPanel) {
    return new BoundaryDropController(boundaryPanel, allowDroppingOnBoundaryPanel);
  }

  /**
  * @deprecated Method has been replaced by {@link PickupDragController#restoreSelectedWidgetsLocation()}
  */
  protected final void restoreDraggableLocation(Widget draggable) {
    throw new UnsupportedOperationException();
  }

  /**
   * @deprecated Method has been moved down to {@link PickupDragController#restoreSelectedWidgetsStyle()}
   */
  protected final void restoreDraggableStyle(Widget draggable) {
    throw new UnsupportedOperationException();
  }

  /**
   * @deprecated Method has been moved down to {@link PickupDragController#saveSelectedWidgetsLocationAndStyle()}
   */
  protected final void saveDraggableLocationAndStyle(Widget draggable) {
    throw new UnsupportedOperationException();
  }
}