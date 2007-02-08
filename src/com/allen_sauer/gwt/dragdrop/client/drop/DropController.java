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

  public abstract boolean onDrop(DragContext dragContext);

  public abstract void onEnter(DragContext dragContext);

  public abstract void onLeave(DragContext dragContext);

  public abstract void onMove(DragContext dragContext);

}