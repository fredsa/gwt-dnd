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
package com.allen_sauer.gwt.dragdrop.demo.client.example;

import com.google.gwt.user.client.ui.Label;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController} example.
 */
public final class IndexedExample extends Example {

  private static final String STYLE_DEMO_LABEL = "demo-flow-label";

  private IndexedFlowPanel flowPanelDropTarget;

  public IndexedExample(DragController dragController) {
    super(dragController);
    flowPanelDropTarget = new IndexedFlowPanel();
    flowPanelDropTarget.setWidth("400px");
    setWidget(flowPanelDropTarget);
    IndexedDropController indexedDropController = new IndexedDropController(flowPanelDropTarget);
    dragController.registerDropController(indexedDropController);
  }

  public Class getControllerClass() {
    return IndexedDropController.class;
  }

  public String getDescription() {
    return "Allows drop to occur anywhere among the children of a supported <code>IndexedPanel</code>.";
  }

  protected void onLoad() {
    super.onLoad();
    for (int i = 1; i <= 5; i++) {
      Label label = new Label("Draggable child #" + i);
      label.addStyleName(STYLE_DEMO_LABEL);
      getDragController().makeDraggable(label);
      flowPanelDropTarget.add(label);
    }
    flowPanelDropTarget.add(createDraggable());
  }
}
