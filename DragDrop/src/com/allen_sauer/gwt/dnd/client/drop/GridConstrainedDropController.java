/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dnd.client.drop;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragContext;

/**
 * A {@link DropController} which constrains the placement of draggable widgets the grid specified
 * in the constructor.
 */
public class GridConstrainedDropController extends AbsolutePositionDropController {

  private int gridX;

  private int gridY;

  public GridConstrainedDropController(AbsolutePanel dropTarget, int gridX, int gridY) {
    super(dropTarget);
    this.gridX = gridX;
    this.gridY = gridY;
  }

  @Override
  public void drop(Widget widget, int left, int top) {
    left = Math.max(0, Math.min(left, dropTarget.getOffsetWidth() - widget.getOffsetWidth()));
    top = Math.max(0, Math.min(top, dropTarget.getOffsetHeight() - widget.getOffsetHeight()));
    left = Math.round((float) left / gridX) * gridX;
    top = Math.round((float) top / gridY) * gridY;
    dropTarget.add(widget, left, top);
  }

  @Override
  public void onMove(DragContext context) {
    super.onMove(context);
    for (Draggable draggable : draggableList) {
      draggable.desiredX = context.desiredDraggableX - dropTargetOffsetX + draggable.relativeX;
      draggable.desiredY = context.desiredDraggableY - dropTargetOffsetY + draggable.relativeY;
      draggable.desiredX = Math.max(0, Math.min(draggable.desiredX, dropTargetClientWidth
          - draggable.offsetWidth));
      draggable.desiredY = Math.max(0, Math.min(draggable.desiredY, dropTargetClientHeight
          - draggable.offsetHeight));
      draggable.desiredX = Math.round((float) draggable.desiredX / gridX) * gridX;
      draggable.desiredY = Math.round((float) draggable.desiredY / gridY) * gridY;
      dropTarget.add(draggable.positioner, draggable.desiredX, draggable.desiredY);
    }
  }
}
