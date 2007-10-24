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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;

/**
 * A {@link DropController} for instances of {@link FlowPanel}.
 * 
 * @see IndexedDropController
 */
public class FlowPanelDropController extends AbstractIndexedDropController {
  private static final String CSS_DRAGDROP_FLOW_PANEL_POSITIONER = "dragdrop-flow-panel-positioner";

  private FlowPanel dropTarget;

  /**
   * @see IndexedDropController#IndexedDropController(com.google.gwt.user.client.ui.IndexedPanel)
   * 
   * @param dropTarget
   */
  public FlowPanelDropController(FlowPanel dropTarget) {
    super(dropTarget);
    if (!(dropTarget instanceof FlowPanel)) {
      throw new IllegalArgumentException(GWT.getTypeName(dropTarget) + " is not currently supported by this controller");
    }
    this.dropTarget = dropTarget;
  }

  // TODO remove after enhancement for issue 1112 provides InsertPanel interface
  // http://code.google.com/p/google-web-toolkit/issues/detail?id=1112
  protected void insert(Widget widget, int beforeIndex) {
    dropTarget.insert(widget, beforeIndex);
  }

  /**
   * Determine if location is to the right of area's center.
   * 
   * @param location the location to consider
   * @return true if the location is indicates an index position following the widget
   */
  protected boolean locationIndicatesIndexFollowingWidget(WidgetArea widgetArea, Location location) {
    return location.getLeft() > widgetArea.getLeft() + widgetArea.getWidth() / 2;
  }

  protected Widget newPositioner(Widget reference) {
    HTML positioner = new HTML("&#x203B;");
    positioner.addStyleName(CSS_DRAGDROP_FLOW_PANEL_POSITIONER);
    return positioner;
  }
}
