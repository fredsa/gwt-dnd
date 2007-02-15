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

  public TrashBinDropController(TrashBin trashBin) {
    super(trashBin);
    this.trashBin = trashBin;
  }

  public void drop(Widget draggable) {
    super.drop(draggable);
    draggable.removeStyleName(STYLE_TRASHBIN_ENGAGE);
    trashBin.eatWidget(draggable);
  }

  public boolean onDrop(Widget reference, Widget draggable, DragController dragController) {
    super.onDrop(reference, draggable, dragController);
    draggable.removeStyleName(STYLE_TRASHBIN_ENGAGE);
    trashBin.eatWidget(draggable);
    return true;
  }

  public void onEnter(Widget reference, Widget draggable, DragController dragController) {
    super.onEnter(reference, draggable, dragController);
    draggable.addStyleName(STYLE_TRASHBIN_ENGAGE);
  }

  public void onLeave(Widget draggable, DragController dragController) {
    super.onLeave(draggable, dragController);
    draggable.removeStyleName(STYLE_TRASHBIN_ENGAGE);
  }

}
