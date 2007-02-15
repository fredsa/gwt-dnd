package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.BoundryDropController;

/**
 * Allows table rows to dragged by their handle.
 */
public class TableRowDragController extends DragController {

  private static final String STYLE_TABLE_PROXY = "table-proxy";
  private int columns;
  private HTMLTable draggableTable;
  private int dragRow;

  public TableRowDragController(AbsolutePanel boundryPanel) {
    super(boundryPanel);
  }

  public void dragEnd(Widget draggable, Widget dropTarget) {
    draggableTable.getRowFormatter().removeStyleName(dragRow, STYLE_DRAGGING);
    super.dragEnd(draggable, dropTarget);
  }

  public void dragStart(Widget draggable) {
    draggableTable = (HTMLTable) draggable.getParent();
    dragRow = getWidgetRow(draggable, draggableTable);
    columns = draggableTable.getCellCount(dragRow);
    draggableTable.getRowFormatter().setStyleName(dragRow, STYLE_DRAGGING);
    super.dragStart(draggable);
  }

  protected BoundryDropController newBoundryDropController(AbsolutePanel boundryPanel) {
    return new BoundryDropController(boundryPanel, false);
  }

  protected Widget newDraggableProxy(Widget draggable) {
    HTMLTable proxy;
    proxy = new Grid(1, columns);
    proxy.addStyleName(STYLE_PROXY);
    proxy.addStyleName(STYLE_TABLE_PROXY);
    for (int col = 0; col < columns; col++) {
      HTML html = new HTML(draggableTable.getHTML(dragRow, col));
      Widget w = draggableTable.getWidget(dragRow, col);
      if (w != null) {
        html.setPixelSize(w.getOffsetWidth(), w.getOffsetHeight());
      }
      proxy.setWidget(0, col, html);
    }
    return proxy;
  }

  private int getWidgetRow(Widget widget, HTMLTable table) {
    for (int row = 0; row < table.getRowCount(); row++) {
      for (int col = 1; col < table.getCellCount(row); col++) {
        Widget w = table.getWidget(row, col);
        if (w == widget) {
          return row;
        }
      }
    }
    return 0;
  }

}
