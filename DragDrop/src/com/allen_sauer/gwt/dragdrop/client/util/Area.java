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
package com.allen_sauer.gwt.dragdrop.client.util;

import com.google.gwt.user.client.ui.Widget;

/**
 * Class to represent a rectangular region of a widget relative to another widget.
 * Also keeps track of the size of the widget borders and its inner width and height.
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
   * Determine the area of a widget relative to a panel. The area returned is such that:
   * <ul>
   * <li><code>parent.add(widget, area.getLeft(), area.getTop())</code>
   * leaves the object in the exact same location on the screen and area</li>
   * <li><code>area.getRight() = area.getLeft() + widget.getOffsetWidget()</code></li>
   * <li><code>area.getBottom() = area.getTop() + widget.getOffsetHeight()</code></li>
   * </ul>
   * 
   * Note that boundryPanel need not be the parent node, or even an ancestor of widget.
   * Therefore coordinates returned may be negative or may exceed the dimensions of boundryPanel.
   * 
   * @param widget the widget whose area we seek
   * @param reference the widget relative to which we seek our area. If <code>null</code>,
   *        then <code>RootPanel().get()</code> is assumed
   */
  public Area(Widget widget, Widget reference) {
    left = widget.getAbsoluteLeft();
    top = widget.getAbsoluteTop();
    widgetBorderLeft = UIUtil.getBorderLeft(widget.getElement());
    widgetBorderTop = UIUtil.getBorderTop(widget.getElement());
    widgetInnerWidth = UIUtil.getClientWidth(widget.getElement());
    widgetInnerHeight = UIUtil.getClientHeight(widget.getElement());

    if (reference != null) {
      left -= reference.getAbsoluteLeft();
      left -= UIUtil.getBorderLeft(reference.getElement());
      top -= reference.getAbsoluteTop();
      top -= UIUtil.getBorderTop(reference.getElement());
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

  /**
   * Clone our area.
   * 
   * @return the new area
   */
  public Area copyOf() {
    return new Area(left, top, right, bottom);
  }

  public int getBottom() {
    return bottom;
  }

  public Location getCenter() {
    return new Location(left + getWidth() / 2, top + getHeight() / 2);
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
   * Determine if location is to the bottom-right of the following 45 degree line.
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
   *
   * @param location the location to consider
   * @return true if the location is to below the 45 degree line
   */
  public boolean inBottomRight(Location location) {
    Location center = getCenter();
    float distanceX = (float) (location.getLeft() - center.getLeft()) / getWidth();
    float distanceY = (float) (location.getTop() - center.getTop()) / getHeight();
    return (distanceX + distanceY) > 0;
  }

  /**
   * Determine if the target area intersects our area
   * 
   * @param targetArea the area to compare to
   * @return true if target area intersects our area
   */
  public boolean intersects(Area targetArea) {
    if ((right < targetArea.left) || (left > targetArea.right) || (bottom < targetArea.top) || (top > targetArea.bottom)) {
      return false;
    }
    return true;
  }

  /**
   * Determine if the provided location intersects with our area.
   * 
   * @param location the location to examine
   * @return true if the location falls within our area
   */
  public boolean intersects(Location location) {
    return ((left <= location.getLeft()) && (location.getLeft() <= right))
        && ((top <= location.getTop()) && (location.getTop() <= bottom));
  }

  /**
   * Translate our top left position to the new location.
   * 
   * @param location the position to translate to
   */
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
