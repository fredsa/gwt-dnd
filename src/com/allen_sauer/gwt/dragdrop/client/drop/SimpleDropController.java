package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragContext;

/**
 * Minimal implementation of a DropController.
 */
public class SimpleDropController extends AbstractDropController {

  public SimpleDropController(Panel dropTargetPanel) {
    super(dropTargetPanel);
  }

  public void drop(Widget dragAndDropController) {
    super.drop(dragAndDropController);
  }

  public String getDropTargetStyleName() {
    return "dragdrop-drop-target";
  }

  public boolean onDrop(DragContext dragAndDropController) {
    return super.onDrop(dragAndDropController);
  }

  public void onEnter(DragContext dragAndDropController) {
    super.onEnter(dragAndDropController);
  }

  public void onLeave(DragContext dragAndDropController) {
    super.onLeave(dragAndDropController);
  }

  public void onMove(DragContext dragAndDropController) {
    super.onMove(dragAndDropController);
  }

}
