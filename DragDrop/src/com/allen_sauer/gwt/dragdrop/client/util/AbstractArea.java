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

public abstract class AbstractArea implements Area {

  private int bottom;
  private int left;
  private int right;
  private int top;

  public Area copyOf() {
    return new CoordinateArea(getLeft(), getTop(), getRight(), getBottom());
  }

  public final int getBottom() {
    return this.bottom;
  }

  public Location getCenter() {
    return new CoordinateLocation(left + getWidth() / 2, top + getHeight() / 2);
  }

  public int getHeight() {
    return bottom - top;
  }

  public final int getLeft() {
    return this.left;
  }

  public final int getRight() {
    return this.right;
  }

  public final int getTop() {
    return this.top;
  }

  public int getWidth() {
    return right - left;
  }

  public boolean inBottomRight(Location location) {
    Location center = getCenter();
    float distanceX = (float) (location.getLeft() - center.getLeft()) / getWidth();
    float distanceY = (float) (location.getTop() - center.getTop()) / getHeight();
    return (distanceX + distanceY) > 0;
  }

  public boolean intersects(Area targetArea) {
    if ((getRight() < targetArea.getLeft()) || (getLeft() > targetArea.getRight()) || (getBottom() < targetArea.getTop())
        || (getTop() > targetArea.getBottom())) {
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
    return "[ (" + getLeft() + ", " + getTop() + ") - (" + getRight() + ", " + getBottom() + ") ]";
  }

  protected final void setBottom(int bottom) {
    this.bottom = bottom;
  }

  protected final void setLeft(int left) {
    this.left = left;
  }

  protected final void setRight(int right) {
    this.right = right;
  }

  protected final void setTop(int top) {
    this.top = top;
  }
}
