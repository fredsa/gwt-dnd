/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dnd.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dnd.demo.client.example.DraggableFactory;
import com.allen_sauer.gwt.dnd.demo.client.example.absolute.AbsolutePositionExample;
import com.allen_sauer.gwt.dnd.demo.client.example.bin.BinExample;
import com.allen_sauer.gwt.dnd.demo.client.example.draghandle.DragHandleExample;
import com.allen_sauer.gwt.dnd.demo.client.example.duallist.DualListExample;
import com.allen_sauer.gwt.dnd.demo.client.example.flextable.FlexTableRowExample;
import com.allen_sauer.gwt.dnd.demo.client.example.flowpanel.FlowPanelExample;
import com.allen_sauer.gwt.dnd.demo.client.example.grid.GridConstrainedExample;
import com.allen_sauer.gwt.dnd.demo.client.example.insertpanel.InsertPanelExample;
import com.allen_sauer.gwt.dnd.demo.client.example.matryoshka.MatryoshkaExample;
import com.allen_sauer.gwt.dnd.demo.client.example.palette.PaletteExample;
import com.allen_sauer.gwt.dnd.demo.client.example.puzzle.PuzzleExample;
import com.allen_sauer.gwt.dnd.demo.client.example.resetcache.ResetCacheExample;
import com.allen_sauer.gwt.dnd.demo.client.example.window.WindowExample;

/**
 * EntryPoint class for demonstrating and testing gwt-dnd.
 */
public final class DragDropDemo implements EntryPoint {

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
          text += throwable.toString() + "\n";
          for (StackTraceElement element : stackTraceElements) {
            text += "    at " + element + "\n";
          }
          throwable = throwable.getCause();
          if (throwable != null) {
            text += "Caused by: ";
          }
        }
        DialogBox dialogBox = new DialogBox(true, false);
        dialogBox.getElement().getStyle().setProperty("backgroundColor", "#ABCDEF");
        System.err.print(text);
        text = text.replaceAll(" ", "&nbsp;");
        dialogBox.setHTML("<pre>" + text + "</pre>");
        dialogBox.center();
      }
    });

    // use a deferred command so that the handler catches onModuleLoad2() exceptions
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
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
    dragController.setBehaviorMultipleSelection(false);

    mainPanel.add(new HTML(
        "<div style='font-weight: bold; font-size: 1.2em;'><a href='http://code.google.com/p/gwt-dnd/'>gwt-dnd</a>"
            + " - Drag-and-Drop for your Google Web Toolkit projects.</div>"
            + "<div style='font-style: italic; margin-bottom: 1em;'>by Fred Sauer</div>"));

    // Umbrella example illustrating basic drag and drop behavior
    HTML boundaryDescription = ExampleTabPanel.describe(new Class[] {
        DragDropDemo.class, PickupDragController.class, BoundaryDropController.class,},
        "Most of the example drag operations are constrained to the panel below."
            + " Try to drag one of the widgets outside the area below.");
    boundaryDescription.addStyleName(CSS_DEMO_BOUNDARY);
    mainPanel.add(boundaryDescription);
    mainPanel.add(boundaryPanel);

    // Add configuration panel for main drag controller
    VerticalPanel configurationPanel = new VerticalPanel();
    configurationPanel.setWidth("200px");
    configurationPanel.add(new MultipleSelectionBehaviorPanel(dragController));
    configurationPanel.add(new DragStartSensitivityBehaviorPanel(dragController));
    configurationPanel.add(new DragProxyBehaviorPanel(dragController));
    configurationPanel.add(new ConstrainedToBoundaryBehaviorPanel(dragController));
    boundaryPanel.add(configurationPanel, 10, 0);

    // Create some draggable widgets to play with
    boundaryPanel.add(createDraggable(), 100, 430);
    boundaryPanel.add(createDraggable(), 20, 450);
    boundaryPanel.add(createDraggable(), 40, 480);
    boundaryPanel.add(createDraggable(), 60, 510);

    // TabPanel to hold our examples
    final ExampleTabPanel examples = new ExampleTabPanel(7);
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

    examples.add(new InsertPanelExample(demoDragHandler));
    examples.add(new FlexTableRowExample(demoDragHandler));
    examples.add(new WindowExample(demoDragHandler));
    examples.add(new DragHandleExample(demoDragHandler));

    examples.add(new DualListExample(demoDragHandler));
    examples.add(new PuzzleExample(demoDragHandler));
    examples.add(new MatryoshkaExample(demoDragHandler));
    examples.add(new ResetCacheExample(dragController));

    examples.add(new PaletteExample(demoDragHandler));

    mainPanel.add(
        new HTML(
                "<div style='color: gray; margin-top: 1em;'>Demo created with gwt-dnd @GWT_DND_VERSION@ and GWT " + GWT.getVersion() + "</div>"));

    final String initToken = History.getToken();
    if (initToken.length() == 0) {
      // select a random example
      examples.selectTab(Random.nextInt(examples.getTabCount() - 1));
    } else {
      DeferredCommand.addCommand(new Command() {
        public void execute() {
          examples.selectTabByHistoryToken(initToken);
        }
      });
    }
  }
}
