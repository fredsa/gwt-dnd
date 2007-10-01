package com.mycompany.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.drop.FlowPanelDropController;

public class MyApplication implements EntryPoint {
  public void onModuleLoad() {
    // catch unexpected exceptions, especially in other browsers
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable ex) {
        Window.alert("Uncaught Exception\n" + (ex == null ? "null" : ex.toString()));
      }
    });

    // use a deferred command so that the handler catches onModuleLoad2()
    // exceptions
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        onModuleLoad2();
      }
    });
  }

  private void onModuleLoad2() {
    // create a boundary panel to constrain all drag operations
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(400, 300);
    boundaryPanel.addStyleName("getting-started-blue");

    // create a drop target on which we can drop labels
    AbsolutePanel targetPanel = new AbsolutePanel();
    targetPanel.setPixelSize(300, 200);
    targetPanel.addStyleName("getting-started-blue");

    // add both panels to the root panel
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
    for (int i = 1; i <= 1; i++) {
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

    FlowPanel targetPanelIdx = new FlowPanel();
    targetPanelIdx.setPixelSize(250, 50);
    targetPanelIdx.addStyleName("getting-started-blue");

    boundaryPanel.add(targetPanelIdx, 10, 220);
//    targetPanelIdx.add(new Label("xx"));

    FlowPanelDropController dropControllerIdx = new FlowPanelDropController(targetPanelIdx);
    dragController.registerDropController(dropControllerIdx);
  }
}
