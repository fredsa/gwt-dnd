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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.demo.client.example.AbsolutePositionExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.FlexTableRowExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.GridConstrainedExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.IndexedExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.NoOverlapExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.TrashBinExample;
import com.allen_sauer.gwt.dragdrop.demo.client.util.DebugUtil;

/**
 * EntryPoint class for testing Drag and Drop library.
 * 
 */
public class DragDropDemo implements EntryPoint {

  private static final String STYLE_DEMO_BOUNDRY = "demo-boundry";

  private DragController dragController;

  // TODO add demo drag or drop event veto
  public void onModuleLoad() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable e) {
        DebugUtil.debug(e);
      }
    });

    AbsolutePanel boundryPanel = new AbsolutePanel();
    dragController = new DragController(boundryPanel);

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
        "Most of our example drag operations are constrained to the panel below. Try drag one of the widgets outside this area.");
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

    examples.add(new TrashBinExample(dragController));
    examples.add(new AbsolutePositionExample(dragController));
    examples.add(new GridConstrainedExample(dragController));
    examples.add(new IndexedExample(dragController));
    examples.add(new NoOverlapExample(dragController));
    examples.add(new FlexTableRowExample(dragController));

    examples.selectTab(0);
  }

  private Widget createDraggable() {
    Widget redBox = new RedBoxDraggableWidget();
    dragController.makeDraggable(redBox);
    return redBox;
  }

}
