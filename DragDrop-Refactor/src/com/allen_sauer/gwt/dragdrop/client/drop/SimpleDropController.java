package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;

/**
 * Minimal implementation of a DropController.
 */
public class SimpleDropController extends AbstractDropController {

  public SimpleDropController(Widget dropTargetPanel) {
    super(dropTargetPanel);
  }

  public void drop(Widget draggable) {
    super.drop(draggable);
  }

  public boolean onDrop(Widget draggable, DragController dragController) {
    return super.onDrop(draggable, dragController);
  }

  public void onEnter(Widget draggable, DragController dragController) {
    super.onEnter(draggable, dragController);
  }

  public void onLeave(Widget draggable, DragController dragController) {
    super.onLeave(draggable, dragController);
  }

  public void onMove(Widget draggable, DragController dragController) {
    super.onMove(draggable, dragController);
  }

}
