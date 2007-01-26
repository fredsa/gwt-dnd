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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.GridConstrainedDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;

/**
 * EntryPoint class for testing Drag and Drop library.
 * 
 */
public class DragDropDemo implements EntryPoint {

  private final int draggableSize = 65;

  public void onModuleLoad() {
    RedBoxDraggablePanel draggable;
    AbsolutePanel boundryPanel = new AbsolutePanel();
    boundryPanel.setPixelSize(750, 500);
    RootPanel.get().add(new HTML("<h3>Drag-and-Drop Examples</h3>"));
    RootPanel.get().add(
        new HTML(
            "<p>Here's the <a href='http://code.google.com/p/gwt-dnd/'>gwt-dnd</a> library in action. "
                + "You will find each of the included <code>DropContoller</code>s demonstrated.</p>"));

    HTML boundryDescription = ExampleTabPanel.describe("BoundryDropController",
        "All our example drag operations are constrained to the panel below.");
    boundryDescription.addStyleName("boundry");
    RootPanel.get().add(boundryDescription);
    RootPanel.get().add(boundryPanel);

    draggable = createDraggable(boundryPanel);
    RootPanel.get().add(draggable);
    boundryPanel.add(draggable, 20, 20);
    int draggableOffsetWidth = draggable.getOffsetWidth();
    int draggableOffsetHeight = draggable.getOffsetHeight();
    boundryPanel.add(createDraggable(boundryPanel), 20, 100);
    boundryPanel.add(createDraggable(boundryPanel), 20, 200);
    boundryPanel.add(createDraggable(boundryPanel), 40, 240);
    boundryPanel.add(createDraggable(boundryPanel), 60, 280);

    ExampleTabPanel dropTargets = new ExampleTabPanel();

    // Example 1: TrashBinDropController
    TrashBinPanel simpleDropTarget = new TrashBinPanel(120, 120);
    new TrashBinDropController(simpleDropTarget);
    dropTargets.add(
        simpleDropTarget,
        "TrashBinDropController",
        "Classic drop target which simply recognizes when a draggable widget is dropped on it.");

    // Example 2: AbsolutePositionDropController
    AbsolutePanel positioningDropTarget = new AbsolutePanel();
    new AbsolutePositionDropController(positioningDropTarget);
    positioningDropTarget.setPixelSize(400, 150);
    dropTargets.add(positioningDropTarget, "AbsolutePositionDropController",
        "Draggable widgets can be placed anywhere on the grey drop target.");
    positioningDropTarget.add(createDraggable(boundryPanel), 10, 40);
    positioningDropTarget.add(createDraggable(boundryPanel), 60, 8);
    positioningDropTarget.add(createDraggable(boundryPanel), 190, 70);

    // Example 3: GridConstrainedDropController
    draggable = createDraggable(boundryPanel);
    AbsolutePanel gridConstrainedDropTarget = new AbsolutePanel();
    dropTargets.add(gridConstrainedDropTarget, "GridConstrainedDropController",
        "Drops (moves) are constrained to a (" + draggableOffsetWidth + " x "
            + draggableOffsetHeight + ") grid on the grey drop target.");
    gridConstrainedDropTarget.add(draggable);
    draggable = createDraggable(boundryPanel);
    gridConstrainedDropTarget.add(draggable, draggableOffsetWidth,
        draggableOffsetHeight);
    new GridConstrainedDropController(gridConstrainedDropTarget,
        draggableOffsetWidth, draggableOffsetHeight);
    gridConstrainedDropTarget.setPixelSize(draggableOffsetWidth * 6,
        draggableOffsetHeight * 2);

    // Example 4: IndexedDropController
    IndexedFlowPanel flowPanelDropTarget = new IndexedFlowPanel();
    new IndexedDropController(flowPanelDropTarget);
    for (int i = 1; i <= 5; i++) {
      Label label = new Label("child #" + i);
      label.addStyleName("flow-label");
      flowPanelDropTarget.add(label);
      new DragAndDropController(label, boundryPanel);
    }
    dropTargets.add(
        flowPanelDropTarget,
        "IndexedDropController",
        "Allows drop to occur anywhere among the children of a supported <code>IndexedPanel</code>.");
    flowPanelDropTarget.add(createDraggable(boundryPanel));

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

    // TODO be more intelligent about what to prevent
    DOM.addEventPreview(new EventPreview() {
      public boolean onEventPreview(Event event) {
        switch (DOM.eventGetType(event)) {
          case Event.ONMOUSEDOWN:
            DOM.eventPreventDefault(event);
        }

        return true;
      }
    });

    dropTargets.selectTab(1);
    boundryPanel.add(dropTargets, 170, 10);
  }

  private RedBoxDraggablePanel createDraggable(AbsolutePanel boundryPanel) {
    return new RedBoxDraggablePanel(boundryPanel, draggableSize, draggableSize);
  }

}
