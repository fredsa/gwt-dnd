/*
 * Copyright 2007 Fred Sauer
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
package com.allen_sauer.gwt.dragdrop.demo.client.example;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.GridConstrainedDropController;
import com.allen_sauer.gwt.dragdrop.demo.client.RedBoxDraggableWidget;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.GridConstrainedDropController} example.
 */
public final class GridConstrainedExample extends Example {
  private int draggableOffsetHeight;
  private int draggableOffsetWidth;
  private GridConstrainedDropController gridConstrainedDropController;

  public GridConstrainedExample(DragController dragController) {
    super(dragController);
    determineRedBoxDimensions();
    AbsolutePanel gridConstrainedDropTarget = new AbsolutePanel();
    setWidget(gridConstrainedDropTarget);
    gridConstrainedDropController = new GridConstrainedDropController(gridConstrainedDropTarget, draggableOffsetWidth,
        draggableOffsetHeight);
    dragController.registerDropController(gridConstrainedDropController);
    gridConstrainedDropTarget.setPixelSize(draggableOffsetWidth * 5, draggableOffsetHeight * 2);
  }

  public Class getControllerClass() {
    return GridConstrainedDropController.class;
  }

  public String getDescription() {
    return "Drops (moves) are constrained to a (" + draggableOffsetWidth + " x " + draggableOffsetHeight
        + ") grid on the gray drop target.";
  }

  protected void onLoad() {
    super.onLoad();
    gridConstrainedDropController.drop(createDraggable(), 0, 0);
    gridConstrainedDropController.drop(createDraggable(), draggableOffsetWidth, draggableOffsetHeight);
  }

  private void determineRedBoxDimensions() {
    RedBoxDraggableWidget redBox = new RedBoxDraggableWidget();
    RootPanel.get().add(redBox, -500, -500);
    draggableOffsetWidth = redBox.getOffsetWidth();
    draggableOffsetHeight = redBox.getOffsetHeight();
    redBox.removeFromParent();
  }
}
