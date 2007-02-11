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
    left = widget.getAbsoluteLeft();
    top = widget.getAbsoluteTop();
    widgetBorderLeft = UIUtil.getBorderLeft(widget.getElement());
    widgetBorderTop = UIUtil.getBorderTop(widget.getElement());
    widgetInnerWidth = UIUtil.getClientWidth(widget.getElement());
    widgetInnerHeight = UIUtil.getClientHeight(widget.getElement());

    if (boundryPanel != null) {
      left -= boundryPanel.getAbsoluteLeft();
      left -= UIUtil.getBorderLeft(boundryPanel.getElement());
      top -= boundryPanel.getAbsoluteTop();
      top -= UIUtil.getBorderTop(boundryPanel.getElement());
    }
    right = left + widget.getOffsetWidth();
    bottom = top + widget.getOffsetHeight();
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
    return (area.left >= (left + widgetBorderLeft)) && (area.right <= (right + widgetInnerWidth))
        && (area.top >= (top + widgetBorderTop)) && (area.bottom <= (bottom + widgetInnerHeight));
  }

  public Area copyOf() {
    return new Area(left, top, right, bottom);
  }

  public int getBottom() {
    return bottom;
  }

  public Location getCenter() {
    return new Location(left + this.getWidth() / 2, top + this.getHeight() / 2);
  }

  public int getHeight() {
    return bottom - top;
  }

  public int getInternalHeight() {
    return widgetInnerHeight;
  }

  public int getInternalWidth() {
    return widgetInnerWidth;
  }

  public int getLeft() {
    return left;
  }

  public int getRight() {
    return right;
  }

  public int getTop() {
    return top;
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
    return widgetBorderLeft;
  }

  public int getWidgetBorderTop() {
    return widgetBorderTop;
  }

  public int getWidgetInnerHeight() {
    return widgetInnerHeight;
  }

  public int getWidgetInnerWidth() {
    return widgetInnerWidth;
  }

  public int getWidth() {
    return right - left;
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
    if ((right < targetArea.left) || (left > targetArea.right) || (bottom < targetArea.top)
        || (top > targetArea.bottom)) {
      return false;
    }
    return true;
  }

  public boolean intersects(Location location) {
    return ((left <= location.getLeft()) && (location.getLeft() <= right))
        && ((top <= location.getTop()) && (location.getTop() <= bottom));
  }

  public void moveTo(Location location) {
    int deltaX = location.getLeft() - left;
    int deltaY = location.getTop() - top;
    left += deltaX;
    right += deltaX;
    top += deltaY;
    bottom += deltaY;
  }

  public String toString() {
    return "[ (" + left + ", " + top + ") - (" + right + ", " + bottom + ") ]";
  }

}
