package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.SimpleDropController;

public class TrashBinDropController extends SimpleDropController {

  public TrashBinDropController(Panel dropTargetPanel) {
    super(dropTargetPanel);
  }

  public void onDrop(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onDrop(dragAndDropController, draggable);
    draggable.removeFromParent();
  }

  public void onPreDropEnter(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropEnter(dragAndDropController, draggable);
    draggable.addStyleName("pre-trashbin-drop");
  }

  public void onPreDropLeave(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropLeave(dragAndDropController, draggable);
    draggable.removeStyleName("pre-trashbin-drop");
  }

}
