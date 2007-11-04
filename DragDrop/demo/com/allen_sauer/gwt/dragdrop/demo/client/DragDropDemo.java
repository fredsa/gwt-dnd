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
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
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
import com.allen_sauer.gwt.dragdrop.demo.client.example.resetcache.ResetCacheExample;
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
  private static final String DEMO_MAIN_PANEL = "demo-main-panel";

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
        System.err.print(text);
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
    RootPanel mainPanel = RootPanel.get(DEMO_MAIN_PANEL);
    DOM.setInnerHTML(mainPanel.getElement(), "");

    // create the main common boundary panel to which drag operations will be restricted
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.addStyleName(CSS_DEMO_MAIN_BOUNDARY_PANEL);
    boundaryPanel.setPixelSize(900, 600);

    // instantiate the common drag controller used the less specific examples
    dragController = new PickupDragController(boundaryPanel, true);

    mainPanel.add(new HTML("<p>Here's the <a href='http://code.google.com/p/gwt-dnd/'>gwt-dnd</a> 2.x library in action.</p>"));

    // Umbrella example illustrating basic drag and drop behavior
    HTML boundaryDescription = ExampleTabPanel.describe(new Class[] {
        DragDropDemo.class, PickupDragController.class, BoundaryDropController.class,},
        "Most of our example drag operations are constrained to the panel below."
            + " Try to drag one of the widgets outside this area.");
    boundaryDescription.addStyleName(CSS_DEMO_BOUNDARY);
    mainPanel.add(boundaryDescription);
    mainPanel.add(boundaryPanel);

    // Add configuration panel for main drag controller
    VerticalPanel configurationPanel = new VerticalPanel();
    configurationPanel.setWidth("200px");
    configurationPanel.add(new DragProxyBehaviorPanel(dragController));
    configurationPanel.add(new TargetSelectionBehaviorPanel(dragController));
    configurationPanel.add(new ConstrainedToBoundaryBehaviorPanel(dragController));
    boundaryPanel.add(configurationPanel, 10, 0);

    // Create some draggable widgets to play with
    boundaryPanel.add(createDraggable(), 100, 330);
    boundaryPanel.add(createDraggable(), 20, 350);
    boundaryPanel.add(createDraggable(), 40, 390);
    boundaryPanel.add(createDraggable(), 60, 430);

    // TabPanel to hold our examples
    ExampleTabPanel examples = new ExampleTabPanel(7);
    examples.setWidth("650px");
    boundaryPanel.add(examples, 220, 10);

    // text area to log drag events as they are triggered
    final HTML eventTextArea = new HTML();
    eventTextArea.addStyleName(CSS_DEMO_EVENT_TEXT_AREA);
    eventTextArea.setSize(boundaryPanel.getOffsetWidth() + "px", "10em");
    mainPanel.add(new HTML("<br>Events received by registered <code>DragHandler</code>s"));
    mainPanel.add(eventTextArea);

    // instantiate shared drag handler to listen for events
    DemoDragHandler demoDragHandler = new DemoDragHandler(eventTextArea);
    dragController.addDragHandler(demoDragHandler);

    // add our individual examples
    examples.add(new BinExample(dragController));
    examples.add(new AbsolutePositionExample(dragController));
    examples.add(new GridConstrainedExample(dragController));
    examples.add(new FlowPanelExample(dragController));

    examples.add(new IndexedPanelExample(demoDragHandler));
    examples.add(new NoOverlapExample(dragController));
    examples.add(new FlexTableRowExample(demoDragHandler));
    examples.add(new WindowExample(demoDragHandler));

    examples.add(new DragHandleExample(demoDragHandler));
    examples.add(new DualListExample(demoDragHandler));
    examples.add(new PuzzleExample(demoDragHandler));
    examples.add(new MatryoshkaExample(demoDragHandler));

    examples.add(new ResetCacheExample(dragController));

    // select the first example
    examples.selectTab(Random.nextInt(examples.getTabCount() - 1));
  }
}
