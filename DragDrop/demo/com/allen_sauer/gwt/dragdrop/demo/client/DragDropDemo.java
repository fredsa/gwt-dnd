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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.GridConstrainedDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.NoOverlapDropController;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;
import com.allen_sauer.gwt.dragdrop.demo.client.util.DebugUtil;

/**
 * EntryPoint class for testing Drag and Drop library.
 * 
 */
public class DragDropDemo implements EntryPoint {

  private static final String STYLE_DEMO_BOUNDRY = "demo-boundry";
  private static final String STYLE_DEMO_LABEL = "demo-flow-label";

  private DragController dragController;
  private int draggableOffsetHeight;
  private int draggableOffsetWidth;

  // TODO add demo drag or drop event veto
  public void onModuleLoad() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable e) {
        DebugUtil.debug(e);
      }
    });
    AbsolutePanel boundryPanel = new AbsolutePanel();
    dragController = new DragController(boundryPanel);

    determineRedBoxDimensions();
    boundryPanel.setPixelSize(750, 500);
    //    RootPanel.get().add(new HTML("<h3>Drag-and-Drop Examples</h3>"));
    RootPanel.get().add(
        new HTML("<p>Here's the <a href='http://code.google.com/p/gwt-dnd/'>gwt-dnd</a> library in action. "
            + "You will find each of the included <code>DropContoller</code>s demonstrated.</p>"));

    // Provides radio buttons to select draggable behavior
    BehaviorPanel behaviorListBox = new BehaviorPanel(dragController);
    RootPanel.get().add(behaviorListBox);

    // Example: BoundryDropController
    HTML boundryDescription = ExampleTabPanel.describe("BoundryDropController",
        "Most of our example drag operations are constrained to the panel below.");
    boundryDescription.addStyleName(STYLE_DEMO_BOUNDRY);
    RootPanel.get().add(boundryDescription);
    RootPanel.get().add(boundryPanel);

    // Create some draggable widgets to play with
    boundryPanel.add(createDraggable(), 20, 100);
    boundryPanel.add(createDraggable(), 20, 200);
    boundryPanel.add(createDraggable(), 40, 240);
    boundryPanel.add(createDraggable(), 60, 280);

    // TabPanel to hold our examples
    ExampleTabPanel examples = new ExampleTabPanel();
    examples.setWidth("500px");
    boundryPanel.add(examples, 200, 10);

    // Example 1: TrashBinDropController
    AbsolutePanel containingPanel = new AbsolutePanel();
    containingPanel.setPixelSize(400, 200);
    TrashBin trashBin = new TrashBin(120, 120);
    containingPanel.add(trashBin, 30, 30);
    TrashBinDropController trashBinDropController = new TrashBinDropController(trashBin);
    dragController.registerDropController(trashBinDropController);
    examples.add(containingPanel, trashBinDropController,
        "Classic drop target which simply recognizes when a draggable widget is dropped on it.");
    AbsolutePositionDropController controller = new AbsolutePositionDropController(containingPanel);
    controller.drop(createDraggable(), 200, 20);
    controller.drop(createDraggable(), 240, 50);
    controller.drop(createDraggable(), 190, 100);

    // Example 2: AbsolutePositionDropController
    AbsolutePanel positioningDropTarget = new AbsolutePanel();
    positioningDropTarget.setPixelSize(400, 200);
    AbsolutePositionDropController absolutePositionDropController = new AbsolutePositionDropController(positioningDropTarget);
    dragController.registerDropController(absolutePositionDropController);
    examples.add(positioningDropTarget, absolutePositionDropController,
        "Draggable widgets can be placed anywhere on the gray drop target.");
    absolutePositionDropController.drop(createDraggable(), 10, 30);
    absolutePositionDropController.drop(createDraggable(), 60, 8);
    absolutePositionDropController.drop(createDraggable(), 190, 60);

    // Example 3: GridConstrainedDropController
    AbsolutePanel gridConstrainedDropTarget = new AbsolutePanel();
    GridConstrainedDropController gridConstrainedDropController = new GridConstrainedDropController(gridConstrainedDropTarget,
        draggableOffsetWidth, draggableOffsetHeight);
    dragController.registerDropController(gridConstrainedDropController);
    examples.add(gridConstrainedDropTarget, gridConstrainedDropController, "Drops (moves) are constrained to a ("
        + draggableOffsetWidth + " x " + draggableOffsetHeight + ") grid on the gray drop target.");
    gridConstrainedDropTarget.setPixelSize(draggableOffsetWidth * 5, draggableOffsetHeight * 2);
    gridConstrainedDropController.drop(createDraggable(), 0, 0);
    gridConstrainedDropController.drop(createDraggable(), draggableOffsetWidth, draggableOffsetHeight);

    // Example 4: IndexedDropController
    IndexedFlowPanel flowPanelDropTarget = new IndexedFlowPanel();
    flowPanelDropTarget.setWidth("400px");
    IndexedDropController indexedDropController = new IndexedDropController(flowPanelDropTarget);
    dragController.registerDropController(indexedDropController);
    examples.add(flowPanelDropTarget, indexedDropController,
        "Allows drop to occur anywhere among the children of a supported <code>IndexedPanel</code>.");
    for (int i = 1; i <= 5; i++) {
      Label label = new Label("Draggable child #" + i);
      label.addStyleName(STYLE_DEMO_LABEL);
      dragController.makeDraggable(label);
      indexedDropController.drop(label);
    }
    indexedDropController.drop(createDraggable());

    // Example 5: NoOverlapDropController
    AbsolutePanel noOverlapDropTarget = new AbsolutePanel();
    noOverlapDropTarget.setPixelSize(400, 200);
    NoOverlapDropController noOverlapDropController = new NoOverlapDropController(noOverlapDropTarget);
    dragController.registerDropController(noOverlapDropController);
    examples.add(noOverlapDropTarget, noOverlapDropController,
        "Widgets cannot be dropped on top of (overlapping) other dropped widgets");
    noOverlapDropController.drop(createDraggable(), 10, 10);
    noOverlapDropController.drop(createDraggable(), 90, 60);
    noOverlapDropController.drop(createDraggable(), 190, 50);

    // Example 6: TableRowDropController
    AbsolutePanel tableExamplePanel = new AbsolutePanel();
    tableExamplePanel.setPixelSize(450, 400);
    FlexTableRowDragController tableRowDragController = new FlexTableRowDragController(tableExamplePanel);
    DemoFlexTable table1 = new DemoFlexTable(5, 3, tableRowDragController);
    DemoFlexTable table2 = new DemoFlexTable(5, 4, tableRowDragController);
    FlexTableRowDropController flexTableRowDropController1 = new FlexTableRowDropController(table1);
    FlexTableRowDropController flexTableRowDropController2 = new FlexTableRowDropController(table2);
    tableRowDragController.registerDropController(flexTableRowDropController1);
    tableRowDragController.registerDropController(flexTableRowDropController2);
    tableExamplePanel.add(table1, 10, 20);
    tableExamplePanel.add(table2, 230, 40);
    examples.add(tableExamplePanel, flexTableRowDropController1, "Drag <code>FlexTable</code> rows by their drag handle");

    examples.selectTab(0);
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
