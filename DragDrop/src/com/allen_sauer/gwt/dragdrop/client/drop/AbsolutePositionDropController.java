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

import com.allen_sauer.gwt.dragdrop.client.AbsolutePositionDragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.DOMUtil;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

/**
 * A {@link DropController} which allows a draggable widget to be placed at
 * specific (absolute) locations on an
 * {@link com.google.gwt.user.client.ui.AbsolutePanel} drop target.
 */
public class AbsolutePositionDropController extends AbstractPositioningDropController {
  protected AbsolutePanel currentBoundaryPanel;

  private Location dropLocation;
  private final AbsolutePanel dropTarget;

  public AbsolutePositionDropController(AbsolutePanel dropTarget) {
    super(dropTarget);
    this.dropTarget = dropTarget;
  }

  /**
   * Programmatically drop a widget on our drop target while obeying the
   * constraints of this controller.
   * 
   * @param widget the widget to be dropped
   * @param left the desired absolute horizontal location relative to our drop
   *            target
   * @param top the desired absolute vertical location relative to our drop
   *            target
   */
  public void drop(Widget widget, int left, int top) {
    dropTarget.add(widget, left, top);
    constrainedWidgetMove(widget, widget, widget);
  }

  public String getDropTargetStyleName() {
    return super.getDropTargetStyleName() + " dragdrop-absolute-positioning-drop-target";
  }

  public DragEndEvent onDrop(Widget reference, Widget draggable, DragController dragController) {
    // constrain the position before creating the DragEndEvent
    dropTarget.add(draggable, dropLocation.getLeft(), dropLocation.getTop());

    DragEndEvent event = super.onDrop(reference, draggable, dragController);
    return event;
  }

  public void onEnter(Widget reference, Widget draggable, DragController dragController) {
    super.onEnter(reference, draggable, dragController);
    currentBoundaryPanel = dragController.getBoundaryPanel();
  }

  public void onLeave(Widget draggable, DragController dragController) {
    super.onLeave(draggable, dragController);
    currentBoundaryPanel = null;
  }

  public void onMove(int x, int y, Widget reference, Widget draggable, DragController dragController) {
    super.onMove(x, y, reference, draggable, dragController);
    constrainedWidgetMove(reference, draggable, getPositioner());
  }

  public void onPreviewDrop(Widget reference, Widget draggable, DragController dragController) throws VetoDropException {
    super.onPreviewDrop(reference, draggable, dragController);
    dropLocation = getConstrainedLocation(reference, draggable, draggable);
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
   * @return location where widget can be placed or null if no compatible
   *         location found
   */
  protected Location getConstrainedLocation(Widget reference, Widget draggable, Widget widget) {
    Area referenceArea = new WidgetArea(reference, currentBoundaryPanel);
    WidgetLocation referenceLocation = new WidgetLocation(reference, dropTarget);
    referenceLocation.constrain(0, 0, DOMUtil.getClientWidth(dropTarget.getElement()) - referenceArea.getWidth(),
        DOMUtil.getClientHeight(dropTarget.getElement()) - referenceArea.getHeight());
    return referenceLocation;
  }

  protected DragEndEvent makeDragEndEvent(Widget reference, Widget draggable, DragController dragController) {
    Location location = new WidgetLocation(draggable, dropTarget);
    return new AbsolutePositionDragEndEvent(draggable, dropTarget, location.getLeft(), location.getTop());
  }

  private void constrainedWidgetMove(Widget reference, Widget draggable, Widget widget) {
    Location location = getConstrainedLocation(reference, draggable, widget);
    if (location != null) {
      dropTarget.add(widget, location.getLeft(), location.getTop());
    }
  }
}
