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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.AbsolutePositionDragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.DragContext;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.util.DOMUtil;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

import java.util.HashMap;
import java.util.Iterator;

/**
 * A {@link DropController} which allows a draggable widget to be placed at
 * specific (absolute) locations on an
 * {@link com.google.gwt.user.client.ui.AbsolutePanel} drop target.
 */
public class AbsolutePositionDropController extends AbstractPositioningDropController {
  private static final Widget helperWidget = new SimplePanel();
  private AbsolutePanel currentBoundaryPanel;
  private final HashMap dropLocations = new HashMap();

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

  public DragEndEvent onDrop(DragContext context) {
    for (Iterator iterator = context.selectedWidgets.iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();
      Location dropLocation = (Location) dropLocations.get(widget);

      dropTarget.add(widget, dropLocation.getLeft(), dropLocation.getTop());
    }
    dropLocations.clear();

    // constrain the position before creating the DragEndEvent
    DragEndEvent event = super.onDrop(context);
    return event;
  }

  public void onEnter(DragContext context) {
    super.onEnter(context);
    currentBoundaryPanel = context.dragController.getBoundaryPanel();
  }

  public void onLeave(DragContext context) {
    super.onLeave(context);
    currentBoundaryPanel = null;
  }

  public void onMove(DragContext context) {
    super.onMove(context);
    constrainedWidgetMove(context.movableWidget, context.draggable, getPositioner());
  }

  public void onPreviewDrop(DragContext context) throws VetoDropException {
    super.onPreviewDrop(context);

    WidgetLocation referenceLocation = new WidgetLocation(context.movableWidget, currentBoundaryPanel);

    // temporarily store widget drop location for use in onDrop()
    dropLocations.clear();

    for (Iterator iterator = context.selectedWidgets.iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();
      WidgetLocation relativeLocation = new WidgetLocation(widget, context.draggable);

      // Use helper widget to determine constrained location
      currentBoundaryPanel.add(helperWidget, referenceLocation.getLeft() + relativeLocation.getLeft(), referenceLocation.getTop()
          + relativeLocation.getTop());
      helperWidget.setPixelSize(widget.getOffsetWidth(), widget.getOffsetHeight());
      Location dropLocation = getConstrainedLocation(helperWidget, widget, widget);
      helperWidget.removeFromParent();
      if (dropLocation == null) {
        throw new VetoDropException();
      }
      dropLocations.put(widget, dropLocation);
    }
  }

  /**
   * If possible, move widget as close to the desired reference location as the
   * constraints of this DropController allow.
   * 
   * @param reference widget whose location is the desired drop location
   * @param draggable actual draggable widget
   * @param widget positioner or the draggable widget to be moved
   * @return location where widget can be placed or <code>null</code> if no compatible
   *         location found
   */
  protected Location getConstrainedLocation(Widget reference, Widget draggable, Widget widget) {
    WidgetLocation referenceLocation = new WidgetLocation(reference, dropTarget);
    referenceLocation.constrain(0, 0, DOMUtil.getClientWidth(dropTarget.getElement()) - reference.getOffsetWidth(),
        DOMUtil.getClientHeight(dropTarget.getElement()) - reference.getOffsetHeight());
    return referenceLocation;
  }

  protected DragEndEvent makeDragEndEvent(DragContext context) {
    Location location = new WidgetLocation(context.draggable, dropTarget);
    return new AbsolutePositionDragEndEvent(context, location.getLeft(), location.getTop());
  }

  private void constrainedWidgetMove(Widget reference, Widget draggable, Widget widget) {
    Location location = getConstrainedLocation(reference, draggable, widget);
    if (location != null) {
      dropTarget.add(widget, location.getLeft(), location.getTop());
    }
  }
}
