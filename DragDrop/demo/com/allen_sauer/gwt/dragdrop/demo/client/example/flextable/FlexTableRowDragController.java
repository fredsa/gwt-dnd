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
package com.allen_sauer.gwt.dragdrop.demo.client.example.flextable;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dragdrop.demo.client.util.FlexTableUtil;

/**
 * Allows table rows to dragged by their handle.
 */
final class FlexTableRowDragController extends PickupDragController {

  private static final String STYLE_DEMO_TABLE_PROXY = "demo-table-proxy";

  private FlexTable draggableTable;
  private int dragRow;

  public FlexTableRowDragController(AbsolutePanel boundaryPanel) {
    super(boundaryPanel);
    // TODO support 'classic' drop behavior
    setDragProxyEnabled(true);
  }

  public void dragEnd(Widget draggable, Widget dropTarget) {
    draggableTable.getRowFormatter().removeStyleName(dragRow, STYLE_DRAGGING);
    super.dragEnd(draggable, dropTarget);
  }

  public void dragStart(Widget draggable) {
    draggableTable = (FlexTable) draggable.getParent();
    dragRow = getWidgetRow(draggable, draggableTable);
    draggableTable.getRowFormatter().setStyleName(dragRow, STYLE_DRAGGING);
    super.dragStart(draggable);
  }

  public FlexTable getDraggableTable() {
    return draggableTable;
  }

  public int getDragRow() {
    return dragRow;
  }

  public BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel) {
    return new BoundaryDropController(boundaryPanel, false);
  }

  protected Widget maybeNewDraggableProxy(Widget draggable) {
    FlexTable proxy;
    proxy = new FlexTable();
    proxy.addStyleName(STYLE_PROXY);
    proxy.addStyleName(STYLE_DEMO_TABLE_PROXY);
    FlexTableUtil.copyRow(draggableTable, proxy, dragRow, 0);
    return proxy;
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
