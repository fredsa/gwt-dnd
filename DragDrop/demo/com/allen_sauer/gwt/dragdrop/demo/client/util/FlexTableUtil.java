package com.allen_sauer.gwt.dragdrop.demo.client.util;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Utility class to manipulate {@link com.google.gwt.user.client.ui.FlexTable FlexTables}.
 */
public class FlexTableUtil {

  public static void copyRow(FlexTable sourceTable, FlexTable targetTable, int sourceRow, int targetRow) {
    targetTable.insertRow(targetRow);
    for (int col = 0; col < sourceTable.getCellCount(sourceRow); col++) {
      HTML html = new HTML(sourceTable.getHTML(sourceRow, col));
      targetTable.setWidget(targetRow, col, html);
    }
  }

  public static void moveRow(FlexTable sourceTable, FlexTable targetTable, int sourceRow, int targetRow) {
    if (sourceTable == targetTable && sourceRow >= targetRow) {
      sourceRow++;
    }
    targetTable.insertRow(targetRow);
    for (int col = 0; col < sourceTable.getCellCount(sourceRow); col++) {
      Widget w = sourceTable.getWidget(sourceRow, col);
      if (w != null) {
        targetTable.setWidget(targetRow, col, w);
      } else {
        HTML html = new HTML(sourceTable.getHTML(sourceRow, col));
        targetTable.setWidget(targetRow, col, html);
      }
    }
    sourceTable.removeRow(sourceRow);
  }

}
