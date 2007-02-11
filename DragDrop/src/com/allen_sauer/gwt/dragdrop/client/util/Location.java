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
package com.allen_sauer.gwt.dragdrop.client.util;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A position represented by a left (x) and top (y) coordinate.
 */
public class Location {

  private int left;
  private int top;

  public Location(int left, int top) {
    this.left = left;
    this.top = top;
  }

  /**
   * Determine location of widget relative boundryPanel such that
   * <code>boundryPanel.add(widget, location.getLeft(), location.getTop())</code>
   * leaves the object in the exact same location on the screen. Note that boundryPanel need not
   * be the parent node, or even an ancestor of widget. Therefore coordinates returned may be 
   * negative or may exceed the dimensions of boundryPanel.
   * 
   * @param widget the widget whose coordinates we seek
   * @param boundryPanel the AbsolutePanel relative to which we seek our coordinates
   */
  public Location(Widget widget, AbsolutePanel boundryPanel) {
    left = widget.getAbsoluteLeft();
    top = widget.getAbsoluteTop();
    if (boundryPanel != null) {
      left -= boundryPanel.getAbsoluteLeft();
      left -= UIUtil.getBorderLeft(boundryPanel.getElement());
      top -= boundryPanel.getAbsoluteTop();
      top -= UIUtil.getBorderTop(boundryPanel.getElement());
    }
  }

  public void constrain(int minLeft, int minTop, int maxLeft, int maxTop) {
    left = Math.min(maxLeft, Math.max(left, minLeft));
    top = Math.min(maxTop, Math.max(top, minTop));
  }

  public int getLeft() {
    return left;
  }

  public int getTop() {
    return top;
  }

  public void snapToGrid(int gridX, int gridY) {
    left = Math.round((float) left / gridX) * gridX;
    top = Math.round((float) top / gridY) * gridY;
  }

  public String toString() {
    return "(" + left + ", " + top + ")";
  }
}
