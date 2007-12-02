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
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.DropController;

/**
 * Common interface which all drag controllers must implement.
 */
public interface DragController extends FiresDragEvents {
  /**
   * Register a drag handler which will listen for
   * {@link DragStartEvent DragStartEvents} and
   * and {@link DragEndEvent DragEndEvents}.
   * 
   * @see #removeDragHandler(DragHandler)
   */
  void addDragHandler(DragHandler handler);

  /**
   * All currently selected widgets are deselected.
   */
  void clearSelection();

  /**
   * Callback method for {@link MouseDragHandler}.
   */
  void dragEnd();

  /**
   * @deprecated Use {@link #dragEnd()} instead.
   */
  void dragEnd(Widget draggable, Widget dropTarget);

  /**
   * Callback method for {@link MouseDragHandler}.
   */
  void dragMove();

  /**
  * Callback method for {@link MouseDragHandler} when a drag operation
  * is initiated for this drag controller.
  */
  void dragStart();

  /**
   * @deprecated Used {@link #dragStart()} instead.
   */
  void dragStart(Widget draggable);

  /**
   * Determine whether or not drag operations are constrained to the boundary panel.
   * 
  * @return <code>true</code> if drags are constrained to the boundary panel
  */
  boolean getBehaviorConstrainedToBoundaryPanel();

  /**
   * Gets the number of pixels the mouse must be moved to initiate a drag operation.
   * 
   * @return number of pixels or <code>0</code> (zero) if mouse down starts the drag
   */
  int getBehaviorDragStartSensitivity();

  /**
   * Determines whether multiple widget selection behavior is enabled.
   * 
   * @return <code>true</code> if multiple widget selection behavior is enabled
   */
  boolean getBehaviorMultipleSelection();

  /**
   * Get the boundary panel provided in the constructor.
   * 
   * @return the AbsolutePanel provided in the constructor
   */
  AbsolutePanel getBoundaryPanel();

  /**
   * @deprecated No longer a part of the API.
  */
  DropControllerCollection getDropControllerCollection();

  /**
   * @deprecated No longer a part of the API.
   */
  DropController getIntersectDropController(Widget widget);

  /**
   * @deprecated No longer a part of the API.
   */
  DropController getIntersectDropController(Widget widget, int x, int y);

  /**
  * @deprecated The movable panel is now returned by {@link #dragStart(Widget)}.
  */
  Widget getMovableWidget();

  /**
   * Enable dragging on widget. Call this method for each widget that
   * you would like to make draggable under this drag controller.
   * 
   * @param draggable the widget to be made draggable
   */
  void makeDraggable(Widget draggable);

  /**
   * Enable dragging on widget, specifying the child widget serving as a
   * drag handle.
   * 
   * @param draggable the widget to be made draggable
   * @param dragHandle the widget by which widget can be dragged
   */
  void makeDraggable(Widget draggable, Widget dragHandle);

  /**
   * Performs the reverse of {@link #makeDraggable(Widget)}, making the widget
   * no longer draggable by this drag controller.
   * 
   * @param widget the widget which should no longer be draggable
   */
  void makeNotDraggable(Widget widget);

  /**
   * @deprecated No longer a part of the API.
   */
  void notifyDragEnd(DragEndEvent dragEndEvent);

  /**
   * Callback method for {@link MouseDragHandler}.
   * 
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  void previewDragEnd() throws VetoDragException;

  /**
   * @deprecated Used {@link #previewDragEnd()} instead.
   */
  void previewDragEnd(Widget draggable, Widget dropTarget) throws VetoDragException;

  /**
   * Callback method for {@link MouseDragHandler}.
   * 
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  void previewDragStart() throws VetoDragException;

  /**
   * @deprecated Used {@link #previewDragStart()} instead.
   */
  void previewDragStart(Widget draggable) throws VetoDragException;

  /**
   * Unregister drag handler.
   * 
   * @see #addDragHandler(DragHandler)
   */
  void removeDragHandler(DragHandler handler);

  /**
   * Reset the internal drop controller (drop target) cache which is initialized by
   * {{@link #dragStart(Widget)}. This method should be called when a drop target's
   * size and/or location changes, or when drop target eligibility changes.
   */
  void resetCache();

  /**
   * Set whether or not movable widget is to be constrained to the boundary panel
   * during dragging. The default is not to constrain the draggable or drag proxy.
   * 
   * @param constrainedToBoundaryPanel whether or not to constrain to the boundary panel
   */
  void setBehaviorConstrainedToBoundaryPanel(boolean constrainedToBoundaryPanel);

  /**
   * Sets the number of pixels the mouse must be moved in either horizontal or vertical
   * direction in order to initiate a drag operation. Defaults to <code>0</code> (zero).
   * 
   * @param pixels number of pixels or <code>0</code> (zero) to start dragging on mouse down
   */
  void setBehaviorDragStartSensitivity(int pixels);

  /**
   * Sets whether multiple widgets can be selected for dragging at one time via
   * <code>CTRL</code>/<code>META</code>-click. Defaults to <code>true</code>.
   * 
   * @param multipleSelectionAllowed whether multiple selections are enabled
   */
  void setBehaviorMultipleSelection(boolean multipleSelectionAllowed);

  /**
  * @deprecated Use {@link #setBehaviorConstrainedToBoundaryPanel(boolean)} instead.
  */
  void setConstrainWidgetToBoundaryPanel(boolean constrainWidgetToBoundaryPanel);

  /**
   * Toggle the selection of the specified widget.
   * 
   * @param draggable the widget whose selection is to be toggled
   */
  void toggleSelection(Widget draggable);
}