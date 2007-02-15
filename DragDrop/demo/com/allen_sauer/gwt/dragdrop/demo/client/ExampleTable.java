package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;

/**
 * Table to demonstrate draggable rows and columns.
 */
public class ExampleTable extends Grid {

  public ExampleTable(int rows, int cols, TableRowDragController tableRowDragController) {
    super(rows, cols);
    for (int row = 0; row < rows; row++) {
      HTML handle = new HTML("[drag-here]");
      setWidget(row, 0, handle);
      tableRowDragController.makeDraggable(handle);
      for (int col = 1; col < cols; col++) {
        setHTML(row, col, "[" + row + ", " + col + "]");
      }
    }
  }

}
