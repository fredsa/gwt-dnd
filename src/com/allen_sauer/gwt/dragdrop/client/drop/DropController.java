package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;

public interface DropController {

  public abstract Panel getDropTargetPanel();

  public abstract String getDropTargetStyleName();

  public abstract void onDrop(DragAndDropController dragAndDropController, Widget draggable);

  public abstract void onPreDropEnter(DragAndDropController dragAndDropController, Widget draggable);

  public abstract void onPreDropLeave(DragAndDropController dragAndDropController, Widget draggable);

  public abstract void onPreDropMove(DragAndDropController dragAndDropController, Widget draggable);

}