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
package com.allen_sauer.gwt.dnd.test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;

/**
 * EntryPoint class for demonstrating and testing drag-and-drop library.
 */
public final class DragDropTest implements EntryPoint {
  private static native String getCompatMode()
  /*-{
    return $doc.compatMode;
  }-*/;

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

  public void onModuleLoad2() {
    RootPanel.get().add(new HTML("DragDropTest is in <b>" + getCompatMode() + "</b> mode."));

    int depth = 2;

    PickupDragController dragController = new PickupDragController(RootPanel.get(), true);
    ScrollPanel scrollPanel1 = new ScrollPanel();
    DOM.setStyleAttribute(scrollPanel1.getElement(), "position", "relative");
    scrollPanel1.setPixelSize(300, 300);

    ScrollPanel scrollPanel2 = new ScrollPanel();
    DOM.setStyleAttribute(scrollPanel2.getElement(), "position", "relative");
    scrollPanel2.setPixelSize(400, 400);

    AbsolutePanel absolutePanel = new AbsolutePanel();
    absolutePanel.setPixelSize(600, 600);

    AbsolutePositionDropController dropController = new AbsolutePositionDropController(
        absolutePanel);
    dragController.registerDropController(dropController);

    for (int i = 0; i < 10; i++) {
      Label label = new Label("drag me");
      absolutePanel.add(label, i * 60, i * 60);
      dragController.makeDraggable(label);
    }

    RootPanel.get().add(scrollPanel1);

    if (depth >= 1) {
      scrollPanel1.setWidget(absolutePanel);

      scrollPanel1.setHorizontalScrollPosition(50);
      scrollPanel1.setScrollPosition(50);
    }

    if (depth >= 2) {
      scrollPanel1.setWidget(scrollPanel2);
      scrollPanel2.setWidget(absolutePanel);

      scrollPanel2.setHorizontalScrollPosition(200);
      scrollPanel2.setScrollPosition(200);
    }
  }
}
