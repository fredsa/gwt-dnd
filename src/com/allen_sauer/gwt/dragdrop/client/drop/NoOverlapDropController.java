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

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.CoordinateLocation;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A {@link DropController} which constrains prevents draggable widgets from
 * overlapping.
 */
public class NoOverlapDropController extends AbsolutePositionDropController {

  /**
   * Helper class to hop through the provided int range using the halfway point
   * to speed traversal.
   */
  private static class TestRange {

    private int start;
    private int stopBefore;

    /**
     * @param start inclusive start of range
     * @param stopBefore non-inclusive end of range
     */
    public TestRange(int start, int stopBefore) {
      this.start = start;
      this.stopBefore = stopBefore;
    }

    public int getHalfway() {
      int halfway = (start + stopBefore) / 2;
      return halfway == stopBefore ? start : halfway;
    }

    public boolean hasMore() {
      int diff = start - stopBefore;
      return diff > 1 || diff < -1;
    }

    public void setStart(int start) {
      this.start = start;
    }

    public void setStopBefore(int stopBefore) {
      this.stopBefore = stopBefore;
    }

    public String toString() {
      return start + " through " + stopBefore + " non-inclusive";
    }
  }

  private Location lastGoodLocation;

  public NoOverlapDropController(AbsolutePanel dropTarget) {
    super(dropTarget);
  }

  public String getDropTargetStyleName() {
    return super.getDropTargetStyleName() + " dragdrop-no-overlap-drop-target";
  }

  public void onEnter(Widget reference, Widget draggable, DragController dragController) {
    super.onEnter(reference, draggable, dragController);
    lastGoodLocation = null;
  }

  protected Location getConstrainedLocation(Widget reference, Widget draggable, Widget widget) {
    Location location = internalGetConstrainedLocation(reference, draggable, widget);
    if (location != null) {
      lastGoodLocation = location;
    }
    return location;
  }

  private boolean collision(Collection widgets, Area area) {
    for (Iterator iterator = widgets.iterator(); iterator.hasNext();) {
      Widget w = (Widget) iterator.next();
      if ((new WidgetArea(w, getDropTargetInfo().getDropTarget())).intersects(area)) {
        return true;
      }
    }
    return false;
  }

  private Location findBetterLocation(Area referenceArea, Collection widgets) {
    Area tempReferenceArea = referenceArea.copyOf();
    Location newLocation = null;

    // see if there is an acceptable location horizontally closer to the
    // reference widget
    TestRange range = new TestRange(lastGoodLocation.getLeft(), referenceArea.getLeft());
    while (range.hasMore()) {
      int left = range.getHalfway();
      Location tempLocation = new CoordinateLocation(left, lastGoodLocation.getTop());
      tempReferenceArea.moveTo(tempLocation);
      // TODO consider only widgets in area between desired and known-good positions
      if (collision(widgets, tempReferenceArea)) {
        range.setStopBefore(left);
      } else {
        newLocation = tempLocation;
        range.setStart(left);
      }
    }

    // now try vertically closer to the reference widget
    Location startLocation = newLocation != null ? newLocation : lastGoodLocation;
    range = new TestRange(startLocation.getTop(), referenceArea.getTop());
    while (range.hasMore()) {
      int top = range.getHalfway();
      Location tempLocation = new CoordinateLocation(startLocation.getLeft(), top);
      tempReferenceArea.moveTo(tempLocation);
      // TODO consider only widgets in area between desired and known-good positions
      if (collision(widgets, tempReferenceArea)) {
        range.setStopBefore(top);
      } else {
        newLocation = tempLocation;
        range.setStart(top);
      }
    }

    return newLocation;
  }

  private Location internalGetConstrainedLocation(Widget reference, Widget draggable, Widget widget) {
    WidgetArea referenceArea = new WidgetArea(reference, getDropTargetInfo().getBoundaryPanel());
    WidgetLocation referenceLocation = new WidgetLocation(reference, getDropTargetInfo().getDropTarget());
    referenceLocation.constrain(0, 0, getDropTargetInfo().getDropAreaClientWidth() - referenceArea.getWidth(),
        getDropTargetInfo().getDropAreaClientHeight() - referenceArea.getHeight());
    // Determine where draggableArea would be if it were constrained to the
    // dropArea
    // Also causes draggableArea to become relative to dropTarget
    referenceArea.moveTo(referenceLocation);

    // determine our potential collision targets
    Collection collisionTargets = new ArrayList();
    for (Iterator iteartor = getDropTargetInfo().getDropTarget().iterator(); iteartor.hasNext();) {
      Widget w = (Widget) iteartor.next();
      if (w != reference && w != draggable && w != widget && w != getPositioner()) {
        collisionTargets.add(w);
      }
    }

    // test reference widget location for collisions
    if (!collision(collisionTargets, referenceArea)) {
      // no overlap; okay to move widget to new location
      return referenceLocation;
    }

    if (lastGoodLocation != null) {
      // attempt to determine location closer to the reference widget without
      // collisions
      Location newLocation = findBetterLocation(referenceArea, collisionTargets);

      if (newLocation != null) {
        // found a better location; move there
        return newLocation;
      }
      if (widget == draggable) {
        // on drop move to last good location
        return lastGoodLocation;
      }
    }
    // give up
    return null;
  }
}
