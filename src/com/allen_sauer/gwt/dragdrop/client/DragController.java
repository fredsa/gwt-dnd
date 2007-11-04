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
 * Common interface which all drag controllers much implement.
 * 
 * <p>
 * Be sure to also create one or more
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.DropController DropControllers}
 * and {@link #registerDropController(DropController) register} them.
 * </p>
 */
public interface DragController extends FiresDragEvents {
  /**
   * Drop target selection behavior.
   * 
   * TODO Convert to enum for JDK 1.5
   */
  public static class TargetSelectionMethod {
    public static final TargetSelectionMethod MOUSE_POSITION = new TargetSelectionMethod();
    public static final TargetSelectionMethod WIDGET_CENTER = new TargetSelectionMethod();

    /**
     * Prevent class instantiation.
     */
    private TargetSelectionMethod() {
    }
  }

  /**
   * Register a drag handler which will listen for
   * {@link DragStartEvent DragStartEvents} and
   * and {@link DragEndEvent DragEndEvents}.
   * 
   * @see #removeDragHandler(DragHandler)
   */
  void addDragHandler(DragHandler handler);

  /**
   * Callback method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @param dropTarget widget on which draggable was dropped. <code>null</code>
   *            if drag was cancelled
   */
  void dragEnd(Widget draggable, Widget dropTarget);

  /**
   * Callback method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   */
  void dragStart(Widget draggable);

  /**
   * Whether or not dropping on the boundary panel is permitted.
   * 
   * @return <code>true</code> if dropping on the boundary panel is allowed
   */
  boolean getBehaviorBoundaryPanelDrop();

  /**
   * Determine whether or not drag operations are constrained to the boundary panel.
   * 
  * @return <code>true</code> if drags are constrained to the boundary panel
  */
  boolean getBehaviorConstrainedToBoundaryPanel();

  /**
   * Determine the current drop target selection method for this drag controller.
   * 
   * @return the current selection method
   */
  TargetSelectionMethod getBehaviorTargetSelection();

  /**
   * Get the boundary panel provided in the constructor.
   * 
   * @return the AbsolutePanel provided in the constructor
   */
  AbsolutePanel getBoundaryPanel();

  /**
   * Callback method for {@link MouseDragHandler} to determine
   * which DropController represents the deepest DOM descendant
   * drop target located at the provided location (x, y) or
   * which suitably intersects with <code>widget</code>.
   * 
   * @param widget draggable or its proxy widget
   * @param x offset left relative to document body
   * @param y offset top relative to document body
   * 
   * @return the responsible DropController
   */
  DropController getIntersectDropController(Widget widget, int x, int y);

  /**
   * Callback method for {@link MouseDragHandler} to determine the
   * container widget that will move as part of the drag operation.
   * This may be the actual draggable widget, an appropriate drag proxy
   * widget, or a wrapper widget.
   * 
   * @return the movable container widget
   */
  Widget getMovableWidget();

  /**
   * Enable dragging on widget. Call this method for each widget that
   * you would like to make draggable under this drag controller.
   * 
   * @param widget the widget to be made draggable
   */
  void makeDraggable(Widget widget);

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
   * Callback method for {@link MouseDragHandler}.
   * 
   * @param dragEndEvent the event
   */
  void notifyDragEnd(DragEndEvent dragEndEvent);

  /**
   * Callback method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  void previewDragEnd(Widget draggable, Widget dropTarget) throws VetoDragException;

  /**
   * Callback method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  void previewDragStart(Widget draggable) throws VetoDragException;

  /**
   * Register a new DropController, representing a new drop target, with this
   * drag controller.
   * 
   * @see #unregisterDropController(DropController)
   * 
   * @param dropController the controller to register
   */
  void registerDropController(DropController dropController);

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
   * Set whether or not widgets may be dropped anywhere on the boundary panel.
   * Set to <code>false</code> when you only want explicitly registered drop
   * controllers to accept drops. Defaults to <code>true</code>.
   * 
   * @param allowDroppingOnBoundaryPanel <code>true</code> to allow dropping
   */
  void setBehaviorBoundaryPanelDrop(boolean allowDroppingOnBoundaryPanel);

  /**
   * Set whether or not movable widget is to be constrained to the boundary panel
   * during dragging. The default is not to constrain the draggable or drag proxy.
   * 
   * @param constrainedToBoundaryPanel whether or not to constrain to the boundary panel
   */
  void setBehaviorConstrainedToBoundaryPanel(boolean constrainedToBoundaryPanel);

  /**
   * Set the drop target selection method for this drag controller.
   * Defaults to {@link TargetSelectionMethod#MOUSE_POSITION}.
   * 
   * @param targetSelectionMethod the selection method
   */
  void setBehaviorTargetSelection(TargetSelectionMethod targetSelectionMethod);

  /**
   * Unregister a DropController from this drag controller.
   * 
   * @see #registerDropController(DropController)
   * 
   * @param dropController the controller to register
   */
  void unregisterDropController(DropController dropController);
}