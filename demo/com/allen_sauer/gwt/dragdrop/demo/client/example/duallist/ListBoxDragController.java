package com.allen_sauer.gwt.dragdrop.demo.client.example.duallist;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;

public class ListBoxDragController extends PickupDragController {

  private MouseListBox currentDraggableListBox;

  public ListBoxDragController(DualListBox dualListBox) {
    super(dualListBox, false);
  }

  public void dragEnd(Widget draggable, Widget dropTarget) {
    super.dragEnd(draggable, dropTarget);
  }

  public void dragStart(Widget draggable) {
    currentDraggableListBox = (MouseListBox) draggable;
    super.dragStart(draggable);
  }

  public MouseListBox getCurrentDraggableListBox() {
    return this.currentDraggableListBox;
  }

  public Widget getMovableWidget() {
    return super.getMovableWidget();
  }

  public void notifyDragEnd(DragEndEvent dragEndEvent) {
    super.notifyDragEnd(dragEndEvent);
    // cleanup 
    currentDraggableListBox = null;
  }
  
  protected Widget maybeNewDraggableProxy(Widget draggable) {
    MouseListBox proxyMouseListBox = new MouseListBox(currentDraggableListBox.getSelectedWidgetCount());
    proxyMouseListBox.addStyleName(STYLE_PROXY);
    DualListBox.copyOrmoveItems(currentDraggableListBox, proxyMouseListBox, true, DualListBox.OPERATION_COPY);
    return proxyMouseListBox;
  }
}
