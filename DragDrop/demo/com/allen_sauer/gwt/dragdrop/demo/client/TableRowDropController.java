package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbstractPositioningDropController;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

/**
 * Allows one or more table rows to be dropped into an existing table.
 */
public class TableRowDropController extends AbstractPositioningDropController {

  private HTMLTable htmlTable;

  public TableRowDropController(HTMLTable htmlTable) {
    super(htmlTable);
    this.htmlTable = htmlTable;
  }

  public void onMove(Widget reference, Widget draggable, DragController dragController) {
    super.onMove(reference, draggable, dragController);
    int refY = reference.getAbsoluteTop() + reference.getOffsetHeight() / 2;
    for (int row = htmlTable.getRowCount() - 1; row >= 0; row--) {
      Widget w = htmlTable.getWidget(row, 0);
      int y = w.getAbsoluteTop() + w.getOffsetHeight() / 2;
      if (y < refY) {
        Location widgetLocation = new Location(w, dragController.getBoundryPanel());
        Location tableLocation = new Location(htmlTable, dragController.getBoundryPanel());
        dragController.getBoundryPanel().add(getPositioner(), tableLocation.getLeft(),
            widgetLocation.getTop() + w.getOffsetHeight());
        return;
      } else if (row == 0) {
        Location widgetLocation = new Location(w, dragController.getBoundryPanel());
        Location tableLocation = new Location(htmlTable, dragController.getBoundryPanel());
        dragController.getBoundryPanel().add(getPositioner(), tableLocation.getLeft(), widgetLocation.getTop());
      }
    }
  }

  protected Widget newPositioner(Widget reference) {
    Widget p = new SimplePanel();
    p.addStyleName("dragdrop-table-positioner");
    p.setPixelSize(htmlTable.getOffsetWidth(), 1);
    return p;
  }
}
