package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Panel;

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;

/**
 * Interface to support drop behavior for drop targets. Each drop target is
 * associated with a DropController instance.
 */
public interface DropController {

  public abstract Panel getDropTargetPanel();

  public abstract String getDropTargetStyleName();

  public abstract boolean onDrop(DragAndDropController dragAndDropController);

  public abstract void onEnter(DragAndDropController dragAndDropController);

  public abstract void onLeave(DragAndDropController dragAndDropController);

  public abstract void onMove(DragAndDropController dragAndDropController);

}