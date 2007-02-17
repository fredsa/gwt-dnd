package com.allen_sauer.gwt.dragdrop.demo.client.example;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.demo.client.DemoFlexTable;
import com.allen_sauer.gwt.dragdrop.demo.client.FlexTableRowDragController;
import com.allen_sauer.gwt.dragdrop.demo.client.FlexTableRowDropController;

/**
 * {@link com.allen_sauer.gwt.dragdrop.demo.client.FlexTableRowDropController} example.
 */
public class FlexTableRowExample extends Example {

  public FlexTableRowExample(DragController dragController) {
    super(dragController);
    AbsolutePanel tableExamplePanel = new AbsolutePanel();
    tableExamplePanel.setPixelSize(450, 300);
    setWidget(tableExamplePanel);
    FlexTableRowDragController tableRowDragController = new FlexTableRowDragController(tableExamplePanel);
    DemoFlexTable table1 = new DemoFlexTable(5, 3, tableRowDragController);
    DemoFlexTable table2 = new DemoFlexTable(5, 4, tableRowDragController);
    FlexTableRowDropController flexTableRowDropController1 = new FlexTableRowDropController(table1);
    FlexTableRowDropController flexTableRowDropController2 = new FlexTableRowDropController(table2);
    tableRowDragController.registerDropController(flexTableRowDropController1);
    tableRowDragController.registerDropController(flexTableRowDropController2);
    tableExamplePanel.add(table1, 10, 20);
    tableExamplePanel.add(table2, 230, 40);
  }

  public String getDescription() {
    return "Drag <code>FlexTable</code> rows by their drag handle<br>(currently only implements the 'proxy' drag behavior).";
  }

  public Class getDropControllerClass() {
    return FlexTableRowDropController.class;
  }
}
