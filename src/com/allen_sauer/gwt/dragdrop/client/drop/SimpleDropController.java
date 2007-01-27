package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;

/**
 * Minimal implementation of a DropController.
 */
public class SimpleDropController extends AbstractDropController {

  public SimpleDropController(Panel dropTargetPanel) {
    super(dropTargetPanel);
  }

  public String getDropTargetStyleName() {
    return "dragdrop-drop-target";
  }

  public void onDrop(DragAndDropController dragAndDropController, Widget draggable) {
    super.onDrop(dragAndDropController, draggable);
  }

  public void onEnter(DragAndDropController dragAndDropController, Widget draggable) {
    super.onEnter(dragAndDropController, draggable);
  }

  public void onLeave(DragAndDropController dragAndDropController, Widget draggable) {
    super.onLeave(dragAndDropController, draggable);
  }

  public void onMove(DragAndDropController dragAndDropController, Widget draggable) {
    super.onMove(dragAndDropController, draggable);
  }

}
