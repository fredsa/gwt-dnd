/*
 * Copyright 2006 Fred Sauer
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
import com.allen_sauer.gwt.dragdrop.client.util.Location;

import java.util.Iterator;

/**
 * A {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DropController} which
 * constrains prevents draggable widgets from overlapping.
 */
public class NoOverlapDropController extends AbsolutePositionDropController {

  /**
   * Helper class to iterate through the provided int range.
   */
  private class IntRangeIterator {

    private final int finish;
    private final int increment;
    private int pos;

    public IntRangeIterator(int start, int finish) {
      this.pos = start;
      this.finish = finish;
      this.increment = start < finish ? 1 : -1;
    }

    public boolean hasNext() {
      return this.pos != this.finish;
    }

    public int next() {
      this.pos += this.increment;
      return this.pos;
    }
  }

  private AbsolutePanel dropTargetPanel;

  public NoOverlapDropController(AbsolutePanel dropTargetPanel) {
    super(dropTargetPanel);
    this.dropTargetPanel = dropTargetPanel;
  }

  public String getDropTargetStyleName() {
    return super.getDropTargetStyleName() + " dragdrop-no-overlap-drop-target";
  }

  public void onEnter(Widget draggable, DragController dragController) {
    super.onEnter(draggable, dragController);
  }

  protected boolean constrainedWidgetMove(DragController dragController, Widget draggable, Widget widget) {
    AbsolutePanel boundryPanel = dragController.getBoundryPanel();
    Area dropArea = new Area(this.dropTargetPanel, boundryPanel);
    Area draggableArea = new Area(draggable, boundryPanel);
    Location location = new Location(draggable, this.dropTargetPanel);
    location.constrain(0, 0, dropArea.getInternalWidth() - draggableArea.getWidth(), dropArea.getInternalHeight()
        - draggableArea.getHeight());
    // Determine where draggableArea would be if it were constrained to the dropArea
    // Also causes draggableArea to become relative to dropTargetPanel
    draggableArea.moveTo(location);
    if (!collision(draggable, draggableArea)) {
      // no overlap; move widget to new location
      this.dropTargetPanel.add(widget, location.getLeft(), location.getTop());
      return true;
    }
    if (getPositioner().isAttached()) {
      Area dropTargetArea = new Area(this.dropTargetPanel, boundryPanel);
      Area positionerArea = new Area(getPositioner(), boundryPanel);
      if (dropTargetArea.contains(positionerArea)) {
        boundryPanel.add(getPositioner(), positionerArea.getLeft(), positionerArea.getTop());
        Location positionerLocation = new Location(getPositioner(), this.dropTargetPanel);
        Area tempDraggableArea = draggableArea.copyOf();
        Location newLocation = null;
        for (IntRangeIterator iterator = new IntRangeIterator(positionerLocation.getLeft(), draggableArea.getLeft()); iterator.hasNext();) {
          int left = iterator.next();
          Location tempLocation = new Location(left, positionerLocation.getTop());
          tempDraggableArea.moveTo(tempLocation);
          // TODO consider only widgets in area between desired and known-good positions
          if (!collision(draggable, tempDraggableArea)) {
            newLocation = tempLocation;
          } else {
            break;
          }
        }

        Location startLocation = newLocation != null ? newLocation : positionerLocation;
        for (IntRangeIterator iterator = new IntRangeIterator(startLocation.getTop(), draggableArea.getTop()); iterator.hasNext();) {
          int top = iterator.next();
          Location tempLocation = new Location(startLocation.getLeft(), top);
          tempDraggableArea.moveTo(tempLocation);
          // TODO consider only widgets in area between desired and known-good positions
          if (!collision(draggable, tempDraggableArea)) {
            newLocation = tempLocation;
          } else {
            break;
          }
        }

        if (newLocation != null) {
          this.dropTargetPanel.add(widget, newLocation.getLeft(), newLocation.getTop());
          return true;
        }
      }
    }
    if ((widget != getPositioner()) && getPositioner().isAttached()) {
      Area dropTargetArea = new Area(this.dropTargetPanel, boundryPanel);
      Area positionerArea = new Area(getPositioner(), boundryPanel);
      if (dropTargetArea.contains(positionerArea)) {
        // set location to where positioner was last successfully placed
        location = new Location(getPositioner(), this.dropTargetPanel);
        this.dropTargetPanel.add(widget, location.getLeft(), location.getTop());
        return true;
      }
    }
    return false;
  }

  private boolean collision(Widget widget, Area area) {
    for (Iterator iterator = this.dropTargetPanel.iterator(); iterator.hasNext();) {
      Widget w = (Widget) iterator.next();
      if ((w == widget) || (w == getPositioner())) {
        continue;
      }
      if ((new Area(w, this.dropTargetPanel)).intersects(area)) {
        return true;
      }
    }
    return false;
  }

}
