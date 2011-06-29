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
package com.allen_sauer.gwt.dnd.client.util;

/**
 * Provides default method implementations.
 */
public abstract class AbstractLocation implements Location {

  @Override
  public Location newLocationSnappedToGrid(int gridX, int gridY) {
    int left = Math.round((float) getLeft() / gridX) * gridX;
    int top = Math.round((float) getTop() / gridY) * gridY;
    return new CoordinateLocation(left, top);
  }

  /**
   * Textual representation of this location formatted as <code>(x, y)</code>.
   * 
   * @return a string representation of this location
   */
  @Override
  public String toString() {
    return "(" + getLeft() + ", " + getTop() + ")";
  }
}
