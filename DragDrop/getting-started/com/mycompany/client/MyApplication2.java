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
package com.mycompany.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;

/**
 * Illustrative example.
 */
public class MyApplication2 implements EntryPoint {

  /**
   * Main entry point method.
   */
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

  /**
   * Deferred initialization method, called from {@link #onModuleLoad()}.
   */
  private void onModuleLoad2() {
    // Create a boundary panel to constrain all drag operations
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(400, 300);
    boundaryPanel.addStyleName("getting-started-blue");

    // Create a drop target on which we can drop labels
    AbsolutePanel targetPanel = new AbsolutePanel();
    targetPanel.setPixelSize(300, 200);
    targetPanel.addStyleName("getting-started-blue");

    // Add both panels to the root panel
    RootPanel.get().add(boundaryPanel);
    boundaryPanel.add(targetPanel, 40, 40);

    // Create a DragController for each logical area where a set of draggable
    // widgets and drop targets will be allowed to interact with one another.
    PickupDragController dragController = new PickupDragController(boundaryPanel, true);

    // Positioner is always constrained to the boundary panel
    // Use 'true' to also constrain the draggable or drag proxy to the boundary panel
    dragController.setBehaviorConstrainedToBoundaryPanel(false);

    // Allow multiple widgets to be selected at once using CTRL-click
    dragController.setBehaviorMultipleSelection(true);

    // create a DropController for each drop target on which draggable widgets
    // can be dropped
    DropController dropController = new AbsolutePositionDropController(targetPanel);

    // Don't forget to register each DropController with a DragController
    dragController.registerDropController(dropController);

    // create a few randomly placed draggable labels
    for (int i = 1; i <= 5; i++) {
      // create a label and give it style
      Label label = new Label("Label #" + i, false);
      label.addStyleName("getting-started-label");

      // add it to the DOM so that offset width/height becomes available
      targetPanel.add(label, 0, 0);

      // determine random label location within target panel
      int left = Random.nextInt(DOMUtil.getClientWidth(targetPanel.getElement())
          - label.getOffsetWidth());
      int top = Random.nextInt(DOMUtil.getClientHeight(targetPanel.getElement())
          - label.getOffsetHeight());

      // move the label
      targetPanel.setWidgetPosition(label, left, top);

      // make the label draggable
      dragController.makeDraggable(label);
    }
  }
}
