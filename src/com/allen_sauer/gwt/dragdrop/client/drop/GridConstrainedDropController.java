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

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

/**
 * A {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DropController} which
 * constrains the placement of draggable widgets the grid specified in the
 * constructor.
 */
public class GridConstrainedDropController extends AbsolutePositionDropController {

  private AbsolutePanel dropTarget;
  private int gridX;
  private int gridY;

  public GridConstrainedDropController(AbsolutePanel dropTarget, int gridX, int gridY) {
    super(dropTarget);
    this.dropTarget = dropTarget;
    this.gridX = gridX;
    this.gridY = gridY;
  }

  public String getDropTargetStyleName() {
    return super.getDropTargetStyleName() + " dragdrop-grid-constrained-drop-target";
  }

  protected boolean constrainedWidgetMove(Widget reference, Widget draggable, Widget widget, DragController dragController) {
    AbsolutePanel boundryPanel = dragController.getBoundryPanel();
    Area dropArea = new Area(dropTarget, boundryPanel);
    Area draggableArea = new Area(reference, boundryPanel);
    Location location = new Location(reference, dropTarget);
    location.constrain(0, 0, dropArea.getInternalWidth() - draggableArea.getWidth(), dropArea.getInternalHeight()
        - draggableArea.getHeight());
    location.snapToGrid(gridX, gridY);
    dropTarget.add(widget, location.getLeft(), location.getTop());
    return true;
  }
}
