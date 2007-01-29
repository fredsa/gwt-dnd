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

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

/**
 * A {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DropController} which
 * allows a draggable widget to be placed anywhere on an
 * {@link com.google.gwt.user.client.ui.AbsolutePanel} drop target.
 */
public class AbsolutePositionDropController extends AbstractPositioningDropController {
  private AbsolutePanel dropTargetPanel;

  public AbsolutePositionDropController(AbsolutePanel dropTargetPanel) {
    super(dropTargetPanel);
    this.dropTargetPanel = dropTargetPanel;
  }

  public void drop(DragAndDropController dragAndDropController, int left, int top) {
    Location location = new Location(this.dropTargetPanel, dragAndDropController.getBoundryPanel());
    dragAndDropController.getBoundryPanel().add(dragAndDropController.getDraggable(), location.getLeft() + left,
        location.getTop() + top);
    constrainedWidgetMove(dragAndDropController, dragAndDropController.getDraggable());
  }

  public String getDropTargetStyleName() {
    return "dragdrop-drop-target dragdrop-absolute-positioning-drop-target";
  }

  public void onDrop(DragAndDropController dragAndDropController) {
    super.onDrop(dragAndDropController);
    constrainedWidgetMove(dragAndDropController, dragAndDropController.getDraggable());
  }

  public void onEnter(DragAndDropController dragAndDropController) {
    super.onEnter(dragAndDropController);
  }

  public void onLeave(DragAndDropController dragAndDropController) {
    super.onLeave(dragAndDropController);
  }

  public void onMove(DragAndDropController dragAndDropController) {
    super.onMove(dragAndDropController);
    constrainedWidgetMove(dragAndDropController, dragAndDropController.getPostioningBox());
  }

  protected void constrainedWidgetMove(DragAndDropController dragAndDropController, Widget widget) {
    AbsolutePanel boundryPanel = dragAndDropController.getBoundryPanel();
    Area dropArea = new Area(this.dropTargetPanel, boundryPanel);
    Area widgetArea = new Area(widget, boundryPanel);
    Location location = new Location(dragAndDropController.getDraggable(), this.dropTargetPanel);
    location.constrain(0, 0, dropArea.getWidth() - widgetArea.getWidth(), dropArea.getHeight() - widgetArea.getHeight());
    this.dropTargetPanel.add(widget, location.getLeft(), location.getTop());
  }

}
