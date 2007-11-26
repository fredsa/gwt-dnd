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
package com.allen_sauer.gwt.dragdrop.demo.client.example.puzzle;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dragdrop.demo.client.example.DraggableFactory;
import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

/**
 * Example illustrating image dragging and drop target selection with multiple
 * eligible drop targets being considered.
 */
public class PuzzleExample extends Example {
  private static final int COLUMNS = 4;
  private static final String CSS_DEMO_PUZZLE_CELL = "demo-PuzzleExample-cell";
  private static final String CSS_DEMO_PUZZLE_EXAMPLE = "demo-PuzzleExample";
  private static final String CSS_DEMO_PUZZLE_TABLE = "demo-PuzzleExample-table";
  private static final int IMAGE_HEIGHT = 58;
  private static final int IMAGE_WIDTH = 65;
  private static final int ROWS = 4;
  private PickupDragController dragController;

  public PuzzleExample(DemoDragHandler demoDragHandler) {
    addStyleName(CSS_DEMO_PUZZLE_EXAMPLE);

    // use the boundary panel as this composite's widget
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(500, 300);
    setWidget(boundaryPanel);

    // initialize our flex table
    FlexTable flexTable = new FlexTable();
    flexTable.setStyleName(CSS_DEMO_PUZZLE_TABLE);
    boundaryPanel.add(flexTable, 50, 20);

    // initialize our drag controller
    dragController = new PickupDragController(boundaryPanel, true);
    dragController.addDragHandler(demoDragHandler);

    // create our grid
    for (int i = 0; i < COLUMNS; i++) {
      for (int j = 0; j < ROWS; j++) {
        // create a simple panel drop target for the current cell
        SimplePanel simplePanel = new SimplePanel();
        simplePanel.setPixelSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        flexTable.setWidget(i, j, simplePanel);
        flexTable.getCellFormatter().setStyleName(i, j, CSS_DEMO_PUZZLE_CELL);

        // place a pumpkin in each panel in the cells in the first column
        if (j == 0) {
          simplePanel.setWidget(createDraggable());
        }

        // instantiate a drop controller of the panel in the current cell
        SetWidgetDropController dropController = new SetWidgetDropController(simplePanel);
        dragController.registerDropController(dropController);
      }
    }
  }

  public String getDescription() {
    return "Demonstrate image dragging and target selection when there are multiple intersecting drop targets.";
  }

  public Class[] getInvolvedClasses() {
    return new Class[] {PuzzleExample.class, SetWidgetDropController.class, PickupDragController.class,};
  }

  protected Widget createDraggable() {
    return DraggableFactory.createDraggableImage(dragController);
  }
}
