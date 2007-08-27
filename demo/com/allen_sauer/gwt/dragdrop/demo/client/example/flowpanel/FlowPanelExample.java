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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.FlowPanelDropController;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;
import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.FlowPanelDropController} example.
 */
public final class FlowPanelExample extends Example {
  private static final String CSS_DEMO_FLOW_PANEL_EXAMPLE = "demo-FlowPanelExample";
  private static final String CSS_DEMO_FLOW_PANEL_EXAMPLE_LABEL = "demo-FlowPanelExample-label";

  private IndexedFlowPanel flowPanelDropTarget;

  public FlowPanelExample(DragController dragController) {
    super(dragController);
    addStyleName(CSS_DEMO_FLOW_PANEL_EXAMPLE);
    flowPanelDropTarget = new IndexedFlowPanel();
    flowPanelDropTarget.setWidth("400px");
    setWidget(flowPanelDropTarget);
    FlowPanelDropController flowPanelDropController = new FlowPanelDropController(flowPanelDropTarget);
    dragController.registerDropController(flowPanelDropController);
  }

  public String getDescription() {
    return "Allows drop to occur anywhere in a <code>IndexedFlowPanel</code>.";
  }

  public Class[] getInvolvedClasses() {
    return new Class[] {FlowPanelExample.class, FlowPanelDropController.class, IndexedFlowPanel.class,};
  }

  protected void onLoad() {
    super.onLoad();
    for (int i = 1; i <= 25; i++) {
      FocusPanel focusPanel = new FocusPanel();
      focusPanel.setStyleName("demo-FlowPanelExample-draggable");

      FlowPanel flowPanel = new FlowPanel();
      focusPanel.add(flowPanel);

      HTML label = new HTML("Draggable&nbsp;#" + i);
      HTML spacer = new HTML(" ");
      label.addStyleName(CSS_DEMO_FLOW_PANEL_EXAMPLE_LABEL);
      flowPanel.add(label);
      flowPanel.add(spacer);
      getDragController().makeDraggable(focusPanel);
      flowPanelDropTarget.add(focusPanel);
    }
  }
}
