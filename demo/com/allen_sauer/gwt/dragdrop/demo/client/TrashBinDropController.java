package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.Widget;

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

  public void drop(Widget widget) {
    super.drop(widget);
    widget.removeStyleName("pre-trashbin-drop");
    this.dropTargetPanel.eatWidget(widget);
  }

  public boolean onDrop(DragContext dragContext) {
    super.onDrop(dragContext);
    dragContext.getDraggable().removeStyleName("pre-trashbin-drop");
    this.dropTargetPanel.eatWidget(dragContext.getDraggable());
    return true;
  }

  public void onEnter(DragContext dragContext) {
    super.onEnter(dragContext);
    dragContext.getDraggable().addStyleName("pre-trashbin-drop");
  }

  public void onLeave(DragContext dragContext) {
    super.onLeave(dragContext);
    dragContext.getDraggable().removeStyleName("pre-trashbin-drop");
  }

}
