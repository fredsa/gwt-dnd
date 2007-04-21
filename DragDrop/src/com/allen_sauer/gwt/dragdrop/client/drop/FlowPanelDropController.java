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

import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;

/**
 * A {@link DropController} for instances of {@link FlowPanel}.
 * 
 * @see IndexedDropController
 * 
 * TODO VerticalPanel performance is slow because of positioner DOM manipulation
 */
public class FlowPanelDropController extends AbstractIndexedDropController {

  private static final String STYLE_DRAGDROP_FLOW_PANEL_POSITIONER = "dragdrop-flow-panel-positioner";

  private FlowPanel dropTarget;

  /**
   * @see IndexedDropController#IndexedDropController(com.google.gwt.user.client.ui.IndexedPanel)
   * 
   * @param dropTarget
   */
  public FlowPanelDropController(FlowPanel dropTarget) {
    super(dropTarget);
    if (!(dropTarget instanceof IndexedFlowPanel)) {
      throw new IllegalArgumentException(GWT.getTypeName(dropTarget) + " is not currently supported by this controller");
    }
    this.dropTarget = dropTarget;
  }

  public Class getControllerClass() {
    return FlowPanelDropController.class;
  }

  // TODO remove after enhancement for issue 616
  // http://code.google.com/p/google-web-toolkit/issues/detail?id=616
  protected void insert(Widget widget, int beforeIndex) {
    if (dropTarget instanceof IndexedFlowPanel) {
      ((IndexedFlowPanel) dropTarget).insert(widget, beforeIndex);
    } else {
      throw new RuntimeException("Method insert(Widget widget, int beforeIndex) not supported by " + GWT.getTypeName(dropTarget));
    }
  }

  protected Widget newPositioner(Widget reference) {
    HTML positioner = new HTML("&#x203B;");
    positioner.addStyleName(STYLE_DRAGDROP_FLOW_PANEL_POSITIONER);
    return positioner;
  }
}
