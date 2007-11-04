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

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.DOMUtil;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A {@link DropControllerCollection} which utilizes the provided mouse location
 * <code>(x, y)</code> for drop target selection.
 */
final class WidgetCenterDropControllerCollection extends DropControllerCollection {
  private final ArrayList dropControllerList;
  private Candidate[] sortedCandidates;

  /**
   * Default constructor.
   */
  WidgetCenterDropControllerCollection(ArrayList dropControllerList) {
    this.dropControllerList = dropControllerList;
  }

  /**
   * Determines which DropController represents the deepest DOM descendant
   * drop target intersecting with the provided <code>widget</code>.
   * 
   * @param widget draggable or its proxy widget
   * @param x (ignored) offset left relative to document body
   * @param y (ignored) offset top relative to document body
   * @param boundaryPanel the panel which provides the boundaries for the drag
   *                      controller. Drop targets must be within this are to be
   *                      considered
   * 
   * @return a drop controller for the intersecting drop target or <code>null</code> if none
   *         are applicable
   */
  DropController getIntersectDropController(Widget widget, int x, int y, Panel boundaryPanel) {
    Area widgetArea = new WidgetArea(widget, null);
    Location widgetCenter = widgetArea.getCenter();
    Candidate result = null;
    int closestCenterDistanceToEdge = Integer.MAX_VALUE;
    for (int i = 0; i < sortedCandidates.length; i++) {
      Candidate candidate = sortedCandidates[i];
      Area targetArea = candidate.getTargetArea();
      if (targetArea.intersects(widgetArea)) {
        int widgetCenterDistanceToTargetEdge = targetArea.distanceToEdge(widgetCenter);
        if (widgetCenterDistanceToTargetEdge <= closestCenterDistanceToEdge) {
          if (result == null || !DOMUtil.isOrContains(candidate.getDropTarget().getElement(), result.getDropTarget().getElement())) {
            closestCenterDistanceToEdge = widgetCenterDistanceToTargetEdge;
            result = candidate;
          }
        }
      }
    }
    return result == null ? null : result.getDropController();
  }

  /**
   * Cache a list of eligible drop controllers, sorted by relative DOM positions
   * of their respective drop targets. Should be called at the beginning of each
   * drag operation, or whenever drop target eligibility has changed.
   * 
   * @param boundaryPanel boundary area for drop target eligibility
   *            considerations
   * @param draggable
   */
  void resetCache(Panel boundaryPanel, Widget draggable) {
    WidgetArea boundaryArea = new WidgetArea(boundaryPanel, null);

    ArrayList list = new ArrayList();
    for (Iterator iterator = dropControllerList.iterator(); iterator.hasNext();) {
      DropController dropController = (DropController) iterator.next();
      Candidate candidate = new Candidate(dropController);
      if (!DOMUtil.isOrContains(draggable.getElement(), candidate.getDropTarget().getElement())) {
        if (candidate.getTargetArea().intersects(boundaryArea)) {
          list.add(candidate);
        }
      }
    }

    sortedCandidates = (Candidate[]) list.toArray(new Candidate[] {});
    Arrays.sort(sortedCandidates);
  }
}
