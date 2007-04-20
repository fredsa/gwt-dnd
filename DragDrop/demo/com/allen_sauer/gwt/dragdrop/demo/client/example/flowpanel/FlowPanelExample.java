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
package com.allen_sauer.gwt.dragdrop.demo.client.example.flowpanel;

import com.google.gwt.user.client.ui.HTML;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.FlowPanelDropController;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;
import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.FlowPanelDropController} example.
 */
public final class FlowPanelExample extends Example {

  private static final String STYLE_DEMO_FLOW_LABEL = "demo-flow-label";

  private IndexedFlowPanel flowPanelDropTarget;

  public FlowPanelExample(DragController dragController) {
    super(dragController);
    flowPanelDropTarget = new IndexedFlowPanel();
    flowPanelDropTarget.setWidth("400px");
    setWidget(flowPanelDropTarget);
    FlowPanelDropController flowPanelDropController = new FlowPanelDropController(flowPanelDropTarget);
    dragController.registerDropController(flowPanelDropController);
  }

  public Class getControllerClass() {
    return FlowPanelDropController.class;
  }

  public String getDescription() {
    return "Allows drop to occur anywhere in a <code>IndexedFlowPanel</code>.";
  }

  protected void onLoad() {
    super.onLoad();
    for (int i = 1; i <= 25; i++) {
      HTML html = new HTML("Draggable&nbsp;#" + i + "&#x200B;");
      html.addStyleName(STYLE_DEMO_FLOW_LABEL);
      getDragController().makeDraggable(html);
      flowPanelDropTarget.add(html);
    }
  }
}
