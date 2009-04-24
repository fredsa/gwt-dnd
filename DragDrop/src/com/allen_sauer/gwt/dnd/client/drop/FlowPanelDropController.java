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
package com.allen_sauer.gwt.dnd.client.drop;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.util.LocationWidgetComparator;

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
    this.dropTarget = dropTarget;
  }

  @Override
  protected LocationWidgetComparator getLocationWidgetComparator() {
    return LocationWidgetComparator.BOTTOM_RIGHT_COMPARATOR;
  }

  // TODO remove after enhancement for issue 1112 provides InsertPanel interface
  // http://code.google.com/p/google-web-toolkit/issues/detail?id=1112
  @Override
  protected void insert(Widget widget, int beforeIndex) {
    dropTarget.insert(widget, beforeIndex);
  }

  @Override
  protected Widget newPositioner(DragContext context) {
    HTML positioner = new HTML("&#x203B;");
    positioner.addStyleName(CSS_DRAGDROP_FLOW_PANEL_POSITIONER);
    return positioner;
  }
}
