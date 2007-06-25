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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A helper class to track all instances of {@link DropController}.
 * 
 */
public class DropControllerCollection extends ArrayList {

  private HashMap areaControllerMap = new HashMap();

  /**
   * Default constructor used by {@link PickupDragController}.
   */
  protected DropControllerCollection() {
  }

  /**
   * Determines which drop controller has a lowest DOM descendant target area which intersects
   * with the provided widget area.
   * 
   * @param widget the widget to use to determine intersects
   * @param boundaryPanel the panel which provides the boundaries for the drag controller.
   *        Drop targets must be within this are to be considered
   * @return a drop controller for the intersecting drop target or null if none are applicable
   */
  public DropController getIntersectDropController(Widget widget, Panel boundaryPanel) {
    Area widgetArea = new WidgetArea(widget, null);
    DropController result = null;
    for (Iterator iterator = areaControllerMap.keySet().iterator(); iterator.hasNext();) {
      WidgetArea targetArea = (WidgetArea) iterator.next();
      if (widgetArea.intersects(targetArea)) {
        DropController dropController = (DropController) areaControllerMap.get(targetArea);
        if (result == null || DOM.isOrHasChild(result.getDropTarget().getElement(), dropController.getDropTarget().getElement())) {
          if (!DOM.isOrHasChild(widget.getElement(), dropController.getDropTarget().getElement())) {
            if (result == null
                || (result instanceof BoundaryDropController && (!(dropController instanceof BoundaryDropController)))) {
              result = dropController;
            }
          }
        }
      }
    }
    return result;
  }

  public void resetCache(Panel boundaryPanel) {
    WidgetArea boundaryArea = new WidgetArea(boundaryPanel, null);

    areaControllerMap.clear();
    for (Iterator iterator = iterator(); iterator.hasNext();) {
      DropController dropController = (DropController) iterator.next();
      Widget target = dropController.getDropTarget();
      if (!target.isAttached()) {
        throw new RuntimeException("Unattached drop target; please call dragController#unregisterDropController");
      }
      Area targetArea = new WidgetArea(target, null);
      if (targetArea.intersects(boundaryArea)) {
        areaControllerMap.put(targetArea, dropController);
      }
    }
  }
}
