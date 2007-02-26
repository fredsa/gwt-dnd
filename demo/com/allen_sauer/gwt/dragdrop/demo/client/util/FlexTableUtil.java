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
