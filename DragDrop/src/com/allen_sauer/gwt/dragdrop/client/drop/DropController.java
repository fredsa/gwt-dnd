package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;

public interface DropController {

  public abstract Panel getDropTargetPanel();

  public abstract String getDropTargetStyleName();

  public abstract void onDrop(DragAndDropController dragAndDropController, Widget draggable);

  public abstract void onEnter(DragAndDropController dragAndDropController, Widget draggable);

  public abstract void onLeave(DragAndDropController dragAndDropController, Widget draggable);

  public abstract void onMove(DragAndDropController dragAndDropController, Widget draggable);

}