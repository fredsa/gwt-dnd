package com.allen_sauer.gwt.dragdrop.client;

import com.google.gwt.user.client.ui.Widget;

import java.util.EventObject;

/**
 * Event containing information about the end of a drag.
 */
public class DragEndEvent extends EventObject {

  private Widget dropTarget;

  public DragEndEvent(Object source, Widget dropTarget) {
    super(source);
    this.dropTarget = dropTarget;
  }

  public Widget getDropTarget() {
    return dropTarget;
  }
}
