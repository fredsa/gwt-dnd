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

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.drop.AbstractPositioningDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.VetoDropException;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

/**
 * Allows one or more table rows to be dropped into an existing table.
 */
public final class FlexTableRowDropController extends AbstractPositioningDropController {

  private static final String CSS_DEMO_TABLE_POSITIONER = "demo-table-positioner";

  private FlexTable flexTable;
  private int targetRow;

  public FlexTableRowDropController(FlexTable flexTable) {
    super(flexTable);
    this.flexTable = flexTable;
  }

  public DragEndEvent onDrop(Widget reference, Widget draggable, DragController dragController) {
    super.onDrop(reference, draggable, dragController);
    FlexTableRowDragController trDragController = (FlexTableRowDragController) dragController;
    FlexTableUtil.moveRow(trDragController.getDraggableTable(), flexTable, trDragController.getDragRow(), targetRow + 1);
    return new FlexTableRowDragEndEvent(draggable, flexTable, targetRow + 1);
  }

  public void onMove(Widget reference, Widget draggable, DragController dragController) {
    super.onMove(reference, draggable, dragController);
    int row = determineRow(reference);
    Widget w = flexTable.getWidget(row == -1 ? 0 : row, 0);
    Location widgetLocation = new WidgetLocation(w, dragController.getBoundaryPanel());
    Location tableLocation = new WidgetLocation(flexTable, dragController.getBoundaryPanel());
    dragController.getBoundaryPanel().add(getPositioner(), tableLocation.getLeft(),
        widgetLocation.getTop() + (row == -1 ? 0 : w.getOffsetHeight()));
  }

  public void onPreviewDrop(Widget reference, Widget draggable, DragController dragController) throws VetoDropException {
    super.onPreviewDrop(reference, draggable, dragController);
    targetRow = determineRow(reference);
  }

  protected Widget newPositioner(Widget reference) {
    Widget p = new SimplePanel();
    p.addStyleName(CSS_DEMO_TABLE_POSITIONER);
    p.setPixelSize(flexTable.getOffsetWidth(), 1);
    return p;
  }

  /**
   * Determines where the reference widget is in relation to the rows of our
   * drop target. Use the middle of the reference widget and the middle of the
   * rows to decide to insert-before or insert-after.
   * 
   * @param reference widget whose position we compare to
   * @return row number after which insertion is desired, or -1 to insert before
   *         row 0
   */
  private int determineRow(Widget reference) {
    int refY = reference.getAbsoluteTop() + reference.getOffsetHeight() / 2;
    for (int row = flexTable.getRowCount() - 1; row >= 0; row--) {
      Widget w = flexTable.getWidget(row, 0);
      int y = w.getAbsoluteTop() + w.getOffsetHeight() / 2;
      if (y < refY) {
        return row;
      }
    }
    return -1;
  }
}
