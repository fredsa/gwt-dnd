package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Panel;

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;

/**
 * Minimal implementation of a DropController.
 */
public class SimpleDropController extends AbstractDropController {

  public SimpleDropController(Panel dropTargetPanel) {
    super(dropTargetPanel);
  }

  public void drop(DragAndDropController dragAndDropController) {
    super.drop(dragAndDropController);
  }

  public String getDropTargetStyleName() {
    return "dragdrop-drop-target";
  }

  public boolean onDrop(DragAndDropController dragAndDropController) {
    return super.onDrop(dragAndDropController);
  }

  public void onEnter(DragAndDropController dragAndDropController) {
    super.onEnter(dragAndDropController);
  }

  public void onLeave(DragAndDropController dragAndDropController) {
    super.onLeave(dragAndDropController);
  }

  public void onMove(DragAndDropController dragAndDropController) {
    super.onMove(dragAndDropController);
  }

}
