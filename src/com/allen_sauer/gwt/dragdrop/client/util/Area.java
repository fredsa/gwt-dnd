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
 * A region specified by the left (x), top (y), pixel width and pixel height.
 */
public class Area {

  private int bottom;
  private int left;
  private int right;
  private int top;

  public Area(Widget widget, AbsolutePanel boundryPanel) {
    this.left = widget.getAbsoluteLeft()
        - (boundryPanel == null ? 0 : boundryPanel.getAbsoluteLeft());
    this.top = widget.getAbsoluteTop()
        - (boundryPanel == null ? 0 : boundryPanel.getAbsoluteTop());
    this.right = this.left + widget.getOffsetWidth();
    this.bottom = this.top + widget.getOffsetHeight();
  }

  public int getBottom() {
    return this.bottom;
  }

  public Location getCenter() {
    return new Location(this.left + this.getWidth() / 2, this.top
        + this.getHeight() / 2);
  }

  public int getHeight() {
    return this.bottom - this.top;
  }

  public int getLeft() {
    return this.left;
  }

  public int getRight() {
    return this.right;
  }

  public int getTop() {
    return this.top;
  }

  public int getWidth() {
    return this.right - this.left;
  }

  public boolean intersects(Area targetArea) {
    if ((this.right < targetArea.left) || (this.left > targetArea.right)
        || (this.bottom < targetArea.top) || (this.top > targetArea.bottom)) {
      return false;
    }
    return true;
  }

  public boolean intersects(Location location) {
    return ((this.left <= location.getLeft()) && (location.getLeft() <= this.right))
        && ((this.top <= location.getTop()) && (location.getTop() <= this.bottom));
  }

  // public int overlapPixels(Area targetArea) {
  // int horizontalPixels = Math.max(0, Math.min(this.right, targetArea.right) -
  // Math.max(this.left, targetArea.left));
  // int verticalPixels = Math.max(0, Math.min(this.bottom, targetArea.bottom) -
  // Math.max(this.top, targetArea.top));
  // return horizontalPixels * verticalPixels;
  // }
  //
  // public int pixels() {
  // return getWidth() * getHeight();
  // }
  //
  // public boolean topOrLeftOf(Area targetArea) {
  // return (this.left + this.getWidth() / 2) < (targetArea.left +
  // targetArea.getWidth() / 2)
  // || (this.top + this.getHeight() / 2) < (targetArea.top +
  // targetArea.getHeight() / 2);
  // }

  public String toString() {
    return "[ (" + this.left + ", " + this.top + ") - (" + this.right + ", "
        + this.bottom + ") ]";
  }

}
