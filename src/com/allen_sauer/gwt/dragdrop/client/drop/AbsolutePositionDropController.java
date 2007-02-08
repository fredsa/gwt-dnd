/*
 * Copyright 2006 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragContext;
import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

/**
 * A {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DropController} which
 * allows a draggable widget to be placed at specific (absolute) locations on an
 * {@link com.google.gwt.user.client.ui.AbsolutePanel} drop target.
 */
public class AbsolutePositionDropController extends
    AbstractPositioningDropController {
  private AbsolutePanel dropTargetPanel;

  public AbsolutePositionDropController(AbsolutePanel dropTargetPanel) {
    super(dropTargetPanel);
    this.dropTargetPanel = dropTargetPanel;
  }

  public final void drop(Widget dragAndDropController) {
    throw new RuntimeException("single argument drop() not supported");
  }

  public void drop(Widget widget, int left, int top) {
    super.drop(widget, left, top);
    DragController dragController = DragController.getDragController(widget);
    Location location = new Location(this.dropTargetPanel,
        dragController.getBoundryPanel());
    dragController.getBoundryPanel().add(widget, location.getLeft() + left,
        location.getTop() + top);
    constrainedWidgetMove(dragController, widget, widget);
  }

  public String getDropTargetStyleName() {
    return "dragdrop-drop-target dragdrop-absolute-positioning-drop-target";
  }

  public boolean onDrop(DragContext dragContext) {
    boolean result = constrainedWidgetMove(dragContext.getDragController(),
        dragContext.getDraggable(), dragContext.getDraggable());
    super.onDrop(dragContext);
    return result;
  }

  public void onLeave(DragContext dragContext) {
    super.onLeave(dragContext);
  }

  public void onMove(DragContext dragContext) {
    super.onMove(dragContext);
    constrainedWidgetMove(dragContext.getDragController(),
        dragContext.getDraggable(), getPositionerWidget());
  }

  protected boolean constrainedWidgetMove(DragController dragController,
      Widget draggable, Widget widget) {
    AbsolutePanel boundryPanel = dragController.getBoundryPanel();
    Area dropArea = new Area(this.dropTargetPanel, boundryPanel);
    Area draggableArea = new Area(draggable, boundryPanel);
    Location location = new Location(draggable, this.dropTargetPanel);
    location.constrain(0, 0, dropArea.getWidth() - draggableArea.getWidth(),
        dropArea.getHeight() - draggableArea.getHeight());
    this.dropTargetPanel.add(widget, location.getLeft(), location.getTop());
    return true;
  }

}
