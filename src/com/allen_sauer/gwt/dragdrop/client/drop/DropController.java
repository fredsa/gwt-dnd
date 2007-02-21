package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;

/**
 * Create a DropController for each drop target on which draggable widgets can be dropped.
 * Do not forget to register each DropController with a
 * {@link com.allen_sauer.gwt.dragdrop.client.DragController}.
 */
public interface DropController {

  public abstract Widget getDropTarget();

  public abstract String getDropTargetStyleName();

  public abstract void onDrop(Widget reference, Widget draggable, DragController dragController);

  public abstract void onEnter(Widget reference, Widget draggable, DragController dragController);

  public abstract void onLeave(Widget draggable, DragController dragController);

  public abstract void onMove(Widget reference, Widget draggable, DragController dragController);

  public abstract boolean onPreviewDrop(Widget reference, Widget draggable, DragController dragController);

}