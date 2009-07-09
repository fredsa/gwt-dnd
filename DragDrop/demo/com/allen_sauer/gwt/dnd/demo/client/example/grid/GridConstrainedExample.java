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
package com.allen_sauer.gwt.dnd.demo.client.example.grid;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.GridConstrainedDropController;
import com.allen_sauer.gwt.dnd.demo.client.RedBoxDraggableWidget;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;

/**
 * {@link com.allen_sauer.gwt.dnd.client.drop.GridConstrainedDropController} example.
 */
public final class GridConstrainedExample extends Example {

  private static final String CSS_DEMO_GRID_CONSTRAINED_EXAMPLE = "demo-GridConstrainedExample";

  private int draggableOffsetHeight;

  private int draggableOffsetWidth;

  private GridConstrainedDropController gridConstrainedDropController;

  public GridConstrainedExample(PickupDragController dragController) {
    super(dragController);
    addStyleName(CSS_DEMO_GRID_CONSTRAINED_EXAMPLE);

    // determine runtime dimensions of our basic draggable widgets
    determineRedBoxDimensions();

    // use the drop target as this composite's widget
    AbsolutePanel gridConstrainedDropTarget = new AbsolutePanel();
    setWidget(gridConstrainedDropTarget);

    // instantiate our drop controller
    gridConstrainedDropController = new GridConstrainedDropController(gridConstrainedDropTarget,
        draggableOffsetWidth, draggableOffsetHeight);
    dragController.registerDropController(gridConstrainedDropController);

    // set our drop target size to a multiple of the draggable size
    gridConstrainedDropTarget.setPixelSize(draggableOffsetWidth * 5, draggableOffsetHeight * 2);
  }

  @Override
  public String getDescription() {
    return "Drops (moves) are constrained to a (" + draggableOffsetWidth + " x "
        + draggableOffsetHeight + ") grid on the gray drop target.";
  }

  @Override
  public Class<?>[] getInvolvedClasses() {
    return new Class[] {GridConstrainedExample.class, GridConstrainedDropController.class,};
  }

  @Override
  protected void onInitialLoad() {
    gridConstrainedDropController.drop(createDraggable(), 0, 0);
    gridConstrainedDropController.drop(createDraggable(), draggableOffsetWidth,
        draggableOffsetHeight);
  }

  private void determineRedBoxDimensions() {
    RedBoxDraggableWidget redBox = new RedBoxDraggableWidget();
    RootPanel.get().add(redBox, -500, -500);
    draggableOffsetWidth = redBox.getOffsetWidth();
    draggableOffsetHeight = redBox.getOffsetHeight();
    redBox.removeFromParent();
  }
}
