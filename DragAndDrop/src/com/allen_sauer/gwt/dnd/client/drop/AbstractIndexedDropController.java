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
package com.allen_sauer.gwt.dnd.client.drop;

import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.util.CoordinateLocation;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.LocationWidgetComparator;

import java.util.Iterator;

/**
 * A {@link DropController} for {@link IndexedPanel} drop targets.
 */
public abstract class AbstractIndexedDropController extends AbstractPositioningDropController {
  private int dropIndex;
  private IndexedPanel dropTarget;
  private Widget positioner;

  /**
   * @see FlowPanelDropController#FlowPanelDropController(com.google.gwt.user.client.ui.FlowPanel)
   * 
   * @param dropTarget
   */
  public AbstractIndexedDropController(IndexedPanel dropTarget) {
    super((Panel) dropTarget);
    this.dropTarget = dropTarget;
  }

  public void onDrop(DragContext context) {
    assert dropIndex != -1 : "Should not happen after onPreviewDrop did not veto";
    for (Iterator iterator = context.selectedWidgets.iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();
      insert(widget, dropIndex++);
    }
    super.onDrop(context);
  }

  public void onEnter(DragContext context) {
    super.onEnter(context);
    positioner = newPositioner(context);
  }

  public void onLeave(DragContext context) {
    positioner.removeFromParent();
    positioner = null;
    super.onLeave(context);
  }

  public void onMove(DragContext context) {
    super.onMove(context);
    int targetIndex = DOMUtil.findIntersect(dropTarget, new CoordinateLocation(context.mouseX, context.mouseY),
        getLocationWidgetComparator());

    // check that positioner not already in the correct location
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

  public void onPreviewDrop(DragContext context) throws VetoDragException {
    dropIndex = dropTarget.getWidgetIndex(positioner);
    if (dropIndex == -1) {
      throw new VetoDragException();
    }
    super.onPreviewDrop(context);
  }

  protected abstract LocationWidgetComparator getLocationWidgetComparator();

  /**
   * Insert the provided widget using an appropriate drop target specific method.
   * 
   * TODO remove after enhancement for issue 1112 provides InsertPanel interface
   * 
   * @param widget the widget to be inserted
   * @param beforeIndex the widget index before which <code>widget</code> should be inserted
   */
  protected abstract void insert(Widget widget, int beforeIndex);

  abstract Widget newPositioner(DragContext context);
}
