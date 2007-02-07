package com.allen_sauer.gwt.dragdrop.demo.client;

import com.allen_sauer.gwt.dragdrop.client.DragContext;
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

  public void drop(DragContext dragAndDropController) {
    super.drop(dragAndDropController);
    dragAndDropController.getDraggable().removeStyleName("pre-trashbin-drop");
    this.dropTargetPanel.eatWidget(dragAndDropController.getDraggable());
  }

  public boolean onDrop(DragContext dragAndDropController) {
    super.onDrop(dragAndDropController);
    dragAndDropController.getDraggable().removeStyleName("pre-trashbin-drop");
    this.dropTargetPanel.eatWidget(dragAndDropController.getDraggable());
    return true;
  }

  public void onEnter(DragContext dragAndDropController) {
    super.onEnter(dragAndDropController);
    dragAndDropController.getDraggable().addStyleName("pre-trashbin-drop");
  }

  public void onLeave(DragContext dragAndDropController) {
    super.onLeave(dragAndDropController);
    dragAndDropController.getDraggable().removeStyleName("pre-trashbin-drop");
  }

}
