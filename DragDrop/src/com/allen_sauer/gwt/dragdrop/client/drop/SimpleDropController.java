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

  public void onDrop(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onDrop(dragAndDropController, draggable);
  }

  public void onPreDropEnter(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropEnter(dragAndDropController, draggable);
  }

  public void onPreDropLeave(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropLeave(dragAndDropController, draggable);
  }

  public void onPreDropMove(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropMove(dragAndDropController, draggable);
  }

}
