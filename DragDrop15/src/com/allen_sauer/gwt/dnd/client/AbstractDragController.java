/*
 * Copyright 2008 Fred Sauer
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

import java.util.HashMap;
import java.util.Iterator;

import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/*
 * {@link DragController} which performs the bare essentials such as
 * adding/removing styles, maintaining collections, adding mouse listeners, etc.
 * 
 * <p> Extend this class to implement specialized drag capabilities such table
 * column or panel resizing. For classic drag-and-drop functionality, i.e. the
 * ability to pickup, move around and drop widgets, use {@link
 * PickupDragController}. </p>
 */
public abstract class AbstractDragController implements DragController {

  private static final String CSS_SELECTED = "dragdrop-selected";

  private static HashMap<Widget, Widget> dragHandles = new HashMap<Widget, Widget>();

  private static final String PRIVATE_CSS_DRAGGABLE = "dragdrop-draggable";

  private static final String PRIVATE_CSS_DRAGGING = "dragdrop-dragging";

  private static final String PRIVATE_CSS_HANDLE = "dragdrop-handle";

  static {
    setVersion();
  }

  private static native void setVersion()
  /*-{
    $wnd.$GWT_DND_VERSION = "@GWT_DND_VERSION@";
  }-*/;

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
   * The drag controller's drag context.
   */
  protected final DragContext context;

  /**
   * The current drag end event, created in {@link #previewDragEnd()}
   * and returned a second time in {@link #dragEnd()}.
   */
  private DragEndEvent dragEndEvent;

  /**
   * Collection of registered drag handlers.
   */
  private DragHandlerCollection dragHandlers;

  /**
   * The current drag start event, created in {@link #previewDragStart()}
   * and returned a second time in {@link #dragStart()}.
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
   * Create a new drag-and-drop controller. Drag operations will be limited to
   * the specified boundary panel.
   * 
   * @param boundaryPanel the desired boundary panel or <code>RootPanel.get()</code>
   *                      if entire document body is to be the boundary
   */
  public AbstractDragController(AbsolutePanel boundaryPanel) {
    assert boundaryPanel != null : "Use 'RootPanel.get()' instead of 'null'.";
    this.boundaryPanel = boundaryPanel;
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
    for (Iterator<Widget> iterator = context.selectedWidgets.iterator(); iterator.hasNext();) {
      Widget widget = iterator.next();
      widget.removeStyleName(CSS_SELECTED);
      iterator.remove();
    }
  }

  public void dragEnd() {
    context.draggable.removeStyleName(PRIVATE_CSS_DRAGGING);
    if (dragHandlers != null) {
      dragHandlers.fireDragEnd(dragEndEvent);
      dragEndEvent = null;
    }
    assert dragEndEvent == null;
  }

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
    context.draggable.addStyleName(PRIVATE_CSS_DRAGGING);
    assert dragStartEvent == null;
  }

  public boolean getBehaviorCancelDocumentSelections() {
    return cancelDocumentSelections;
  }

  public boolean getBehaviorConstrainedToBoundaryPanel() {
    return constrainedToBoundaryPanel;
  }

  public int getBehaviorDragStartSensitivity() {
    return dragStartSensitivityPixels;
  }

  public boolean getBehaviorMultipleSelection() {
    return multipleSelectionAllowed;
  }

  public final AbsolutePanel getBoundaryPanel() {
    return boundaryPanel;
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
    Widget dragHandle = dragHandles.remove(draggable);
    mouseDragHandler.makeNotDraggable(dragHandle);
    draggable.removeStyleName(PRIVATE_CSS_DRAGGABLE);
    dragHandle.removeStyleName(PRIVATE_CSS_HANDLE);
  }

  public void previewDragEnd() throws VetoDragException {
    assert dragEndEvent == null;
    if (dragHandlers != null) {
      dragEndEvent = new DragEndEvent(context);
      dragHandlers.firePreviewDragEnd(dragEndEvent);
    }
  }

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

  public final void removeDragHandler(DragHandler handler) {
    if (dragHandlers != null) {
      dragHandlers.remove(handler);
    }
  }

  public void resetCache() {
  }

  public void setBehaviorCancelDocumentSelections(boolean cancelDocumentSelections) {
    this.cancelDocumentSelections = cancelDocumentSelections;
  }

  public void setBehaviorConstrainedToBoundaryPanel(boolean constrainedToBoundaryPanel) {
    this.constrainedToBoundaryPanel = constrainedToBoundaryPanel;
  }

  public void setBehaviorDragStartSensitivity(int pixels) {
    assert pixels >= 0;
    dragStartSensitivityPixels = pixels;
  }

  public void setBehaviorMultipleSelection(boolean multipleSelectionAllowed) {
    this.multipleSelectionAllowed = multipleSelectionAllowed;
    for (Iterator<Widget> iterator = context.selectedWidgets.iterator(); iterator.hasNext();) {
      Widget widget = iterator.next();
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
}