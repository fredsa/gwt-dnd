/*
 * Copyright 2006 Fred Sauer
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
package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.UIUtil;

/**
 * A {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DropController} for
 * instances of
 * {@link com.allen_sauer.gwt.dragdrop.demo.client.IndexedFlowPanel}.
 */
public class IndexedDropController extends AbstractPositioningDropController {

  private IndexedPanel dropTargetPanel;

  public IndexedDropController(IndexedPanel dropTargetPanel) {
    super((Panel) dropTargetPanel);
    this.dropTargetPanel = dropTargetPanel;
  }

  public void drop(Widget widget) {
    super.drop(widget);
    insert(widget, this.dropTargetPanel.getWidgetCount());
  }

  public String getDropTargetStyleName() {
    return super.getDropTargetStyleName() + " dragdrop-flow-panel-drop-target";
  }

  public boolean onDrop(Widget draggable, DragController dragController) {
    int index = this.dropTargetPanel.getWidgetIndex(getPositionerWidget());
    boolean result = super.onDrop(draggable, dragController);
    insert(draggable, index);
    return result;
  }

  public void onEnter(Widget draggable, DragController dragController) {
    super.onEnter(draggable, dragController);
    UIUtil.resetStylePositionStatic(getPositionerWidget().getElement());
  }

  public void onLeave(Widget draggable, DragController dragController) {
    super.onLeave(draggable, dragController);
  }

  public void onMove(Widget draggable, DragController dragController) {
    super.onMove(draggable, dragController);
    indexedAdd(draggable, getPositionerWidget());
  }

  private void indexedAdd(Widget draggable, Widget widget) {
    Location draggableCenter = new Area(draggable, null).getCenter();
    for (int i = 0; i < this.dropTargetPanel.getWidgetCount(); i++) {
      Widget child = this.dropTargetPanel.getWidget(i);
      Area childArea = new Area(child, null);
      if (childArea.intersects(draggableCenter)) {
        int childIndex = this.dropTargetPanel.getWidgetIndex(child);
        if (childArea.inBottomRight(draggableCenter)) {
          // place the draggable after the intersecting child
          childIndex++;
        }
        int widgetIndex = this.dropTargetPanel.getWidgetIndex(widget);
        if ((widgetIndex == -1) || ((widgetIndex != childIndex) && (widgetIndex != childIndex - 1))) {
          if (childIndex > widgetIndex) {
            // adjust index for removal of widget
            insert(widget, childIndex - 1);
          } else {
            insert(widget, childIndex);
          }
        }
        return;
      }
    }
    ((Panel) this.dropTargetPanel).add(widget);
  }

  // TODO remove after enhancement for issue 616
  // http://code.google.com/p/google-web-toolkit/issues/detail?id=616
  private void insert(Widget widget, int beforeIndex) {
    if (this.dropTargetPanel instanceof DeckPanel) {
      ((DeckPanel) this.dropTargetPanel).insert(widget, beforeIndex);
    } else if (this.dropTargetPanel instanceof HorizontalPanel) {
      ((HorizontalPanel) this.dropTargetPanel).insert(widget, beforeIndex);
    } else if (this.dropTargetPanel instanceof VerticalPanel) {
      ((VerticalPanel) this.dropTargetPanel).insert(widget, beforeIndex);
    } else if (this.dropTargetPanel instanceof IndexedFlowPanel) {
      ((IndexedFlowPanel) this.dropTargetPanel).insert(widget, beforeIndex);
    } else {
      throw new RuntimeException("Method insert(Widget widget, int beforeIndex) not supported by "
          + GWT.getTypeName(this.dropTargetPanel));
    }
  }

}
