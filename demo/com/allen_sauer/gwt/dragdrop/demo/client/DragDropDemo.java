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

  private DragController dragController;
  private int draggableOffsetHeight;
  private int draggableOffsetWidth;

  public void onModuleLoad() {
    AbsolutePanel boundryPanel = new AbsolutePanel();
    this.dragController = new DragController(boundryPanel);

    determineRedBoxDimensions();
    boundryPanel.setPixelSize(750, 500);
    RootPanel.get().add(new HTML("<h3>Drag-and-Drop Examples</h3>"));
    RootPanel.get().add(
        new HTML("<p>Here's the <a href='http://code.google.com/p/gwt-dnd/'>gwt-dnd</a> library in action. "
            + "You will find each of the included <code>DropContoller</code>s demonstrated.</p>"));

    HTML boundryDescription = ExampleTabPanel.describe("BoundryDropController",
        "All our example drag operations are constrained to the panel below.");
    boundryDescription.addStyleName(STYLE_BOUNDRY);
    RootPanel.get().add(boundryDescription);
    RootPanel.get().add(boundryPanel);

    // Contrains all example drag operations to boundry panel
    BoundryDropController boundryDropController = new BoundryDropController(boundryPanel);

    // Create some draggable widgets to play with
    boundryDropController.drop(createDraggable(boundryPanel), 20, 100);
    boundryDropController.drop(createDraggable(boundryPanel), 20, 200);
    boundryDropController.drop(createDraggable(boundryPanel), 40, 240);
    boundryDropController.drop(createDraggable(boundryPanel), 60, 280);

    // TabPanel to hold our examples
    ExampleTabPanel dropTargets = new ExampleTabPanel();
    boundryPanel.add(dropTargets, 170, 10);

    // Example 1: TrashBinDropController
    AbsolutePanel containingPanel = new AbsolutePanel();
    TrashBin simpleDropTarget = new TrashBin(120, 120);
    containingPanel.add(simpleDropTarget);
    new TrashBinDropController(simpleDropTarget);
    dropTargets.add(containingPanel, "TrashBinDropController",
        "Classic drop target which simply recognizes when a draggable widget is dropped on it.");
    AbsolutePositionDropController controller = new AbsolutePositionDropController(containingPanel);
    controller.drop(createDraggable(boundryPanel), 200, 20);
    controller.drop(createDraggable(boundryPanel), 240, 50);
    controller.drop(createDraggable(boundryPanel), 190, 100);

    // Example 2: AbsolutePositionDropController
    AbsolutePanel positioningDropTarget = new AbsolutePanel();
    AbsolutePositionDropController absolutePositionDropController = new AbsolutePositionDropController(positioningDropTarget);
    positioningDropTarget.setPixelSize(400, 150);
    dropTargets.add(positioningDropTarget, "AbsolutePositionDropController",
        "Draggable widgets can be placed anywhere on the grey drop target.");
    absolutePositionDropController.drop(createDraggable(boundryPanel), 10, 30);
    absolutePositionDropController.drop(createDraggable(boundryPanel), 60, 8);
    absolutePositionDropController.drop(createDraggable(boundryPanel), 190, 60);

    // Example 3: GridConstrainedDropController
    AbsolutePanel gridConstrainedDropTarget = new AbsolutePanel();
    dropTargets.add(gridConstrainedDropTarget, "GridConstrainedDropController", "Drops (moves) are constrained to a ("
        + this.draggableOffsetWidth + " x " + this.draggableOffsetHeight + ") grid on the grey drop target.");
    GridConstrainedDropController gridConstrainedDropController = new GridConstrainedDropController(gridConstrainedDropTarget,
        this.draggableOffsetWidth, this.draggableOffsetHeight);
    gridConstrainedDropTarget.setPixelSize(this.draggableOffsetWidth * 6, this.draggableOffsetHeight * 2);
    gridConstrainedDropController.drop(createDraggable(boundryPanel), 0, 0);
    gridConstrainedDropController.drop(createDraggable(boundryPanel), this.draggableOffsetWidth, this.draggableOffsetHeight);

    // Example 4: IndexedDropController
    IndexedFlowPanel flowPanelDropTarget = new IndexedFlowPanel();
    dropTargets.add(flowPanelDropTarget, "IndexedDropController",
        "Allows drop to occur anywhere among the children of a supported <code>IndexedPanel</code>.");
    IndexedDropController indexedDropController = new IndexedDropController(flowPanelDropTarget);
    for (int i = 1; i <= 5; i++) {
      Label label = new Label("Draggable child #" + i);
      label.addStyleName("flow-label");
      dragController.makeDraggable(label);
      indexedDropController.drop(label);
    }
    indexedDropController.drop(createDraggable(boundryPanel));

    // Example 5: NoOverlapDropController
    AbsolutePanel noOverlapDropTarget = new AbsolutePanel();
    dropTargets.add(noOverlapDropTarget, "NoOverlapDropController",
        "Widgets cannot be dropped on top of (overlapping) other dropped widgets");
    NoOverlapDropController noOverlapDropController = new NoOverlapDropController(noOverlapDropTarget);
    noOverlapDropTarget.setPixelSize(400, 150);
    noOverlapDropController.drop(createDraggable(boundryPanel), 10, 10);
    noOverlapDropController.drop(createDraggable(boundryPanel), 90, 60);
    noOverlapDropController.drop(createDraggable(boundryPanel), 190, 50);

    // Widget.addDragAndDropListener(new DragAndDropListener() {
    //
    // public boolean onPreDragStart(Widget draggable) {
    // UIUtil.debug("onPreDragStart()");
    // return true;
    // }
    //
    // public void onDragStart(Widget draggable) {
    // UIUtil.debug("onDragStart()");
    // }
    //
    // public void onDrop(Widget draggable, Panel dropTargetPanel) {
    // UIUtil.debug("onDrop()");
    // }
    //
    // });

    dropTargets.selectTab(1);
  }

  private Widget createDraggable(AbsolutePanel boundryPanel) {
    RedBoxDraggableWidget redBox = new RedBoxDraggableWidget();
    dragController.makeDraggable(redBox);
    return redBox;
  }

  private void determineRedBoxDimensions() {
    RedBoxDraggableWidget redBox = new RedBoxDraggableWidget();
    RootPanel.get().add(redBox, 0, 0);
    this.draggableOffsetWidth = redBox.getOffsetWidth();
    this.draggableOffsetHeight = redBox.getOffsetHeight();
    redBox.removeFromParent();
  }

}
