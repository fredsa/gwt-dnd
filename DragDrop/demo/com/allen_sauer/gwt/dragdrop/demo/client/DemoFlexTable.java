package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

/**
 * Table to demonstrate draggable rows and columns.
 */
public class DemoFlexTable extends FlexTable {

  public DemoFlexTable(int rows, int cols, FlexTableRowDragController tableRowDragController) {
    for (int row = 0; row < rows; row++) {
      HTML handle = new HTML("[drag-here]");
      handle.addStyleName("demo-drag-handle");
      setWidget(row, 0, handle);
      tableRowDragController.makeDraggable(handle);
      for (int col = 1; col < cols; col++) {
        setHTML(row, col, "[" + row + ", " + col + "]");
      }
    }
  }
}
