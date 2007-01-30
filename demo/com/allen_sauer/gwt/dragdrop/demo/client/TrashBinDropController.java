package com.allen_sauer.gwt.dragdrop.demo.client;

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

  public void drop(DragAndDropController dragAndDropController) {
    super.drop(dragAndDropController);
    dragAndDropController.getDraggable().removeStyleName("pre-trashbin-drop");
    this.dropTargetPanel.eatWidget(dragAndDropController.getDraggable());
  }

  public boolean onDrop(DragAndDropController dragAndDropController) {
    super.onDrop(dragAndDropController);
    dragAndDropController.getDraggable().removeStyleName("pre-trashbin-drop");
    this.dropTargetPanel.eatWidget(dragAndDropController.getDraggable());
    return true;
  }

  public void onEnter(DragAndDropController dragAndDropController) {
    super.onEnter(dragAndDropController);
    dragAndDropController.getDraggable().addStyleName("pre-trashbin-drop");
  }

  public void onLeave(DragAndDropController dragAndDropController) {
    super.onLeave(dragAndDropController);
    dragAndDropController.getDraggable().removeStyleName("pre-trashbin-drop");
  }

}
