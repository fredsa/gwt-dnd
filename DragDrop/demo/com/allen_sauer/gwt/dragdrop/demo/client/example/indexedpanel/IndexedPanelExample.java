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
package com.allen_sauer.gwt.dragdrop.demo.client.example.indexedpanel;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController;
import com.allen_sauer.gwt.dragdrop.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController} example.
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
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setSize("100%", "100%");
    setWidget(boundaryPanel);

    PickupDragController columnDragController = new PickupDragController(boundaryPanel, false);
    columnDragController.addDragHandler(demoDragHandler);
    PickupDragController widgetDragController = new PickupDragController(boundaryPanel, false);
    widgetDragController.addDragHandler(demoDragHandler);

    HorizontalPanel horizontalPanel = new HorizontalPanel();
    horizontalPanel.addStyleName(CSS_DEMO_INDEXED_PANEL_EXAMPLE_CONTAINER);
    horizontalPanel.setSpacing(SPACING);
    boundaryPanel.add(horizontalPanel);
    IndexedDropController columnDropController = new IndexedDropController(horizontalPanel);
    columnDragController.registerDropController(columnDropController);

    for (int col = 1; col <= COLUMNS; col++) {
      VerticalPanel columnCompositePanel = new VerticalPanel();
      columnCompositePanel.addStyleName(CSS_DEMO_INDEXED_PANEL_EXAMPLE_COLUMN_COMPOSITE);
      VerticalPanel verticalPanel = new VerticalPanel();
      verticalPanel.addStyleName(CSS_DEMO_INDEXED_PANEL_EXAMPLE_CONTAINER);
      verticalPanel.setSpacing(SPACING);
      horizontalPanel.add(columnCompositePanel);
      IndexedDropController widgetDropController = new IndexedDropController(verticalPanel);
      widgetDragController.registerDropController(widgetDropController);

      Label heading = new Label("Column " + col);
      heading.addStyleName(CSS_DEMO_INDEXED_PANEL_EXAMPLE_HEADING);
      columnCompositePanel.add(heading);
      columnCompositePanel.add(verticalPanel);
      columnDragController.makeDraggable(columnCompositePanel, heading);

      for (int row = 1; row <= ROWS; row++) {
        HTML widget = new HTML("Draggable&nbsp;#" + ++count);
        widget.addStyleName(CSS_DEMO_INDEXED_PANEL_EXAMPLE_WIDGET);
        widgetDragController.makeDraggable(widget);
        widget.setHeight(Random.nextInt(4) + 2 + "em");
        verticalPanel.add(widget);
      }
    }
  }

  public Class getControllerClass() {
    return IndexedDropController.class;
  }

  public String getDescription() {
    return "Allows drop to occur anywhere among the children of a supported <code>IndexedPanel</code>.";
  }
}
