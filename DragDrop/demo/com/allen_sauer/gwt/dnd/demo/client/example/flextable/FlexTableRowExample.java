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
package com.allen_sauer.gwt.dnd.demo.client.example.flextable;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.allen_sauer.gwt.dnd.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;

/**
 * {@link FlexTableRowDropController} example.
 */
public final class FlexTableRowExample extends Example {

  private static final String CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE = "demo-FlexTableRowExample";

  public FlexTableRowExample(DemoDragHandler demoDragHandler) {
    addStyleName(CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE);

    // use the containing panel as this composite's widget
    AbsolutePanel tableExamplePanel = new AbsolutePanel();
    tableExamplePanel.setPixelSize(450, 300);
    setWidget(tableExamplePanel);

    // instantiate our drag controller
    FlexTableRowDragController tableRowDragController = new FlexTableRowDragController(
        tableExamplePanel);
    tableRowDragController.addDragHandler(demoDragHandler);

    // instantiate two flex tables
    DemoFlexTable table1 = new DemoFlexTable(5, 3, tableRowDragController);
    DemoFlexTable table2 = new DemoFlexTable(5, 4, tableRowDragController);
    tableExamplePanel.add(table1, 10, 20);
    tableExamplePanel.add(table2, 230, 40);

    // instantiate a drop controller for each table
    FlexTableRowDropController flexTableRowDropController1 = new FlexTableRowDropController(table1);
    FlexTableRowDropController flexTableRowDropController2 = new FlexTableRowDropController(table2);
    tableRowDragController.registerDropController(flexTableRowDropController1);
    tableRowDragController.registerDropController(flexTableRowDropController2);
  }

  @Override
  public String getDescription() {
    return "Drag <code>FlexTable</code> rows by their drag handle.";
  }

  @Override
  public Class<?>[] getInvolvedClasses() {
    return new Class[] {
        FlexTableRowExample.class, FlexTableRowDropController.class,
        FlexTableRowDragController.class, DemoFlexTable.class, FlexTableUtil.class,};
  }
}
