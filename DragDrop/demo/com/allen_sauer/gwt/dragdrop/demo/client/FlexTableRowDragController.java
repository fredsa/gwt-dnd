package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.BoundryDropController;
import com.allen_sauer.gwt.dragdrop.demo.client.util.FlexTableUtil;

/**
 * Allows table rows to dragged by their handle.
 */
public class FlexTableRowDragController extends DragController {

  private static final String STYLE_DEMO_TABLE_PROXY = "demo-table-proxy";
  private FlexTable draggableTable;
  private int dragRow;

  public FlexTableRowDragController(AbsolutePanel boundryPanel) {
    super(boundryPanel);
  }

  public void dragEnd(Widget draggable, Widget dropTarget) {
    draggableTable.getRowFormatter().removeStyleName(dragRow, STYLE_DRAGGING);
    super.dragEnd(draggable, dropTarget);
  }

  // TODO support 'classic' drop behavior
  // TODO do not allow drag outside our boundry panel
  public void dragStart(Widget draggable) {
    draggableTable = (FlexTable) draggable.getParent();
    dragRow = getWidgetRow(draggable, draggableTable);
    draggableTable.getRowFormatter().setStyleName(dragRow, STYLE_DRAGGING);
    super.dragStart(draggable);
  }

  public FlexTable getDraggableTable() {
    return this.draggableTable;
  }

  public int getDragRow() {
    return this.dragRow;
  }

  protected BoundryDropController newBoundryDropController(AbsolutePanel boundryPanel) {
    return new BoundryDropController(boundryPanel, false);
  }

  protected Widget newDraggableProxy(Widget draggable) {
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
