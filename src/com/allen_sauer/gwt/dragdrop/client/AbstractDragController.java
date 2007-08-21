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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;
import com.allen_sauer.gwt.dragdrop.client.util.DOMUtil;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

import java.util.HashMap;

/**
 * {@link DragController} which performs the bare essentials such as
 * adding/removing styles, maintaining collections, adding mouse
 * listeners, etc.
 * 
 * <p>Extend this class to implement specialized drag capabilities such table column
 * or panel resizing. For classic drag-and-drop functionality, i.e. the ability to
 * pickup, move around and drop widgets, use {@link PickupDragController} instead.</p>
 */
public abstract class AbstractDragController implements DragController {
  protected static final String CSS_DRAGGABLE = "dragdrop-draggable";
  protected static final String CSS_DRAGGING = "dragdrop-dragging";
  protected static final String CSS_HANDLE = "dragdrop-handle";

  private static HashMap dragHandles = new HashMap();

  private BoundaryDropController boundaryDropController;
  private AbsolutePanel boundaryPanel;
  private DragHandlerCollection dragHandlers;
  private DropControllerCollection dropControllerCollection = getDropControllerCollection();
  private int initialDraggableIndex;
  private String initialDraggableMargin;
  private Widget initialDraggableParent;
  private Location initialDraggableParentLocation;
  private MouseDragHandler mouseDragHandler;

  /**
   * Create a new drag-and-drop controller. Drag operations will be limited to the
   * specified boundary panel.
   * 
   * <p>Note: An implicit {@link BoundaryDropController} is created and 
   * registered automatically.</p>
   * 
   * @param boundaryPanel the desired boundary panel or null if entire page is to be included
   * @param allowDroppingOnBoundaryPanel whether or not boundary panel should allow dropping
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

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @param dropTarget widget on which draggable was dropped. 
   *        <code>null</code> if drag was cancelled
   */
  public void dragEnd(Widget draggable, Widget dropTarget) {
    draggable.removeStyleName(CSS_DRAGGING);
  }

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   */
  public void dragStart(Widget draggable) {
    dropControllerCollection.resetCache(getBoundaryPanel(), draggable);
    if (dragHandlers != null) {
      dragHandlers.fireDragStart(draggable);
    }
    draggable.addStyleName(CSS_DRAGGING);
  }

  public final AbsolutePanel getBoundaryPanel() {
    return boundaryPanel;
  }

  public DropController getIntersectDropController(Widget widget) {
    DropController dropController = dropControllerCollection.getIntersectDropController(widget, boundaryPanel);
    return dropController != null ? dropController : boundaryDropController;
  }

  /**
   * Attaches a {@link MouseDragHandler} (which is a
   * {@link com.google.gwt.user.client.ui.MouseListener}) to the widget,
   * applies the {@link #CSS_DRAGGABLE} style to the draggable,
   * applies the {@link #CSS_HANDLE} style to the handle.
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
   * Similar to {@link #makeDraggable(Widget)}, but allow separate, child
   * to be specified as the drag handle by which the first widget can be dragged.
   * 
   * @param draggable the widget to be made draggable
   * @param dragHandle the widget by which widget can be dragged
   */
  public void makeDraggable(Widget draggable, Widget dragHandle) {
    mouseDragHandler.makeDraggable(draggable, dragHandle);
    draggable.addStyleName(CSS_DRAGGABLE);
    dragHandle.addStyleName(CSS_HANDLE);
    DOMUtil.preventSelection(dragHandle.getElement());
    dragHandles.put(draggable, dragHandle);
  }

  /**
   * Performs the reverse of {@link #makeDraggable(Widget)}, detaching the
   * {@link MouseDragHandler} from the widget and removing any styling
   * which was applied when making the widget draggable.
   * 
   * @param draggable the widget to no longer be draggable
   */
  public void makeNotDraggable(Widget draggable) {
    Widget dragHandle = (Widget) dragHandles.remove(draggable);
    mouseDragHandler.makeNotDraggable(dragHandle);
    draggable.removeStyleName(CSS_DRAGGABLE);
    dragHandle.removeStyleName(CSS_HANDLE);
  }

  public BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel, boolean allowDropping) {
    return new BoundaryDropController(boundaryPanel, allowDropping);
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

  public void restoreDraggableLocation(Widget draggable) {
    // TODO simplify after enhancement for issue 616
    // http://code.google.com/p/google-web-toolkit/issues/detail?id=616
    if (initialDraggableParent instanceof AbsolutePanel) {
      ((AbsolutePanel) initialDraggableParent).add(draggable, initialDraggableParentLocation.getLeft(),
          initialDraggableParentLocation.getTop());
    } else if (initialDraggableParent instanceof HorizontalPanel) {
      ((HorizontalPanel) initialDraggableParent).insert(draggable, initialDraggableIndex);
    } else if (initialDraggableParent instanceof VerticalPanel) {
      ((VerticalPanel) initialDraggableParent).insert(draggable, initialDraggableIndex);
    } else if (initialDraggableParent instanceof IndexedFlowPanel) {
      ((IndexedFlowPanel) initialDraggableParent).insert(draggable, initialDraggableIndex);
    } else {
      throw new RuntimeException("Unable to handle initialDraggableParent " + GWT.getTypeName(initialDraggableParent));
    }
  }

  public void restoreDraggableStyle(Widget draggable) {
    if (initialDraggableMargin != null && initialDraggableMargin.length() != 0) {
      DOM.setStyleAttribute(draggable.getElement(), "margin", initialDraggableMargin);
    }
  }

  public void saveDraggableLocationAndStyle(Widget draggable) {
    initialDraggableParent = draggable.getParent();

    // TODO simplify after enhancement for issue 616
    // http://code.google.com/p/google-web-toolkit/issues/detail?id=616
    if (initialDraggableParent instanceof AbsolutePanel) {
      initialDraggableParentLocation = new WidgetLocation(draggable, initialDraggableParent);
    } else if (initialDraggableParent instanceof HorizontalPanel) {
      initialDraggableIndex = ((HorizontalPanel) initialDraggableParent).getWidgetIndex(draggable);
    } else if (initialDraggableParent instanceof VerticalPanel) {
      initialDraggableIndex = ((VerticalPanel) initialDraggableParent).getWidgetIndex(draggable);
    } else if (initialDraggableParent instanceof IndexedFlowPanel) {
      initialDraggableIndex = ((IndexedFlowPanel) initialDraggableParent).getWidgetIndex(draggable);
    } else {
      throw new RuntimeException("Unable to handle initialDraggableParent " + GWT.getTypeName(initialDraggableParent));
    }
    initialDraggableMargin = DOM.getStyleAttribute(draggable.getElement(), "margin");
    if (initialDraggableMargin != null && initialDraggableMargin.length() != 0) {
      DOM.setStyleAttribute(draggable.getElement(), "margin", "0px");
    }
  }

  public void unregisterDropController(DropController dropController) {
    dropControllerCollection.remove(dropController);
  }

  /**
   * Allow subclasses ability to override implementing {@link DropContollerCollection}
   * to support custom intersection logic.
   * 
   * @return a new DropControllerCollection
   */
  protected DropControllerCollection getDropControllerCollection() {
    return new DropControllerCollection();
  }
}
