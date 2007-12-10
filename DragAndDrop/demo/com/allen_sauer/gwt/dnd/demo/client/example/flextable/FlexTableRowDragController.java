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
package com.allen_sauer.gwt.dnd.demo.client.example.flextable;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.BoundaryDropController;

/**
 * Allows table rows to dragged by their handle.
 */
public final class FlexTableRowDragController extends PickupDragController {
  private static final String CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE_TABLE_PROXY = "demo-FlexTableRowExample-table-proxy";
  private FlexTable draggableTable;
  private int dragRow;

  public FlexTableRowDragController(AbsolutePanel boundaryPanel) {
    super(boundaryPanel, false);
    setBehaviorDragProxy(true);
    setBehaviorMultipleSelection(false);
  }

  public void dragEnd() {
    super.dragEnd();

    // cleanup
    draggableTable = null;
  }

  public void setBehaviorDragProxy(boolean dragProxyEnabled) {
    if (!dragProxyEnabled) {
      // TODO implement drag proxy behavior
      throw new IllegalArgumentException();
    }
    super.setBehaviorDragProxy(dragProxyEnabled);
  }

  protected BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel, boolean allowDroppingOnBoundaryPanel) {
    if (allowDroppingOnBoundaryPanel) {
      throw new IllegalArgumentException();
    }
    return super.newBoundaryDropController(boundaryPanel, allowDroppingOnBoundaryPanel);
  }

  protected Widget newDragProxy(DragContext context) {
    FlexTable proxy;
    proxy = new FlexTable();
    proxy.addStyleName(CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE_TABLE_PROXY);
    draggableTable = (FlexTable) context.draggable.getParent();
    dragRow = getWidgetRow(context.draggable, draggableTable);
    FlexTableUtil.copyRow(draggableTable, proxy, dragRow, 0);
    return proxy;
  }

  FlexTable getDraggableTable() {
    return draggableTable;
  }

  int getDragRow() {
    return dragRow;
  }

  private int getWidgetRow(Widget widget, FlexTable table) {
    for (int row = 0; row < table.getRowCount(); row++) {
      for (int col = 0; col < table.getCellCount(row); col++) {
        Widget w = table.getWidget(row, col);
        if (w == widget) {
          return row;
        }
      }
    }
    throw new RuntimeException("Unable to determine widget row");
  }
}
