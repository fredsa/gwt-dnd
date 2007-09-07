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
  static {
    // catch unexpected exceptions, especially in other browsers
    // set the handler statically so that it is in effect for onModuleLoad()
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable ex) {
        Window.alert("Uncaught Exception\n" + (ex == null ? "null" : ex.toString()));
      }
    });
  }

  public void onModuleLoad() {
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(400, 300);
    boundaryPanel.addStyleName("getting-started-blue");

    AbsolutePanel targetPanel = new AbsolutePanel();
    targetPanel.setPixelSize(300, 200);
    targetPanel.addStyleName("getting-started-blue");

    RootPanel.get().add(boundaryPanel);
    boundaryPanel.add(targetPanel, 10, 10);

    // create a DragController for each logical area where a set of draggable
    // widgets and drop targets will be allowed to interact with one another.
    DragController dragController = new PickupDragController(boundaryPanel, true);

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
      
      // determine random label location within target pael
      int left = (int) (Math.random() * (targetPanel.getOffsetWidth() - label.getOffsetWidth()));
      int top = (int) (Math.random() * (targetPanel.getOffsetHeight() - label.getOffsetHeight()));

      // move the label
      targetPanel.setWidgetPosition(label, left, top);
      
      // make the label draggable
      dragController.makeDraggable(label);
    }
  }
}
