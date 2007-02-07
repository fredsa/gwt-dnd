package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Panel;

import com.allen_sauer.gwt.dragdrop.client.DragContext;

/**
 * Interface to support drop behavior for drop targets. Each drop target is
 * associated with a DropController instance.
 */
public interface DropController {

  public abstract Panel getDropTargetPanel();

  public abstract String getDropTargetStyleName();

  public abstract boolean onDrop(DragContext dragAndDropController);

  public abstract void onEnter(DragContext dragAndDropController);

  public abstract void onLeave(DragContext dragAndDropController);

  public abstract void onMove(DragContext dragAndDropController);

}