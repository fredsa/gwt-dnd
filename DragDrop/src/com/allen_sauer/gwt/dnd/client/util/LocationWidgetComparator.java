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

public interface LocationWidgetComparator {

  LocationWidgetComparator BOTTOM_HALF_COMPARATOR = new LocationWidgetComparator() {

    @Override
    public boolean locationIndicatesIndexFollowingWidget(Area widgetArea, Location location) {
      return location.getTop() > widgetArea.getTop() + widgetArea.getHeight() / 2;
    }
  };

  LocationWidgetComparator BOTTOM_RIGHT_COMPARATOR = new LocationWidgetComparator() {

    @Override
    public boolean locationIndicatesIndexFollowingWidget(Area widgetArea, Location location) {
      return widgetArea.inBottomRight(location);
    }
  };

  LocationWidgetComparator RIGHT_HALF_COMPARATOR = new LocationWidgetComparator() {

    @Override
    public boolean locationIndicatesIndexFollowingWidget(Area widgetArea, Location location) {
      return location.getLeft() > widgetArea.getLeft() + widgetArea.getWidth() / 2;
    }
  };

  /**
   * Determine whether or not <code>location</code> indicates insertion following widgetArea.
   * @param widgetArea the widget area to consider
   * @param location the location to consider
   * @return true if the location is indicates an index position following the widget
   */
  boolean locationIndicatesIndexFollowingWidget(Area widgetArea, Location location);
}
