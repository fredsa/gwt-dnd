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
 * A {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DragController} for
 * the {@link com.google.gwt.user.client.ui.Panel} which contains a given
 * draggable widget.
 */
public class BoundryDropController extends AbsolutePositionDropController {

  private AbsolutePanel dropTarget;
  private boolean allowDropping;

  public BoundryDropController(AbsolutePanel dropTarget, boolean allowDropping) {
    super(dropTarget);
    this.dropTarget = dropTarget;
    this.allowDropping = allowDropping;
  }

  public String getDropTargetStyleName() {
    return "dragdrop-boundry";
  }

  protected boolean constrainedWidgetMove(Widget reference, Widget draggable, Widget widget, DragController dragController) {
    if (allowDropping) {
      AbsolutePanel boundryPanel = dragController.getBoundryPanel();
      Area dropArea = new Area(dropTarget, boundryPanel);
      Area draggableArea = new Area(reference, boundryPanel);
      Location location = new Location(reference, dropTarget);
      location.constrain(0, 0, dropArea.getInternalWidth() - draggableArea.getWidth(), dropArea.getInternalHeight()
          - draggableArea.getHeight());
      dropTarget.add(widget, location.getLeft(), location.getTop());
      return true;
    } else {
      return false;
    }
  }

}
