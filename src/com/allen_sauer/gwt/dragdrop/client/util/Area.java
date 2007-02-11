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
  private int widgetBorderLeft;
  private int widgetBorderTop;
  private int widgetInnerHeight;
  private int widgetInnerWidth;

  /**
   * Determine area (i.e. the top left and bottom right coordinates) of widget relative to
   * boundryPanel such that:
   * <ul>
   * <li><code>boundryPanel.add(widget, area.getLeft(), area.getTop())</code>
   * leaves the object in the exact same location on the screen and area</li>
   * <li><code>area.getRight() = area.getLeft() + widget.getOffsetWidget()</code></li>
   * <li><code>area.getBottom() = area.getTop() + widget.getOffsetHeight()</code></li>
   * </ul>
   * 
   * Note that boundryPanel need not be the parent node, or even an ancestor of widget.
   * Therefore coordinates returned may be negative or may exceed the dimensions of boundryPanel.
   * 
   * @param widget the widget whose area we seek
   * @param boundryPanel the AbsolutePanel relative to which we seek our area or 
   *        RootPanel().get() if null
   */
  public Area(Widget widget, AbsolutePanel boundryPanel) {
    this.left = widget.getAbsoluteLeft();
    this.top = widget.getAbsoluteTop();
    this.widgetBorderLeft = UIUtil.getBorderLeft(widget.getElement());
    this.widgetBorderTop = UIUtil.getBorderTop(widget.getElement());
    this.widgetInnerWidth = UIUtil.getClientWidth(widget.getElement());
    this.widgetInnerHeight = UIUtil.getClientHeight(widget.getElement());

    if (boundryPanel != null) {
      this.left -= boundryPanel.getAbsoluteLeft();
      this.left -= UIUtil.getBorderLeft(boundryPanel.getElement());
      this.top -= boundryPanel.getAbsoluteTop();
      this.top -= UIUtil.getBorderTop(boundryPanel.getElement());
    }
    this.right = this.left + widget.getOffsetWidth();
    this.bottom = this.top + widget.getOffsetHeight();
  }

  private Area(int left, int top, int right, int bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  /**
   * Determine whether area is fully contained without our area.
   * @param area the area we are testing
   * @return true of area is fully contained within our area
   */
  public boolean contains(Area area) {
    return (area.left >= (this.left + this.widgetBorderLeft)) && (area.right <= (this.right + this.widgetInnerWidth))
        && (area.top >= (this.top + this.widgetBorderTop)) && (area.bottom <= (this.bottom + this.widgetInnerHeight));
  }

  public Area copyOf() {
    return new Area(this.left, this.top, this.right, this.bottom);
  }

  public int getBottom() {
    return this.bottom;
  }

  public Location getCenter() {
    return new Location(this.left + this.getWidth() / 2, this.top + this.getHeight() / 2);
  }

  public int getHeight() {
    return this.bottom - this.top;
  }

  public int getInternalHeight() {
    return this.widgetInnerHeight;
  }

  public int getInternalWidth() {
    return this.widgetInnerWidth;
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

  public int getWidgetBorderLeft() {
    return this.widgetBorderLeft;
  }

  public int getWidgetBorderTop() {
    return this.widgetBorderTop;
  }

  public int getWidgetInnerHeight() {
    return this.widgetInnerHeight;
  }

  public int getWidgetInnerWidth() {
    return this.widgetInnerWidth;
  }

  public int getWidth() {
    return this.right - this.left;
  }

  /**
   * See if location is to the bottom-right of 45 degree line.
   * 
   * <pre>
   *             y  45
   *             | /
   *             |/   
   *        -----+----- x
   *            /|
   *           / |
   * 
   * </pre>
   */
  public boolean inBottomRight(Location location) {
    Location center = getCenter();
    float distanceX = (float) (location.getLeft() - center.getLeft()) / getWidth();
    float distanceY = (float) (location.getTop() - center.getTop()) / getHeight();
    return (distanceX + distanceY) > 0;
  }

  public boolean intersects(Area targetArea) {
    if ((this.right < targetArea.left) || (this.left > targetArea.right) || (this.bottom < targetArea.top)
        || (this.top > targetArea.bottom)) {
      return false;
    }
    return true;
  }

  public boolean intersects(Location location) {
    return ((this.left <= location.getLeft()) && (location.getLeft() <= this.right))
        && ((this.top <= location.getTop()) && (location.getTop() <= this.bottom));
  }

  public void moveTo(Location location) {
    int deltaX = location.getLeft() - this.left;
    int deltaY = location.getTop() - this.top;
    this.left += deltaX;
    this.right += deltaX;
    this.top += deltaY;
    this.bottom += deltaY;
  }

  public String toString() {
    return "[ (" + this.left + ", " + this.top + ") - (" + this.right + ", " + this.bottom + ") ]";
  }

}
