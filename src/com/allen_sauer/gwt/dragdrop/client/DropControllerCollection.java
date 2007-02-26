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

import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.util.Area;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A helper class to track all instances of {@link DropController}.
 * 
 */
public class DropControllerCollection extends ArrayList {

  /**
   * Default constructor used by {@link DragController}.
   */
  protected DropControllerCollection() {
  }
  
  /**
   * Determines which drop controller has a lowest DOM descendant target area which intersects
   * with the provided widget area.
   * 
   * @param widget the widget to use to determine intersects
   * @param boundryPanel the panel which provides the boundaries for the drag controller.
   *        Drop targets must be within this are to be considered
   * @return a drop controller for the intersecting drop target or null if none are applicable
   */
  public DropController getIntersectDropController(Widget widget, Panel boundryPanel) {
    Area widgetArea = new Area(widget, null);
    Area boundryArea = new Area(boundryPanel, null);
    DropController result = null;
    for (Iterator iterator = iterator(); iterator.hasNext();) {
      DropController dropController = (DropController) iterator.next();
      Widget target = dropController.getDropTarget();
      Area targetArea = new Area(target, null);
      if (widgetArea.intersects(targetArea) && targetArea.intersects(boundryArea)) {
        if (result == null || DOM.isOrHasChild(result.getDropTarget().getElement(), target.getElement())) {
          result = dropController;
        }
      }
    }
    return result;
  }
}
