package com.allen_sauer.gwt.dragdrop.demo.client.example.duallist;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.drop.AbstractDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.VetoDropException;

public class ListBoxDropController extends AbstractDropController {

  private MouseListBox mouseListBox;

  public ListBoxDropController(MouseListBox mouseListBox) {
    super(mouseListBox);
    this.mouseListBox = mouseListBox;
  }

  public DragEndEvent onDrop(Widget reference, Widget draggable, DragController dragController) {
    MouseListBox from = ((ListBoxDragController) dragController).getCurrentDraggableListBox();
    DualListBox.copyOrmoveItems(from, mouseListBox, true, DualListBox.OPERATION_MOVE);
    return super.onDrop(reference, draggable, dragController);
  }

  public void onPreviewDrop(Widget reference, Widget draggable, DragController dragController) throws VetoDropException {
    super.onPreviewDrop(reference, draggable, dragController);
    MouseListBox from = ((ListBoxDragController) dragController).getCurrentDraggableListBox();
    // TODO can't be avoid this
    if (from == mouseListBox) {
      throw new VetoDropException();
    }
  }
}
