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

import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;

/**
 * Allows table rows to dragged by their handle.
 */
public final class FlexTableRowDragController extends PickupDragController {
  private static final String CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE_DRAGGING = "demo-FlexTableRowExample-dragging";
  private static final String CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE_TABLE_PROXY = "demo-FlexTableRowExample-table-proxy";
  private FlexTable draggableTable;
  private int dragRow;

  public FlexTableRowDragController(AbsolutePanel boundaryPanel) {
    super(boundaryPanel, false);
    setBehaviorDragProxy(true);
  }

  public void dragEnd(Widget draggable, Widget dropTarget) {
    draggableTable.getRowFormatter().removeStyleName(dragRow, CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE_DRAGGING);
    super.dragEnd(draggable, dropTarget);
  }

  public Widget dragStart(Widget draggable) {
    draggableTable = (FlexTable) draggable.getParent();
    dragRow = getWidgetRow(draggable, draggableTable);
    draggableTable.getRowFormatter().addStyleName(dragRow, CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE_DRAGGING);

    return super.dragStart(draggable);
  }

  public FlexTable getDraggableTable() {
    return draggableTable;
  }

  public int getDragRow() {
    return dragRow;
  }

  public void notifyDragEnd(DragEndEvent dragEndEvent) {
    super.notifyDragEnd(dragEndEvent);
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

  protected Widget newDragProxy(Widget draggable) {
    FlexTable proxy;
    proxy = new FlexTable();
    proxy.addStyleName(CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE_TABLE_PROXY);
    FlexTableUtil.copyRow(draggableTable, proxy, dragRow, 0);
    return proxy;
  }

  protected void restoreSelectedWidgetsLocation() {
    // Nothing to restore because we use a drag proxy
  }

  protected void restoreSelectedWidgetsStyle() {
    // Nothing to restore because we use a drag proxy
  }

  protected void saveSelectedWidgetsLocationAndStyle() {
    // Nothing to save because we use a drag proxy
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
