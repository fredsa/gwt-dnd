/*
 * Copyright 2006 Fred Sauer
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
package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.BoundryDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.GridConstrainedDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.NoOverlapDropController;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;

/**
 * EntryPoint class for testing Drag and Drop library.
 * 
 */
public class DragDropDemo implements EntryPoint {

  private static final String STYLE_BOUNDRY = "boundry";
  private static final String STYLE_DEMO_LABEL = "flow-label";

  private DragController dragController;
  private int draggableOffsetHeight;
  private int draggableOffsetWidth;

  public void onModuleLoad() {
    AbsolutePanel boundryPanel = new AbsolutePanel();
    dragController = new DragController(boundryPanel);

    determineRedBoxDimensions();
    boundryPanel.setPixelSize(750, 500);
    RootPanel.get().add(new HTML("<h3>Drag-and-Drop Examples</h3>"));
    RootPanel.get().add(
        new HTML("<p>Here's the <a href='http://code.google.com/p/gwt-dnd/'>gwt-dnd</a> library in action. "
            + "You will find each of the included <code>DropContoller</code>s demonstrated.</p>"));

    // Provides radio buttons to select draggable behavior
    BehaviorPanel behaviorListBox = new BehaviorPanel(dragController);
    RootPanel.get().add(behaviorListBox);

    // Add our working example
    HTML boundryDescription = ExampleTabPanel.describe("BoundryDropController",
        "All our example drag operations are constrained to the panel below.");
    boundryDescription.addStyleName(STYLE_BOUNDRY);
    RootPanel.get().add(boundryDescription);
    RootPanel.get().add(boundryPanel);

    // Constrains all example drag operations to boundry panel
    BoundryDropController boundryDropController = new BoundryDropController(boundryPanel);

    // Create some draggable widgets to play with
    boundryDropController.drop(createDraggable(), 20, 100);
    boundryDropController.drop(createDraggable(), 20, 200);
    boundryDropController.drop(createDraggable(), 40, 240);
    boundryDropController.drop(createDraggable(), 60, 280);

    // TabPanel to hold our examples
    ExampleTabPanel examples = new ExampleTabPanel();
    examples.setWidth("500px");
    boundryPanel.add(examples, 200, 10);

    // Example 1: TrashBinDropController
    AbsolutePanel containingPanel = new AbsolutePanel();
    containingPanel.setPixelSize(400, 200);
    TrashBin simpleDropTarget = new TrashBin(120, 120);
    containingPanel.add(simpleDropTarget);
    new TrashBinDropController(simpleDropTarget);
    examples.add(containingPanel, "TrashBinDropController",
        "Classic drop target which simply recognizes when a draggable widget is dropped on it.");
    AbsolutePositionDropController controller = new AbsolutePositionDropController(containingPanel);
    controller.drop(createDraggable(), 200, 20);
    controller.drop(createDraggable(), 240, 50);
    controller.drop(createDraggable(), 190, 100);

    // Example 2: AbsolutePositionDropController
    AbsolutePanel positioningDropTarget = new AbsolutePanel();
    AbsolutePositionDropController absolutePositionDropController = new AbsolutePositionDropController(positioningDropTarget);
    positioningDropTarget.setPixelSize(400, 200);
    examples.add(positioningDropTarget, "AbsolutePositionDropController",
        "Draggable widgets can be placed anywhere on the grey drop target.");
    absolutePositionDropController.drop(createDraggable(), 10, 30);
    absolutePositionDropController.drop(createDraggable(), 60, 8);
    absolutePositionDropController.drop(createDraggable(), 190, 60);

    // Example 3: GridConstrainedDropController
    AbsolutePanel gridConstrainedDropTarget = new AbsolutePanel();
    examples.add(gridConstrainedDropTarget, "GridConstrainedDropController", "Drops (moves) are constrained to a ("
        + draggableOffsetWidth + " x " + draggableOffsetHeight + ") grid on the grey drop target.");
    GridConstrainedDropController gridConstrainedDropController = new GridConstrainedDropController(gridConstrainedDropTarget,
        draggableOffsetWidth, draggableOffsetHeight);
    gridConstrainedDropTarget.setPixelSize(draggableOffsetWidth * 5, draggableOffsetHeight * 2);
    gridConstrainedDropController.drop(createDraggable(), 0, 0);
    gridConstrainedDropController.drop(createDraggable(), draggableOffsetWidth, draggableOffsetHeight);

    // Example 4: IndexedDropController
    IndexedFlowPanel flowPanelDropTarget = new IndexedFlowPanel();
    flowPanelDropTarget.setWidth("400px");
    examples.add(flowPanelDropTarget, "IndexedDropController",
        "Allows drop to occur anywhere among the children of a supported <code>IndexedPanel</code>.");
    IndexedDropController indexedDropController = new IndexedDropController(flowPanelDropTarget);
    for (int i = 1; i <= 5; i++) {
      Label label = new Label("Draggable child #" + i);
      label.addStyleName(STYLE_DEMO_LABEL);
      dragController.makeDraggable(label);
      indexedDropController.drop(label);
    }
    indexedDropController.drop(createDraggable());

    // Example 5: NoOverlapDropController
    AbsolutePanel noOverlapDropTarget = new AbsolutePanel();
    examples.add(noOverlapDropTarget, "NoOverlapDropController",
        "Widgets cannot be dropped on top of (overlapping) other dropped widgets");
    NoOverlapDropController noOverlapDropController = new NoOverlapDropController(noOverlapDropTarget);
    noOverlapDropTarget.setPixelSize(400, 200);
    noOverlapDropController.drop(createDraggable(), 10, 10);
    noOverlapDropController.drop(createDraggable(), 90, 60);
    noOverlapDropController.drop(createDraggable(), 190, 50);

    // TODO add demo drag or drop event veto

    examples.selectTab(1);
  }

  private Widget createDraggable() {
    Widget redBox = new RedBoxDraggableWidget();
    dragController.makeDraggable(redBox);
    return redBox;
  }

  private void determineRedBoxDimensions() {
    RedBoxDraggableWidget redBox = new RedBoxDraggableWidget();
    RootPanel.get().add(redBox, 0, 0);
    draggableOffsetWidth = redBox.getOffsetWidth();
    draggableOffsetHeight = redBox.getOffsetHeight();
    redBox.removeFromParent();
  }

}
