/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dnd.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.allen_sauer.gwt.dnd.client.util.Area;
import com.allen_sauer.gwt.dnd.client.util.CoordinateLocation;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.allen_sauer.gwt.dnd.client.util.WidgetArea;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Package private helper implementation class for {@link AbstractDragController} to track all
 * relevant {@link DropController DropControllers}.
 */
class DropControllerCollection {

  protected static class Candidate implements Comparable<Candidate> {

    private final DropController dropController;

    private final Area targetArea;

    Candidate(DropController dropController) {
      this.dropController = dropController;
      Widget target = dropController.getDropTarget();
      if (!target.isAttached()) {
        throw new IllegalStateException(
            "Unattached drop target. You must call DragController#unregisterDropController for all drop targets not attached to the DOM.");
      }
      targetArea = new WidgetArea(target, null);
    }

    @Override
    public int compareTo(Candidate other) {
      Element myElement = getDropTarget().getElement();
      Element otherElement = other.getDropTarget().getElement();
      return compareElement(myElement, otherElement);
    }

    @Override
    public boolean equals(Object other) {
      throw new RuntimeException("hash code not implemented");
    }

    @Override
    public int hashCode() {
      throw new RuntimeException("hash code not implemented");
    }

    DropController getDropController() {
      return dropController;
    }

    Widget getDropTarget() {
      return dropController.getDropTarget();
    }

    Area getTargetArea() {
      return targetArea;
    }

    private int compareElement(Element myElement, Element otherElement) {
      if (myElement == otherElement) {
        return 0;
      } else if (DOM.isOrHasChild(myElement, otherElement)) {
        return -1;
      } else if (DOM.isOrHasChild(otherElement, myElement)) {
        return 1;
      } else {
        // check parent ensuring global candidate sorting is correct
        Element myParentElement = myElement.getParentElement().cast();
        Element otherParentElement = otherElement.getParentElement().cast();
        if (myParentElement != null && otherParentElement != null) {
          return compareElement(myParentElement, otherParentElement);
        }
        return 0;
      }
    }
  }

  private final ArrayList<DropController> dropControllerList;

  private Candidate[] sortedCandidates = null;

  /**
   * Default constructor.
   */
  DropControllerCollection(ArrayList<DropController> dropControllerList) {
    this.dropControllerList = dropControllerList;
  }

  /**
   * Determines which DropController represents the deepest DOM descendant drop target located at
   * the provided location <code>(x, y)</code>.
   * 
   * @param x offset left relative to document body
   * @param y offset top relative to document body
   * @return a drop controller for the intersecting drop target or <code>null</code> if none are
   *         applicable
   */
  DropController getIntersectDropController(int x, int y) {
    Location location = new CoordinateLocation(x, y);
    if (DOMUtil.DEBUG) {
      for (int i = sortedCandidates.length - 1; i >= 0; i--) {
        Candidate candidate = sortedCandidates[i];
        DOMUtil.debugWidgetWithColor(candidate.getDropTarget(), "blue");
      }
    }
    for (int i = sortedCandidates.length - 1; i >= 0; i--) {
      Candidate candidate = sortedCandidates[i];
      Area targetArea = candidate.getTargetArea();
      if (targetArea.intersects(location)) {
        if (DOMUtil.DEBUG) {
          DOMUtil.debugWidgetWithColor(candidate.getDropTarget(), "green");
        }
        return candidate.getDropController();
      }
      if (DOMUtil.DEBUG) {
        DOMUtil.debugWidgetWithColor(candidate.getDropTarget(), "red");
      }
    }
    return null;
  }

  /**
   * Cache a list of eligible drop controllers, sorted by relative DOM positions of their respective
   * drop targets. Called at the beginning of each drag operation, or whenever drop target
   * eligibility has changed while dragging.
   * 
   * @param boundaryPanel boundary area for drop target eligibility considerations
   * @param context the current drag context
   */
  void resetCache(Panel boundaryPanel, DragContext context) {
    ArrayList<Candidate> list = new ArrayList<Candidate>();

    if (context.draggable != null) {
      WidgetArea boundaryArea = new WidgetArea(boundaryPanel, null);
      for (DropController dropController : dropControllerList) {
        Candidate candidate = new Candidate(dropController);
        Widget dropTarget = candidate.getDropTarget();
        if (DOM.isOrHasChild(context.draggable.getElement(), dropTarget.getElement())) {
          continue;
        }
        if (candidate.getTargetArea().intersects(boundaryArea)) {
          list.add(candidate);
        }
      }
    }

    sortedCandidates = list.toArray(new Candidate[list.size()]);
    Arrays.sort(sortedCandidates);
  }
}
