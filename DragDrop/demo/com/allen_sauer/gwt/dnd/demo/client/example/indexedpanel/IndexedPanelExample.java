/*
 * Copyright 2008 Fred Sauer
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
package com.allen_sauer.gwt.dnd.demo.client.example.indexedpanel;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.IndexedDropController;
import com.allen_sauer.gwt.dnd.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;

/**
 * Example of columns that can be rearranged, with widget that can be moved
 * within a column or between columns.
 */
public final class IndexedPanelExample extends Example {

  private static final int COLUMNS = 3;

  private static final String CSS_DEMO_INDEXED_PANEL_EXAMPLE = "demo-IndexedPanelExample";

  private static final String CSS_DEMO_INDEXED_PANEL_EXAMPLE_COLUMN_COMPOSITE = "demo-IndexedPanelExample-column-composite";

  private static final String CSS_DEMO_INDEXED_PANEL_EXAMPLE_CONTAINER = "demo-IndexedPanelExample-container";

  private static final String CSS_DEMO_INDEXED_PANEL_EXAMPLE_HEADING = "demo-IndexedPanelExample-heading";

  private static final String CSS_DEMO_INDEXED_PANEL_EXAMPLE_WIDGET = "demo-IndexedPanelExample-widget";

  private static final int ROWS = 4;

  private static final int SPACING = 0;

  public IndexedPanelExample(DemoDragHandler demoDragHandler) {
    addStyleName(CSS_DEMO_INDEXED_PANEL_EXAMPLE);
    int count = 0;

    // use the boundary panel as this composite's widget
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setSize("100%", "100%");
    setWidget(boundaryPanel);

    // initialize our column drag controller
    PickupDragController columnDragController = new PickupDragController(boundaryPanel, false);
    columnDragController.setBehaviorMultipleSelection(false);
    columnDragController.addDragHandler(demoDragHandler);

    // initialize our widget drag controller
    PickupDragController widgetDragController = new PickupDragController(boundaryPanel, false);
    widgetDragController.setBehaviorMultipleSelection(false);
    widgetDragController.addDragHandler(demoDragHandler);

    // initialize horizontal panel to hold our columns
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    horizontalPanel.addStyleName(CSS_DEMO_INDEXED_PANEL_EXAMPLE_CONTAINER);
    horizontalPanel.setSpacing(SPACING);
    boundaryPanel.add(horizontalPanel);

    // initialize our column drop controller
    IndexedDropController columnDropController = new IndexedDropController(horizontalPanel);
    columnDragController.registerDropController(columnDropController);

    for (int col = 1; col <= COLUMNS; col++) {
      // initialize a vertical panel to hold the heading and a second vertical
      // panel
      VerticalPanel columnCompositePanel = new VerticalPanel();
      columnCompositePanel.addStyleName(CSS_DEMO_INDEXED_PANEL_EXAMPLE_COLUMN_COMPOSITE);

      // initialize inner vertical panel to hold individual widgets
      VerticalPanel verticalPanel = new VerticalPanel();
      verticalPanel.addStyleName(CSS_DEMO_INDEXED_PANEL_EXAMPLE_CONTAINER);
      verticalPanel.setSpacing(SPACING);
      horizontalPanel.add(columnCompositePanel);

      // initialize a widget drop controller for the current column
      NoInsertAtEndIndexedDropController widgetDropController = new NoInsertAtEndIndexedDropController(
          verticalPanel);
      widgetDragController.registerDropController(widgetDropController);

      // Put together the column pieces
      Label heading = new Label("Column " + col);
      heading.addStyleName(CSS_DEMO_INDEXED_PANEL_EXAMPLE_HEADING);
      columnCompositePanel.add(heading);
      columnCompositePanel.add(verticalPanel);

      // make the column draggable by its heading
      columnDragController.makeDraggable(columnCompositePanel, heading);

      for (int row = 1; row <= ROWS; row++) {
        // initialize a widget
        HTML widget = new HTML("Draggable&nbsp;#" + ++count);
        widget.addStyleName(CSS_DEMO_INDEXED_PANEL_EXAMPLE_WIDGET);
        widget.setHeight(Random.nextInt(4) + 2 + "em");
        verticalPanel.add(widget);

        // make the widget draggable
        widgetDragController.makeDraggable(widget);
      }

      // prevent vertical panel from collapsing to zero when last widget is
      // removed
      Label spacerLabel = new Label("");
      spacerLabel.setPixelSize(10, 50);
      verticalPanel.add(spacerLabel);
    }
  }

  @Override
  public String getDescription() {
    return "Allows drop to occur anywhere among the children of a supported <code>IndexedPanel</code>.";
  }

  @Override
  public Class<?>[] getInvolvedClasses() {
    return new Class[] {
        IndexedPanelExample.class, NoInsertAtEndIndexedDropController.class,
        PickupDragController.class,};
  }
}
