package com.allen_sauer.gwt.dragdrop.demo.client.example;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.NoOverlapDropController;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.NoOverlapDropController} example.
 */
public class NoOverlapExample extends Example {

  private NoOverlapDropController noOverlapDropController;

  public NoOverlapExample(DragController dragController) {
    super(dragController);
    AbsolutePanel noOverlapDropTarget = new AbsolutePanel();
    noOverlapDropTarget.setPixelSize(400, 200);
    setWidget(noOverlapDropTarget);
    noOverlapDropController = new NoOverlapDropController(noOverlapDropTarget);
    dragController.registerDropController(noOverlapDropController);
  }

  public String getDescription() {
    return "Widgets cannot be dropped on top of (overlapping) other dropped widgets.";
  }

  public Class getDropControllerClass() {
    return NoOverlapDropController.class;
  }

  protected void onLoad() {
    super.onLoad();
    noOverlapDropController.drop(createDraggable(), 10, 10);
    noOverlapDropController.drop(createDraggable(), 90, 60);
    noOverlapDropController.drop(createDraggable(), 190, 50);
  }
}
