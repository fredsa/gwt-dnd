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
package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.AbstractDragController;
import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

/**
 * A {@link DropController} which allows a draggable widget to be placed at
 * specific (absolute) locations on an {@link com.google.gwt.user.client.ui.AbsolutePanel}
 * drop target.
 */
public class AbsolutePositionDropController extends AbstractPositioningDropController {

  private AbsolutePanel dropTarget;
  private Location dropLocation;

  public AbsolutePositionDropController(AbsolutePanel dropTarget) {
    super(dropTarget);
    this.dropTarget = dropTarget;
  }

  /**
   * Programmatically drop a widget on our drop target while obeying the constraints
   * of this controller.
   * 
   * @param widget the widget to be dropped
   * @param left the desired absolute horizontal location relative to our drop target
   * @param top the desired absolute vertical location relative to our drop target
   */
  public void drop(Widget widget, int left, int top) {
    DragController dragController = AbstractDragController.getDragController(widget);
    Location location = new Location(dropTarget, dragController.getBoundaryPanel());
    dragController.getBoundaryPanel().add(widget, location.getLeft() + left, location.getTop() + top);
    constrainedWidgetMove(widget, widget, widget, dragController);
  }

  public String getDropTargetStyleName() {
    return super.getDropTargetStyleName() + " dragdrop-absolute-positioning-drop-target";
  }

  public void onDrop(Widget reference, Widget draggable, DragController dragController) {
    super.onDrop(reference, draggable, dragController);
    dropTarget.add(draggable, dropLocation.getLeft(), dropLocation.getTop());
  }

  public void onLeave(Widget draggable, DragController dragController) {
    super.onLeave(draggable, dragController);
  }

  public void onMove(Widget reference, Widget draggable, DragController dragController) {
    super.onMove(reference, draggable, dragController);
    constrainedWidgetMove(reference, draggable, getPositioner(), dragController);
  }

  public void onPreviewDrop(Widget reference, Widget draggable, DragController dragController) throws VetoDropException {
    super.onPreviewDrop(reference, draggable, dragController);
    dropLocation = getConstrainedLocation(reference, draggable, draggable, dragController);
    if (dropLocation == null) {
      throw new VetoDropException();
    }
  }

  /**
   * If possible, move widget as close to the desired reference location as the
   * constraints of this DropController allow.
   * 
   * @param reference widget whose location is the desired drop location
   * @param draggable actual draggable widget
   * @param widget positioner or the draggable widget to be moved
   * @param dragController the DragController for this operation
   * @return location where widget can be placed or null if no compatible location found
   */
  protected Location getConstrainedLocation(Widget reference, Widget draggable, Widget widget, DragController dragController) {
    AbsolutePanel boundaryPanel = dragController.getBoundaryPanel();
    Area dropArea = new Area(dropTarget, boundaryPanel);
    Area draggableArea = new Area(reference, boundaryPanel);
    Location location = new Location(reference, dropTarget);
    location.constrain(0, 0, dropArea.getInternalWidth() - draggableArea.getWidth(), dropArea.getInternalHeight()
        - draggableArea.getHeight());
    return location;
  }

  private void constrainedWidgetMove(Widget reference, Widget draggable, Widget widget, DragController dragController) {
    Location location = getConstrainedLocation(reference, draggable, widget, dragController);
    if (location != null) {
      dropTarget.add(widget, location.getLeft(), location.getTop());
    }
  }
}
