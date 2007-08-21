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

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dragdrop.demo.client.example.DraggableFactory;
import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

public class PuzzleExample extends Example {
  private static final int COLUMNS = 4;
  private static final String CSS_DEMO_PUZZLE_CELL = "demo-PuzzleExample-cell";
  private static final String CSS_DEMO_PUZZLE_TABLE = "demo-PuzzleExample-table";
  private static final int IMAGE_HEIGHT = 58;
  private static final int IMAGE_WIDTH = 65;
  private static final int ROWS = 4;
  private DragController dragController;

  public PuzzleExample(DemoDragHandler demoDragHandler) {
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(500, 300);
    setWidget(boundaryPanel);

    FlexTable flexTable = new FlexTable();
    flexTable.setStyleName(CSS_DEMO_PUZZLE_TABLE);
    boundaryPanel.add(flexTable, 50, 20);

    dragController = new PickupDragController(boundaryPanel, true);
    dragController.addDragHandler(demoDragHandler);

    for (int i = 0; i < COLUMNS; i++) {
      for (int j = 0; j < ROWS; j++) {
        SimplePanel simplePanel = new SimplePanel();
        simplePanel.setPixelSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        flexTable.setWidget(i, j, simplePanel);
        flexTable.getCellFormatter().setStyleName(i, j, CSS_DEMO_PUZZLE_CELL);
        if (j == 0) {
          simplePanel.setWidget(createDraggable());
        }

        SetWidgetDropController dropController = new SetWidgetDropController(simplePanel);
        dragController.registerDropController(dropController);
      }
    }
  }

  public Class getControllerClass() {
    return SetWidgetDropController.class;
  }

  public String getDescription() {
    return "Demonstrate image dragging and target selection when there are multiple intersecting drop targets.";
  }

  protected Widget createDraggable() {
    return DraggableFactory.createDraggableImage(dragController);
  }
}
