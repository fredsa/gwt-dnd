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
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A helper class to track all relevant {@link DropController DropControllers},
 * used by {@link AbstractDragController}.
 */
public class DropControllerCollection extends ArrayList {
  private static class Candidate implements Comparable {
    private final DropController dropController;
    private Integer size;
    private Area targetArea;

    public Candidate(DropController dropController) {
      this.dropController = dropController;
      Widget target = dropController.getDropTarget();
      if (!target.isAttached()) {
        throw new IllegalStateException("Unattached drop target; please call dragController#unregisterDropController");
      }
      targetArea = new WidgetArea(target, null);
    }

    public int compareTo(Object arg0) {
      Candidate other = (Candidate) arg0;
      return getSize().compareTo(other.getSize());
    }

    public DropController getDropController() {
      return dropController;
    }

    public Widget getDropTarget() {
      return dropController.getDropTarget();
    }

    public Integer getSize() {
      if (size == null) {
        size = new Integer(targetArea.getSize());
      }
      return size;
    }

    public Area getTargetArea() {
      return targetArea;
    }
  }

  private Candidate[] sortedCandidates;

  /**
   * Default constructor.
   */
  public DropControllerCollection() {
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
    Location widgetCenter = widgetArea.getCenter();
    Candidate result = null;
    int closestCenterDistanceToEdge = Integer.MAX_VALUE;
    for (int i = 0; i < sortedCandidates.length; i++) {
      Candidate candidate = sortedCandidates[i];
      Area targetArea = candidate.getTargetArea();
      if (targetArea.intersects(widgetArea)) {
        int widgetCenterDistanceToTargetEdge = targetArea.distanceToEdge(widgetCenter);
        if (widgetCenterDistanceToTargetEdge < closestCenterDistanceToEdge) {
          if (result == null || !DOM.isOrHasChild(candidate.getDropTarget().getElement(), result.getDropTarget().getElement())) {
            closestCenterDistanceToEdge = widgetCenterDistanceToTargetEdge;
            result = candidate;
          }
        }
      }
    }
    return result == null ? null : result.getDropController();
  }

  /**
   * Cache a list of eligible drop controllers, sorted by target area size.
   * Should be called at the beginning of each drag operation, or whenever
   * drop target eligibility has changed.
   * 
   * @param boundaryPanel boundary area for drop target eligibility considerations
   * @param draggable 
   */
  public void resetCache(Panel boundaryPanel, Widget draggable) {
    WidgetArea boundaryArea = new WidgetArea(boundaryPanel, null);

    ArrayList list = new ArrayList();
    for (Iterator iterator = iterator(); iterator.hasNext();) {
      DropController dropController = (DropController) iterator.next();
      Candidate candidate = new Candidate(dropController);
      if (!DOM.isOrHasChild(draggable.getElement(), candidate.getDropTarget().getElement())) {
        if (candidate.getTargetArea().intersects(boundaryArea)) {
          list.add(candidate);
        }
      }
    }
    sortedCandidates = (Candidate[]) list.toArray(new Candidate[] {});
    Arrays.sort(sortedCandidates);
  }
}
