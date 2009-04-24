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
package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dragdrop.demo.client.example.AbsolutePositionExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.GridConstrainedExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.NoOverlapExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.bin.BinExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.draghandle.DragHandleExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.duallist.DualListExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.flextable.FlexTableRowExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.flowpanel.FlowPanelExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.indexedpanel.IndexedPanelExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.resize.ResizeExample;

/**
 * EntryPoint class for demonstrating and testing gwt-dnd.
 */
public final class DragDropDemo implements EntryPoint {
  private static final String CSS_DEMO_BOUNDARY = "demo-boundary";
  private static final String CSS_DEMO_MAIN_BOUNDARY_PANEL = "demo-main-boundary-panel";
  private static final String CSS_DEMO_EVENT_TEXT_AREA = "demo-event-text-area";

  private PickupDragController dragController;

  /**
   * Initialize demonstration application.
   */
  public void onModuleLoad() {
    // set uncaught exception handler
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable throwable) {
        String text = "Uncaught exception: ";
        while (throwable != null) {
          StackTraceElement[] stackTraceElements = throwable.getStackTrace();
          text += new String(throwable.toString() + "\n");
          for (int i = 0; i < stackTraceElements.length; i++) {
            text += "    at " + stackTraceElements[i] + "\n";
          }
          throwable = throwable.getCause();
          if (throwable != null) {
            text += "Caused by: ";
          }
        }
        DialogBox dialogBox = new DialogBox(true);
        DOM.setStyleAttribute(dialogBox.getElement(), "backgroundColor", "#ABCDEF");
        System.err.print(text);
        text = text.replaceAll(" ", "&nbsp;");
        dialogBox.setHTML("<pre>" + text + "</pre>");
        dialogBox.show();
      }
    });

    // use a deferred command so that the handler catches onModuleLoad2() exceptions
    DeferredCommand.add(new Command() {
      public void execute() {
        onModuleLoad2();
      }
    });
  }

  public void onModuleLoad2() {
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.addStyleName(CSS_DEMO_MAIN_BOUNDARY_PANEL);
    dragController = new PickupDragController(boundaryPanel, true);

    boundaryPanel.setPixelSize(950, 500);
    //    RootPanel.get().add(new HTML("<h3>Drag-and-Drop Examples</h3>"));
    RootPanel.get().add(
        new HTML("<p>Here's the <a href='http://code.google.com/p/gwt-dnd/'>gwt-dnd</a> library in action. "
            + "You will find each of the included <code>DropContoller</code>s demonstrated.</p>"));

    // Provides radio buttons to select draggable behavior
    BehaviorPanel behaviorListBox = new BehaviorPanel(dragController);
    RootPanel.get().add(behaviorListBox);

    // Example: BoundaryDropController
    HTML boundaryDescription = ExampleTabPanel.describe(BoundaryDropController.class,
        "Most of our example drag operations are constrained to the panel below. Try drag one of the widgets outside this area.");
    boundaryDescription.addStyleName(CSS_DEMO_BOUNDARY);
    RootPanel.get().add(boundaryDescription);
    RootPanel.get().add(boundaryPanel);

    // Create some draggable widgets to play with
    boundaryPanel.add(createDraggable(), 20, 100);
    boundaryPanel.add(createDraggable(), 20, 200);
    boundaryPanel.add(createDraggable(), 40, 240);
    boundaryPanel.add(createDraggable(), 60, 280);

    // TabPanel to hold our examples
    ExampleTabPanel examples = new ExampleTabPanel();
    examples.setWidth("500px");
    boundaryPanel.add(examples, 200, 10);

    final HTML eventTextArea = new HTML();
    eventTextArea.addStyleName(CSS_DEMO_EVENT_TEXT_AREA);
    eventTextArea.setSize(boundaryPanel.getOffsetWidth() + "px", "10em");

    RootPanel.get().add(new HTML("<br>Events received by registered <code>DragHandler</code>s"));
    RootPanel.get().add(eventTextArea);

    DemoDragHandler demoDragHandler = new DemoDragHandler(eventTextArea);
    dragController.addDragHandler(demoDragHandler);

    examples.add(new BinExample(dragController));
    examples.add(new AbsolutePositionExample(dragController));
    examples.add(new GridConstrainedExample(dragController));
    examples.add(new FlowPanelExample(dragController));
    examples.add(new IndexedPanelExample(demoDragHandler));
    examples.add(new NoOverlapExample(dragController));
    examples.add(new FlexTableRowExample(demoDragHandler));
    examples.add(new ResizeExample(demoDragHandler));
    examples.add(new DragHandleExample(demoDragHandler));
    examples.add(new DualListExample(demoDragHandler));

    examples.selectTab(0);
  }

  private Widget createDraggable() {
    Widget redBox = new RedBoxDraggableWidget();
    dragController.makeDraggable(redBox);
    return redBox;
  }
}
