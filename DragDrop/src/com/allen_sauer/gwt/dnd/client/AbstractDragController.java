/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dnd.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.DragClientBundle;

import java.util.HashMap;
import java.util.Iterator;

/**
 * {@link DragController} which performs the bare essentials such as adding/removing styles,
 * maintaining collections, adding mouse listeners, etc.
 * 
 * <p> Extend this class to implement specialized drag capabilities such table column or panel
 * resizing. For classic drag-and-drop functionality, i.e. the ability to pickup, move around and
 * drop widgets, use {@link PickupDragController}. </p>
 */
public abstract class AbstractDragController implements DragController {
  // CHECKSTYLE_JAVADOC_OFF

  private HashMap<Widget, Widget> dragHandles = new HashMap<Widget, Widget>();

  /**
   * The drag controller's drag context.
   */
  protected final DragContext context;

  /**
   * The boundary panel to which all drag operations are constrained.
   */
  AbsolutePanel boundaryPanel;

  private boolean cancelDocumentSelections = true;

  /**
   * Whether or not widgets are physically constrained to the boundary panel.
   */
  private boolean constrainedToBoundaryPanel;

  /**
   * The current drag end event, created in {@link #previewDragEnd()} and returned a second time in
   * {@link #dragEnd()}.
   */
  private DragEndEvent dragEndEvent;

  /**
   * Collection of registered drag handlers.
   */
  private DragHandlerCollection dragHandlers;

  /**
   * The current drag start event, created in {@link #previewDragStart()} and returned a second time
   * in {@link #dragStart()}.
   */
  private DragStartEvent dragStartEvent;

  /**
   * Drag sensitivity in pixels.
   */
  private int dragStartSensitivityPixels;

  /**
   * This drag controller's mouse drag handler.
   */
  private MouseDragHandler mouseDragHandler;

  /**
   * Whether multiple selection behavior is enabled.
   */
  private boolean multipleSelectionAllowed = false;

  /**
   * Whether scrollIntoView() or it's equivalent is to be called during dragging.
   */
  private boolean scrollIntoView = true;

  /**
   * Create a new drag-and-drop controller. Drag operations will be limited to the specified
   * boundary panel.
   * 
   * @param boundaryPanel the desired boundary panel or <code>RootPanel.get()</code> if entire
   *          document body is to be the boundary
   */
  public AbstractDragController(AbsolutePanel boundaryPanel) {
    assert boundaryPanel != null : "Use 'RootPanel.get()' instead of 'null'.";
    this.boundaryPanel = boundaryPanel;
    context = new DragContext(this);
    mouseDragHandler = new MouseDragHandler(context);
  }

  @Override
  public final void addDragHandler(DragHandler handler) {
    if (dragHandlers == null) {
      dragHandlers = new DragHandlerCollection();
    }
    dragHandlers.add(handler);
  }

  @Override
  public void clearSelection() {
    for (Iterator<Widget> iterator = context.selectedWidgets.iterator(); iterator.hasNext();) {
      Widget widget = iterator.next();
      widget.removeStyleName(DragClientBundle.INSTANCE.css().selected());
      iterator.remove();
    }
  }

  @Override
  public void dragEnd() {
    context.draggable.removeStyleName(DragClientBundle.INSTANCE.css().dragging());
    if (dragHandlers != null) {
      dragHandlers.fireDragEnd(dragEndEvent);
      dragEndEvent = null;
    }
    assert dragEndEvent == null;
  }

  @Override
  public void dragStart() {
    if (!GWT.isScript()) {
      if (DOMUtil.getClientHeight(boundaryPanel.getElement()) == 0) {
        if (boundaryPanel.getElement().equals(RootPanel.getBodyElement())) {
          DOMUtil.reportFatalAndThrowRuntimeException("boundary panel (= the BODY element) has zero height;"
              + " dragging cannot occur inside an AbsolutePanel that has a height of zero pixels;"
              + " you can often remedy this quite easily by adding the following line of"
              + " CSS to your application's stylesheet:" + " BODY, HTML { height: 100%; }");
        }
      }
    }
    resetCache();
    if (dragHandlers != null) {
      dragHandlers.fireDragStart(dragStartEvent);
      dragStartEvent = null;
    }
    context.draggable.addStyleName(DragClientBundle.INSTANCE.css().dragging());
    assert dragStartEvent == null;
  }

  @Override
  public boolean getBehaviorCancelDocumentSelections() {
    return cancelDocumentSelections;
  }

  @Override
  public boolean getBehaviorConstrainedToBoundaryPanel() {
    return constrainedToBoundaryPanel;
  }

  @Override
  public int getBehaviorDragStartSensitivity() {
    return dragStartSensitivityPixels;
  }

  @Override
  public boolean getBehaviorMultipleSelection() {
    return multipleSelectionAllowed;
  }

  @Override
  public boolean getBehaviorScrollIntoView() {
    return scrollIntoView;
  }

  @Override
  public final AbsolutePanel getBoundaryPanel() {
    return boundaryPanel;
  }

  /**
   * Attaches a {@link MouseDragHandler} to the widget, applies styles to the draggable and the
   * handle.
   * 
   * @see #makeDraggable(Widget, Widget)
   * @see HasDragHandle
   * 
   * @param draggable the widget to be made draggable
   */
  @Override
  public void makeDraggable(Widget draggable) {
    if (draggable instanceof HasDragHandle) {
      makeDraggable(draggable, ((HasDragHandle) draggable).getDragHandle());
    } else {
      makeDraggable(draggable, draggable);
    }
  }

  /**
   * Similar to {@link #makeDraggable(Widget)}, but allow separate, child to be specified as the
   * drag handle by which the first widget can be dragged.
   * 
   * @param draggable the widget to be made draggable
   * @param dragHandle the widget by which widget can be dragged
   */
  @Override
  public void makeDraggable(Widget draggable, Widget dragHandle) {
    mouseDragHandler.makeDraggable(draggable, dragHandle);
    draggable.addStyleName(DragClientBundle.INSTANCE.css().draggable());
    dragHandle.addStyleName(DragClientBundle.INSTANCE.css().handle());
    dragHandles.put(draggable, dragHandle);
  }

  /**
   * Performs the reverse of {@link #makeDraggable(Widget)}, detaching the {@link MouseDragHandler}
   * from the widget and removing any styling which was applied when making the widget draggable.
   * 
   * @param draggable the widget to no longer be draggable
   */
  @Override
  public void makeNotDraggable(Widget draggable) {
    Widget dragHandle = dragHandles.remove(draggable);
    mouseDragHandler.makeNotDraggable(dragHandle);
    draggable.removeStyleName(DragClientBundle.INSTANCE.css().draggable());
    dragHandle.removeStyleName(DragClientBundle.INSTANCE.css().handle());
  }

  @Override
  public void previewDragEnd() throws VetoDragException {
    assert dragEndEvent == null;
    if (dragHandlers != null) {
      dragEndEvent = new DragEndEvent(context);
      dragHandlers.firePreviewDragEnd(dragEndEvent);
    }
  }

  @Override
  public void previewDragStart() throws VetoDragException {
    assert dragStartEvent == null;
    if (dragHandlers != null) {
      dragStartEvent = new DragStartEvent(context);
      try {
        dragHandlers.firePreviewDragStart(dragStartEvent);
      } catch (VetoDragException ex) {
        dragStartEvent = null;
        throw ex;
      }
    }
  }

  @Override
  public final void removeDragHandler(DragHandler handler) {
    if (dragHandlers != null) {
      dragHandlers.remove(handler);
    }
  }

  @Override
  public void resetCache() {
  }

  @Override
  public void setBehaviorCancelDocumentSelections(boolean cancelDocumentSelections) {
    this.cancelDocumentSelections = cancelDocumentSelections;
  }

  @Override
  public void setBehaviorConstrainedToBoundaryPanel(boolean constrainedToBoundaryPanel) {
    this.constrainedToBoundaryPanel = constrainedToBoundaryPanel;
  }

  @Override
  public void setBehaviorDragStartSensitivity(int pixels) {
    assert pixels >= 0;
    dragStartSensitivityPixels = pixels;
  }

  @Override
  public void setBehaviorMultipleSelection(boolean multipleSelectionAllowed) {
    this.multipleSelectionAllowed = multipleSelectionAllowed;
    for (Iterator<Widget> iterator = context.selectedWidgets.iterator(); iterator.hasNext();) {
      Widget widget = iterator.next();
      widget.removeStyleName(DragClientBundle.INSTANCE.css().selected());
      iterator.remove();
    }
  }

  @Override
  public void setBehaviorScrollIntoView(boolean scrollIntoView) {
    this.scrollIntoView = scrollIntoView;
  }

  public void setConstrainWidgetToBoundaryPanel(boolean constrainWidgetToBoundaryPanel) {
    setBehaviorConstrainedToBoundaryPanel(constrainWidgetToBoundaryPanel);
  }

  @Override
  public void toggleSelection(Widget draggable) {
    assert draggable != null;
    if (context.selectedWidgets.remove(draggable)) {
      draggable.removeStyleName(DragClientBundle.INSTANCE.css().selected());
    } else if (multipleSelectionAllowed) {
      context.selectedWidgets.add(draggable);
      draggable.addStyleName(DragClientBundle.INSTANCE.css().selected());
    } else {
      context.selectedWidgets.clear();
      context.selectedWidgets.add(draggable);
    }
  }
}