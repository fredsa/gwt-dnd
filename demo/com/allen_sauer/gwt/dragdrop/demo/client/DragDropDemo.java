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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackPanel;

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

  private int counter;

  public void onModuleLoad() {
    AbsolutePanel boundryPanel = new AbsolutePanel();
    boundryPanel.setPixelSize(750, 500);
    RootPanel.get().add(new HTML("<h1>Drag-and-Drop Examples</h1>"));
    RootPanel.get().add(
        new HTML(
            "<p>Here's the <a href='http://code.google.com/p/gwt-dnd/'>gwt-dnd</a> library in action. "
                + "You will find each of the included <code>DropContoller</code>s demonstrated.</p>"));

    HTML example1 = new HTML(describe("BoundryDropController",
        "All our example drag operations are constrained to this panel."));
    example1.addStyleName("example1");
    RootPanel.get().add(example1);
    RootPanel.get().add(boundryPanel);

    boundryPanel.add(new RedBoxDraggablePanel(boundryPanel, 60, 60), 20, 20);
    boundryPanel.add(new RedBoxDraggablePanel(boundryPanel, 60, 60), 20, 100);
    boundryPanel.add(new RedBoxDraggablePanel(boundryPanel, 60, 60), 20, 180);
    boundryPanel.add(new RedBoxDraggablePanel(boundryPanel, 60, 60), 20, 200);
    boundryPanel.add(new RedBoxDraggablePanel(boundryPanel, 60, 60), 20, 220);
    boundryPanel.add(new RedBoxDraggablePanel(boundryPanel, 60, 60), 20, 240);
    boundryPanel.add(new RedBoxDraggablePanel(boundryPanel, 60, 60), 20, 260);
    boundryPanel.add(new RedBoxDraggablePanel(boundryPanel, 60, 60), 20, 280);
    boundryPanel.add(new RedBoxDraggablePanel(boundryPanel, 60, 60), 20, 300);

    StackPanel dropTargetsStackPanel = new StackPanel();

    SimplePanel simpleDropTarget = new SimplePanel();
    new TrashBinDropController(simpleDropTarget);
    simpleDropTarget.addStyleName("trashbin");
    simpleDropTarget.setPixelSize(50, 50);
    simpleDropTarget.add(new Label("Trash bin"));
    dropTargetsStackPanel.add(
        simpleDropTarget,
        describe(
            "TrashBinDropController",
            "Classic drop target which simply recognizes when a draggable widget is dropped on it."),
        true);

    AbsolutePanel positioningDropTarget = new AbsolutePanel();
    new AbsolutePositionDropController(positioningDropTarget);
    positioningDropTarget.setPixelSize(400, 100);
    dropTargetsStackPanel.add(positioningDropTarget, describe(
        "AbsolutePositionDropController",
        "Draggable widget can be placed anywhere on this drop target."), true);

    int gridX = 40;
    int gridY = 40;
    AbsolutePanel gridConstrainedDropTarget = new AbsolutePanel();
    new GridConstrainedDropController(gridConstrainedDropTarget, gridX, gridY);
    gridConstrainedDropTarget.setPixelSize(400, 105);
    dropTargetsStackPanel.add(gridConstrainedDropTarget, describe(
        "GridConstrainedDropController", "Drops are constrained to a (" + gridX
            + ", " + gridY + ") grid on this drop target."), true);

    IndexedFlowPanel flowPanelDropTarget = new IndexedFlowPanel();
    new IndexedDropController(flowPanelDropTarget);
    for (int i = 1; i <= 5; i++) {
      Label label = new Label("child #" + i);
      label.addStyleName("flow-label");
      flowPanelDropTarget.add(label);
      new DragAndDropController(label, boundryPanel);
    }
    dropTargetsStackPanel.add(
        flowPanelDropTarget,
        describe(
            "IndexedDropController",
            "Allows drop to occur anywhere among the children of a supported <code>IndexedPanel</code>."),
        true);

    boundryPanel.add(dropTargetsStackPanel, 170, 10);

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
  }

  private String describe(String controllerClassName, String description) {
    return "Example " + ++counter + ": <code>" + controllerClassName
        + "</code><br>\n" + "<i>" + description + "</i>";
  }
}
