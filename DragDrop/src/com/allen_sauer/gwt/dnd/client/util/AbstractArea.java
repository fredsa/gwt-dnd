/*
 * Copyright 2008 Fred Sauer
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
package com.allen_sauer.gwt.dnd.client.util;

/**
 * Provides implementations for common {@link Area} calculations.
 */
public abstract class AbstractArea implements Area {

  private int bottom;

  private int left;

  private int right;

  private int top;

  public Area copyOf() {
    return new CoordinateArea(getLeft(), getTop(), getRight(), getBottom());
  }

  public int distanceToEdge(Location location) {
    int xDistance = Math.max(left - location.getLeft(), location.getLeft() - right);
    int yDistance = Math.max(top - location.getTop(), location.getTop() - bottom);
    return Math.max(xDistance, yDistance);
  }

  public final int getBottom() {
    return bottom;
  }

  public Location getCenter() {
    return new CoordinateLocation(left + getWidth() / 2, top + getHeight() / 2);
  }

  public int getHeight() {
    return bottom - top;
  }

  public final int getLeft() {
    return left;
  }

  public final int getRight() {
    return right;
  }

  public int getSize() {
    return getWidth() * getHeight();
  }

  public final int getTop() {
    return top;
  }

  public int getWidth() {
    return right - left;
  }

  public boolean inBottomRight(Location location) {
    Location center = getCenter();
    float distanceX = (float) (location.getLeft() - center.getLeft()) / getWidth();
    float distanceY = (float) (location.getTop() - center.getTop()) / getHeight();
    return distanceX + distanceY > 0;
  }

  public boolean intersects(Area targetArea) {
    if (getRight() < targetArea.getLeft() || getLeft() > targetArea.getRight()
        || getBottom() < targetArea.getTop() || getTop() > targetArea.getBottom()) {
      return false;
    }
    return true;
  }

  public boolean intersects(Location location) {
    return left <= location.getLeft() && location.getLeft() <= right && top <= location.getTop()
        && location.getTop() <= bottom;
  }

  public void moveTo(Location location) {
    int deltaX = location.getLeft() - left;
    int deltaY = location.getTop() - top;
    left += deltaX;
    right += deltaX;
    top += deltaY;
    bottom += deltaY;
  }

  /**
   * Textual representation of this area formatted as <code>[(left, top) - (right, bottom) ]</code>.
   * @return a string representation of this area
   */
  @Override
  public String toString() {
    return "[ (" + getLeft() + ", " + getTop() + ") - (" + getRight() + ", " + getBottom() + ") ]";
  }

  /**
   * Set bottom coordinate.
   * 
   * @param bottom the bottom coordinate in pixels
   */
  protected final void setBottom(int bottom) {
    this.bottom = bottom;
  }

  /**
   * Set left coordinate.
   * 
   * @param left the left coordinate in pixels
   */
  protected final void setLeft(int left) {
    this.left = left;
  }

  /**
   * Set right coordinate.
   * 
   * @param right the right coordinate in pixels
   */
  protected final void setRight(int right) {
    this.right = right;
  }

  /**
   * Set top coordinate.
   * 
   * @param top the top coordinate in pixels
   */
  protected final void setTop(int top) {
    this.top = top;
  }
}
