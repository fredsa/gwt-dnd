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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

/**
 * A
 * {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.AbstractDropController}
 * which constrains the placement of draggable widgets the grid specified in the
 * constructor.
 */
public class GridConstrainedDropController extends
    AbstractPositioningDropController {

  private int gridX;
  private int gridY;

  public GridConstrainedDropController(Panel dropTargetPanel, int gridX,
      int gridY) {
    super(dropTargetPanel);
    this.gridX = gridX;
    this.gridY = gridY;
  }

  public String getDropTargetStyleName() {
    return "dragdrop-drop-target dragdrop-grid-constrained-drop-target";
  }

  public void onDrop(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onDrop(dragAndDropController, draggable);
    constrainedWidgetMove(dragAndDropController, draggable, draggable);
  }

  public void onPreDropEnter(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropEnter(dragAndDropController, draggable);
    constrainedWidgetMove(dragAndDropController, draggable,
        dragAndDropController.getPostioningBox());
  }

  public void onPreDropLeave(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropLeave(dragAndDropController, draggable);
  }

  private void constrainedWidgetMove(
      DragAndDropController dragAndDropController, Widget draggable,
      Widget widget) {
    AbsolutePanel boundryPanel = dragAndDropController.getBoundryPanel();
    Area dropArea = new Area(getDropTargetPanel(), boundryPanel);
    Area widgetArea = new Area(widget, boundryPanel);
    Location location = new Location(draggable,
        (AbsolutePanel) getDropTargetPanel());
    location.constrain(0, 0, dropArea.getWidth() - widgetArea.getWidth(),
        dropArea.getHeight() - widgetArea.getHeight());
    location.snapToGrid(this.gridX, this.gridY);
    ((AbsolutePanel) getDropTargetPanel()).add(widget, location.getLeft(),
        location.getTop());
  }
}
