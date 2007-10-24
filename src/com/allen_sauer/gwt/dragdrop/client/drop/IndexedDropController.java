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
package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;

/**
 * A {@link DropController} for instances of {@link IndexedPanel}.
 * 
 * @see FlowPanelDropController
 * 
 * TODO VerticalPanel performance is slow because of positioner DOM manipulation
 */
public class IndexedDropController extends AbstractIndexedDropController {
  private static final String CSS_DRAGDROP_INDEXED_POSITIONER = "dragdrop-indexed-positioner";

  private IndexedPanel dropTarget;

  public IndexedDropController(IndexedPanel dropTarget) {
    super(dropTarget);
    if (!(dropTarget instanceof HorizontalPanel) && !(dropTarget instanceof VerticalPanel)) {
      throw new IllegalArgumentException(GWT.getTypeName(dropTarget) + " is not currently supported by this controller");
    }
    this.dropTarget = dropTarget;
  }

  // TODO remove after enhancement for issue 1112 provides InsertPanel interface
  // http://code.google.com/p/google-web-toolkit/issues/detail?id=1112
  protected void insert(Widget widget, int beforeIndex) {
    if (dropTarget instanceof HorizontalPanel) {
      ((HorizontalPanel) dropTarget).insert(widget, beforeIndex);
    } else if (dropTarget instanceof VerticalPanel) {
      ((VerticalPanel) dropTarget).insert(widget, beforeIndex);
    } else {
      throw new RuntimeException("Method insert(Widget widget, int beforeIndex) not supported by " + GWT.getTypeName(dropTarget));
    }
  }

  /**
   * Determine whether or not <code>location</code> indicates insertion
   * following widget.
   * 
   * <ul>
   *    <li>When <code>dropTarget instanceof HorizontalPanel</code>,
   *        determine if location is to the right of area's center.
   *    </li>
   *    <li>When <code>dropTarget instanceof VerticalPanel</code>,
   *        determine if location is below area'a center.
   *    </li>
   *    <li>Otherwise default to
   *        <code>{@link WidgetArea#inBottomRight(Location)}</code>.
   *    </li>
   * </ul>
   * 
   * @param location the location to consider
   * @return true if the location is indicates an index position following the widget
   */
  protected boolean locationIndicatesIndexFollowingWidget(WidgetArea widgetArea, Location location) {
    if (dropTarget instanceof HorizontalPanel) {
      return location.getLeft() > widgetArea.getLeft() + widgetArea.getWidth() / 2;
    } else if (dropTarget instanceof VerticalPanel) {
      return location.getTop() > widgetArea.getTop() + widgetArea.getHeight() / 2;
    } else {
      return widgetArea.inBottomRight(location);
    }
  }

  protected Widget newPositioner(Widget reference) {
    Widget positioner = super.newPositioner(reference);
    positioner.addStyleName(CSS_DRAGDROP_INDEXED_POSITIONER);
    return positioner;
  }
}
