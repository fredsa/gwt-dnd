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
 * Class representing a rectangular region, with convenience methods for calculations.
 */
public interface Area {

  /**
   * Clone our area.
   * 
   * @return the new area
   */
  Area copyOf();

  /**
   * Determine the shortest distance from the location to the edge of the area. Zero indicates a
   * location on the edge. Negative distances indicate a location inside the area.
   * 
   * @param location the reference location
   * @return shortest distance to edge of area
   */
  int distanceToEdge(Location location);

  /**
   * Get the area's bottom coordinate in pixels.
   * 
   * @return the bottom coordinate in pixels
   */
  int getBottom();

  /**
   * Get the area's center Location.
   * 
   * @return the area's center Location
   */
  Location getCenter();

  /**
   * Get the area's height.
   * 
   * @return the area's height in pixels
   */
  int getHeight();

  /**
   * Get the area's left coordinate in pixels.
   * 
   * @return the left coordinate in pixels
   */
  int getLeft();

  /**
   * Get the area's right coordinate in pixels.
   * 
   * @return the right coordinate in pixels
   */
  int getRight();

  /**
   * Determine area (width * height).
   * 
   * @return size of area
   */
  int getSize();

  /**
   * Get the area's top coordinate in pixels.
   * 
   * @return the top coordinate in pixels
   */
  int getTop();

  /**
   * Get the area's width.
   * 
   * @return the area's width in pixels
   */
  int getWidth();

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
  boolean inBottomRight(Location location);

  /**
   * Determine if the target area intersects our area.
   * 
   * @param targetArea the area to compare to
   * @return true if target area intersects our area
   */
  boolean intersects(Area targetArea);

  /**
   * Determine if the provided location intersects with our area.
   * 
   * @param location the location to examine
   * @return true if the location falls within our area
   */
  boolean intersects(Location location);

  /**
   * Translate our top left position to the new location.
   * 
   * @param location the position to translate to
   */
  void moveTo(Location location);
}