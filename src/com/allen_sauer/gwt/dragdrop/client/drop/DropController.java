package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;

/**
 * Interface to support drop behavior for drop targets. Each drop target is
 * associated with a DropController instance.
 */
public interface DropController {

  public abstract Widget getDropTarget();

  public abstract String getDropTargetStyleName();

  public abstract boolean onDrop(Widget reference, Widget draggable, DragController dragController);

  public abstract void onEnter(Widget draggable, DragController dragController);

  public abstract void onLeave(Widget draggable, DragController dragController);

  public abstract void onMove(Widget reference, Widget draggable, DragController dragController);

}