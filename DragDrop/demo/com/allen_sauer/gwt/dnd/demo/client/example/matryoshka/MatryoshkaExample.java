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
package com.allen_sauer.gwt.dnd.demo.client.example.matryoshka;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;

/**
 * Example of drop targets that are also draggable so that widgets can be dragged into a arbitrary
 * hierarchy of widgets.
 */
public final class MatryoshkaExample extends Example {

  private static final int CELL_HEIGHT = 50;

  private static final int CELL_SPACER = 4;

  private static final int CELL_WIDTH = 50;

  private static final String CSS_DEMO_MATRYOSHKA_EXAMPLE = "demo-MatryoshkaExample";

  private static final int NUMBER_COLS = 5;

  private static final int NUMBER_ROWS = 5;

  public MatryoshkaExample(DemoDragHandler demoDragHandler) {
    addStyleName(CSS_DEMO_MATRYOSHKA_EXAMPLE);

    // use the boundary panel as this composite's widget
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(500, 300);
    setWidget(boundaryPanel);

    boundaryPanel.setPixelSize((NUMBER_COLS + 3) * (CELL_WIDTH + CELL_SPACER) + 2 * CELL_SPACER,
        NUMBER_ROWS * (CELL_HEIGHT + CELL_SPACER) + 2 * CELL_SPACER);

    // initialize our drag controller
    PickupDragController dragController = new PickupDragController(boundaryPanel, true);
    dragController.setBehaviorMultipleSelection(false);
    dragController.addDragHandler(demoDragHandler);

    // create our grid of draggable drop target widgets
    for (int col = 0; col < NUMBER_COLS; col++) {
      for (int row = 0; row < NUMBER_ROWS; row++) {
        // pick a nice color
        int red = 255 * col / NUMBER_COLS;
        int green = 255 * row / NUMBER_ROWS;
        int blue = 128;

        // instantiate a widget which automatically makes itself a drop target
        RGBFocusPanel panel = new RGBFocusPanel(dragController, CELL_WIDTH, CELL_HEIGHT, red,
            green, blue);
        boundaryPanel.add(panel, col * (CELL_WIDTH + CELL_SPACER) + CELL_SPACER, row
            * (CELL_HEIGHT + CELL_SPACER) + CELL_SPACER);

        // make the widget draggable
        dragController.makeDraggable(panel);
      }
    }
  }

  @Override
  public String getDescription() {
    return "Example illustrating widgets that are both draggable and drop targets.";
  }

  @Override
  public Class<?>[] getInvolvedClasses() {
    return new Class[] {
        MatryoshkaExample.class, PickupDragController.class,
        MatryoshkaSetWidgetDropController.class, RGBFocusPanel.class,};
  }
}
