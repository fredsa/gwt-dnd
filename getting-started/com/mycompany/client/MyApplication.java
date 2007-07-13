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
package com.mycompany.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;

public class MyApplication implements EntryPoint {
  private static class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {
    public void onUncaughtException(Throwable ex) {
      Window.alert("Uncaught Exception\n" + (ex == null ? "null" : ex.toString()));
    }
  }

  public void onModuleLoad() {
    // Make sure we catch unexpected exceptions in web mode, especially in other browsers
    GWT.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(400, 300);
    boundaryPanel.addStyleName("getting-started-blue");

    AbsolutePanel targetPanel = new AbsolutePanel();
    targetPanel.setPixelSize(300, 200);
    targetPanel.addStyleName("getting-started-blue");

    RootPanel.get().add(boundaryPanel);
    boundaryPanel.add(targetPanel, 10, 10);

    // Create a DragController for each logical area where a set of draggable
    // widgets and drop targets will be allowed to interact with one another.
    DragController dragController = new PickupDragController(boundaryPanel, true);

    // Create a DropController for each drop target on which draggable widgets can be dropped
    DropController dropController = new AbsolutePositionDropController(targetPanel);

    // Don't forget to register each DropController with a DragController
    dragController.registerDropController(dropController);

    for (int i = 1; i <= 5; i++) {
      Label label = new Label("Label #" + i, false);
      label.addStyleName("getting-started-label");
      targetPanel.add(label, 0, 0);
      int left = (int) (Math.random() * (targetPanel.getOffsetWidth() - label.getOffsetWidth()));
      int top = (int) (Math.random() * (targetPanel.getOffsetHeight() - label.getOffsetHeight()));
      targetPanel.setWidgetPosition(label, left, top);
      // Make individual widgets draggable
      dragController.makeDraggable(label);
    }
  }
}
