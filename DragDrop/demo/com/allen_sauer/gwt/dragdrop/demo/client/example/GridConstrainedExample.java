package com.allen_sauer.gwt.dragdrop.demo.client.example;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.GridConstrainedDropController;
import com.allen_sauer.gwt.dragdrop.demo.client.RedBoxDraggableWidget;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.GridConstrainedDropController} example.
 */
public class GridConstrainedExample extends Example {

  private int draggableOffsetHeight;
  private int draggableOffsetWidth;
  private GridConstrainedDropController gridConstrainedDropController;

  public GridConstrainedExample(DragController dragController) {
    super(dragController);
    determineRedBoxDimensions();
    AbsolutePanel gridConstrainedDropTarget = new AbsolutePanel();
    setWidget(gridConstrainedDropTarget);
    gridConstrainedDropController = new GridConstrainedDropController(gridConstrainedDropTarget, draggableOffsetWidth,
        draggableOffsetHeight);
    dragController.registerDropController(gridConstrainedDropController);
    gridConstrainedDropTarget.setPixelSize(draggableOffsetWidth * 5, draggableOffsetHeight * 2);
  }

  public String getDescription() {
    return "Drops (moves) are constrained to a (" + draggableOffsetWidth + " x " + draggableOffsetHeight
        + ") grid on the gray drop target.";
  }

  public Class getDropControllerClass() {
    return GridConstrainedDropController.class;
  }

  protected void onLoad() {
    super.onLoad();
    gridConstrainedDropController.drop(createDraggable(), 0, 0);
    gridConstrainedDropController.drop(createDraggable(), draggableOffsetWidth, draggableOffsetHeight);
  }

  private void determineRedBoxDimensions() {
    RedBoxDraggableWidget redBox = new RedBoxDraggableWidget();
    RootPanel.get().add(redBox, -500, -500);
    draggableOffsetWidth = redBox.getOffsetWidth();
    draggableOffsetHeight = redBox.getOffsetHeight();
    redBox.removeFromParent();
  }
}
