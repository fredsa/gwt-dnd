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

import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.IndexedDragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.util.CoordinateLocation;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

/**
 * {@link DropController} for {@link IndexedPanel} drop targets.
 */
public abstract class AbstractIndexedDropController extends AbstractPositioningDropController {
  private int dropIndex;
  private IndexedPanel dropTarget;

  /**
   * @see FlowPanelDropController#FlowPanelDropController(com.google.gwt.user.client.ui.FlowPanel)
   * 
   * @param dropTarget
   */
  public AbstractIndexedDropController(IndexedPanel dropTarget) {
    super((Panel) dropTarget);
    this.dropTarget = dropTarget;
  }

  public DragEndEvent onDrop(Widget reference, Widget draggable, DragController dragController) {
    super.onDrop(reference, draggable, dragController);
    assert dropIndex != -1 : "Should not happen after onPreviewDrop did not veto";
    insert(draggable, dropIndex);
    return new IndexedDragEndEvent(draggable, (Panel) dropTarget, dropIndex);
  }

  public void onMove(int x, int y, Widget reference, Widget draggable, DragController dragController) {
    super.onMove(x, y, reference, reference, dragController);

    Location dropTargetLocation = new WidgetLocation((Widget) dropTarget, null);
    Location mouseLocation = new CoordinateLocation(x - dropTargetLocation.getLeft(), y - dropTargetLocation.getTop());
    mouseLocation = new CoordinateLocation(x, y);

    int targetIndex = findIntersect(mouseLocation);

    // check that positioner not already in the correct location
    Widget positioner = getPositioner();
    int positionerIndex = dropTarget.getWidgetIndex(positioner);

    if (positionerIndex != targetIndex && (positionerIndex != targetIndex - 1 || targetIndex == 0)) {
      if (positionerIndex == 0 && dropTarget.getWidgetCount() == 1) {
        // do nothing, the positioner is the only widget
      } else if (targetIndex == -1) {
        // outside drop target, so remove positioner to indicate a drop will not happen
        positioner.removeFromParent();
      } else {
        insert(positioner, targetIndex);
      }
    }
  }

  public void onPreviewDrop(Widget reference, Widget draggable, DragController dragController) throws VetoDropException {
    super.onPreviewDrop(reference, draggable, dragController);
    dropIndex = dropTarget.getWidgetIndex(getPositioner());
    if (dropIndex == -1) {
      throw new VetoDropException();
    }
  }

  /**
   * Insert the provided widget using an appropriate drop target specific method.
   * 
   * TODO remove after enhancement for issue 1112 provides InsertPanel interface
   * 
   * @param widget the widget to be inserted
   * @param beforeIndex the widget index before which <code>widget</code> should be inserted
   */
  protected abstract void insert(Widget widget, int beforeIndex);

  /**
   * Determine whether or not <code>location</code> indicates insertion
   * following widgetArea. This implementation delegates responsibility to
   * <code>{@link WidgetArea#inBottomRight(Location)}</code> by default.
   * 
   * This method should be overridden when all dropTarget children are either displayed
   * only horizontally or only vertically. The specific case of a HorizontalPanel
   * or VerticalPanel dropTqrget is handled by {@link IndexedDropController}.
   * 
   * @param location the location to consider
   * @return true if the location is indicates an index position following the widget
   */
  protected boolean locationIndicatesIndexFollowingWidget(WidgetArea widgetArea, Location location) {
    return widgetArea.inBottomRight(location);
  }

  private int findIntersect(Location mouseLocation) {
    int widgetCount = dropTarget.getWidgetCount();

    // short circuit in case dropTarget has no children
    if (widgetCount == 0) {
      return 0;
    }

    // binary search over range of widgets to find intersection
    int low = 0;
    int high = widgetCount;

    while (true) {
      int mid = (low + high) / 2;
      assert mid >= low;
      assert mid < high;
      WidgetArea midArea = new WidgetArea(dropTarget.getWidget(mid), null);
      if (mid == low) {
        if (mid == 0) {
          if (locationIndicatesIndexFollowingWidget(midArea, mouseLocation)) {
            return high;
          } else {
            return mid;
          }
        } else {
          return high;
        }
      }
      if (midArea.getBottom() < mouseLocation.getTop()) {
        low = mid;
      } else if (midArea.getTop() > mouseLocation.getTop()) {
        high = mid;
      } else if (midArea.getRight() < mouseLocation.getLeft()) {
        low = mid;
      } else if (midArea.getLeft() > mouseLocation.getLeft()) {
        high = mid;
      } else {
        if (locationIndicatesIndexFollowingWidget(midArea, mouseLocation)) {
          return mid + 1;
        } else {
          return mid;
        }
      }
    }
  }
}
