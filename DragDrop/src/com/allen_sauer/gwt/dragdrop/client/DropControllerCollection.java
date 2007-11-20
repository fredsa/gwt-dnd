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
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.DOMUtil;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;

/**
 * Package private helper implementation class for {@link AbstractDragController}
 * to track all relevant {@link DropController DropControllers}.
 */
abstract class DropControllerCollection {
  protected static class Candidate implements Comparable {
    private final DropController dropController;
    private Area targetArea;

    Candidate(DropController dropController) {
      this.dropController = dropController;
      Widget target = dropController.getDropTarget();
      if (!target.isAttached()) {
        throw new IllegalStateException(
            "Unattached drop target. You must call DragController#unregisterDropController for all drop targets not attached to the DOM.");
      }
      targetArea = new WidgetArea(target, null);
    }

    public int compareTo(Object arg0) {
      Candidate other = (Candidate) arg0;

      // TODO remove workaround for GWT issue 1583 (non-stable Arrays.sort), which should be fixed in GWT 1.5
      if (dropController instanceof BoundaryDropController) {
        return -1;
      } else if (other.dropController instanceof BoundaryDropController) {
        return 1;
      }

      Element myElement = getDropTarget().getElement();
      Element otherElement = other.getDropTarget().getElement();
      if (DOM.compare(myElement, otherElement)) {
        return 0;
      } else {
        return DOMUtil.isOrContains(myElement, otherElement) ? -1 : 1;
      }
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
  }

  /**
   * Default constructor.
   */
  protected DropControllerCollection() {
  }

  /**
   * Determines which DropController represents the deepest DOM descendant
   * drop target located at the provided location <code>(x, y)</code> or which suitably
   * intersects with <code>widget</code>.
   * 
   * @param widget draggable or its proxy widget
   * @param x offset left relative to document body
   * @param y offset top relative to document body
   * @param boundaryPanel the panel which provides the boundaries for the drag
   *                      controller. Drop targets must be within this are to be
   *                      considered
   * 
   * @return a drop controller for the intersecting drop target or <code>null</code> if none
   *         are applicable
   */
  abstract DropController getIntersectDropController(Widget widget, int x, int y, Panel boundaryPanel);

  /**
   * Cache a list of eligible drop controllers, sorted by relative DOM positions
   * of their respective drop targets. Should be called at the beginning of each
   * drag operation, or whenever drop target eligibility has changed.
   * 
   * @param boundaryPanel boundary area for drop target eligibility
   *            considerations
   * @param draggable
   */
  abstract void resetCache(Panel boundaryPanel, Widget draggable);
}
