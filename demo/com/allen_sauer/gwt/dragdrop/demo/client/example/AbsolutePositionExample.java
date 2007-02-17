package com.allen_sauer.gwt.dragdrop.demo.client.example;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController} example.
 */
public class AbsolutePositionExample extends Example {

  private AbsolutePositionDropController absolutePositionDropController;

  public AbsolutePositionExample(DragController dragController) {
    super(dragController);
    AbsolutePanel positioningDropTarget = new AbsolutePanel();
    positioningDropTarget.setPixelSize(400, 200);
    setWidget(positioningDropTarget);
    absolutePositionDropController = new AbsolutePositionDropController(positioningDropTarget);
    dragController.registerDropController(absolutePositionDropController);
  }

  public String getDescription() {
    return "Draggable widgets can be placed anywhere on the gray drop target.";
  }

  public Class getDropControllerClass() {
    return AbsolutePositionDropController.class;
  }

  protected void onLoad() {
    super.onLoad();
    absolutePositionDropController.drop(createDraggable(), 10, 30);
    absolutePositionDropController.drop(createDraggable(), 60, 8);
    absolutePositionDropController.drop(createDraggable(), 190, 60);
  }
}
