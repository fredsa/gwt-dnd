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
import com.allen_sauer.gwt.dragdrop.demo.client.example.DraggableFactory;
import com.allen_sauer.gwt.dragdrop.demo.client.example.absolute.AbsolutePositionExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.bin.BinExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.draghandle.DragHandleExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.duallist.DualListExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.flextable.FlexTableRowExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.flowpanel.FlowPanelExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.grid.GridConstrainedExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.indexedpanel.IndexedPanelExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.matryoshka.MatryoshkaExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.nooverlap.NoOverlapExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.puzzle.PuzzleExample;
import com.allen_sauer.gwt.dragdrop.demo.client.example.window.WindowExample;
import com.allen_sauer.gwt.dragdrop.demo.client.util.GWTUtil;

/**
 * EntryPoint class for demonstrating and testing gwt-dnd.
 */
public final class DragDropDemo implements EntryPoint {
  public static final String DEMO_CLIENT_PACKAGE = GWTUtil.getPackageName(DragDropDemo.class);
  private static final String CSS_DEMO_BOUNDARY = "demo-boundary";
  private static final String CSS_DEMO_EVENT_TEXT_AREA = "demo-event-text-area";
  private static final String CSS_DEMO_MAIN_BOUNDARY_PANEL = "demo-main-boundary-panel";

  private PickupDragController dragController;

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
        text = text.replaceAll(" ", "&nbsp;");
        dialogBox.setHTML("<pre>" + text + "</pre>");
        dialogBox.center();
      }
    });

    // use a deferred command so that the handler catches onModuleLoad2() exceptions
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        onModuleLoad2();
      }
    });
  }

  private Widget createDraggable() {
    return DraggableFactory.createDraggableRedBox(dragController);
  }

  private void onModuleLoad2() {
    // create the main common boundary panel to which drag operations will be restricted
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.addStyleName(CSS_DEMO_MAIN_BOUNDARY_PANEL);
    boundaryPanel.setPixelSize(950, 500);

    // instantiate the common drag controller used the less specific examples
    dragController = new PickupDragController(boundaryPanel, true);

    RootPanel.get().add(new HTML("<p>Here's the <a href='http://code.google.com/p/gwt-dnd/'>gwt-dnd</a> library in action.</p>"));

    // Add radio buttons to select draggable behavior
    BehaviorPanel behaviorListBox = new BehaviorPanel(dragController);
    RootPanel.get().add(behaviorListBox);

    // Umbrella example illustrating basic drag and drop behavior
    HTML boundaryDescription = ExampleTabPanel.describe(new Class[] {
        DragDropDemo.class, PickupDragController.class, BoundaryDropController.class,},
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
    ExampleTabPanel examples = new ExampleTabPanel(2);
    examples.setWidth("500px");
    boundaryPanel.add(examples, 200, 10);

    // text area to log drag events as they are triggered
    final HTML eventTextArea = new HTML();
    eventTextArea.addStyleName(CSS_DEMO_EVENT_TEXT_AREA);
    eventTextArea.setSize(boundaryPanel.getOffsetWidth() + "px", "10em");
    RootPanel.get().add(new HTML("<br>Events received by registered <code>DragHandler</code>s"));
    RootPanel.get().add(eventTextArea);

    // instantiate shared drag handler to listen for events
    DemoDragHandler demoDragHandler = new DemoDragHandler(eventTextArea);
    dragController.addDragHandler(demoDragHandler);

    // add our individual examples
    examples.add(0, new BinExample(dragController));
    examples.add(0, new AbsolutePositionExample(dragController));
    examples.add(0, new GridConstrainedExample(dragController));
    examples.add(0, new FlowPanelExample(dragController));
    examples.add(0, new IndexedPanelExample(demoDragHandler));
    examples.add(0, new NoOverlapExample(dragController));
    examples.add(0, new FlexTableRowExample(demoDragHandler));

    examples.add(1, new WindowExample(demoDragHandler));
    examples.add(1, new DragHandleExample(demoDragHandler));
    examples.add(1, new DualListExample(demoDragHandler));
    examples.add(1, new PuzzleExample(demoDragHandler));
    examples.add(1, new MatryoshkaExample(demoDragHandler));

    // select the first example
    examples.selectTab(0, 0);
  }
}
