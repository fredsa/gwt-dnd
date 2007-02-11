package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.SimpleDropController;

/**
 * Sample SimpleDropController which discards draggable widgets which are
 * dropped on it.
 */
public class TrashBinDropController extends SimpleDropController {

  private static final String STYLE_TRASHBIN_ENGAGE = "trashbin-engage";

  private TrashBin trashBin;

  public TrashBinDropController(TrashBin dropTargetPanel) {
    super(dropTargetPanel);
    trashBin = dropTargetPanel;
  }

  public void drop(Widget draggable) {
    super.drop(draggable);
    draggable.removeStyleName(STYLE_TRASHBIN_ENGAGE);
    trashBin.eatWidget(draggable);
  }

  public boolean onDrop(Widget draggable, DragController dragController) {
    super.onDrop(draggable, dragController);
    draggable.removeStyleName(STYLE_TRASHBIN_ENGAGE);
    trashBin.eatWidget(draggable);
    return true;
  }

  public void onEnter(Widget draggable, DragController dragController) {
    super.onEnter(draggable, dragController);
    draggable.addStyleName(STYLE_TRASHBIN_ENGAGE);
  }

  public void onLeave(Widget draggable, DragController dragController) {
    super.onLeave(draggable, dragController);
    draggable.removeStyleName(STYLE_TRASHBIN_ENGAGE);
  }

}
