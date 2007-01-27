package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.SimpleDropController;

/**
 * Sample SimpleDropController which discards draggable widgets which are
 * dropped on it.
 */
public class TrashBinDropController extends SimpleDropController {

  TrashBinPanel dropTargetPanel;

  public TrashBinDropController(TrashBinPanel dropTargetPanel) {
    super(dropTargetPanel);
    this.dropTargetPanel = dropTargetPanel;
  }

  public void onDrop(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onDrop(dragAndDropController, draggable);
    this.dropTargetPanel.eatWidget(draggable);
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
