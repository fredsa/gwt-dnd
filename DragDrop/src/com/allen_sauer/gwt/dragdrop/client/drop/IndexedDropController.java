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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.UIUtil;

import java.util.Iterator;

/**
 * A
 * {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.AbstractDropController}
 * for instances of
 * {@link com.allen_sauer.gwt.dragdrop.demo.client.IndexedFlowPanel}.
 */
public class IndexedDropController extends AbstractPositioningDropController {

  IndexedFlowPanel dropTargetPanel;

  public IndexedDropController(Panel dropTargetPanel) {
    super(dropTargetPanel);
    this.dropTargetPanel = (IndexedFlowPanel) dropTargetPanel;
  }

  public String getDropTargetStyleName() {
    return "dragdrop-drop-target dragdrop-flow-panel-drop-target";
  }

  public void onDrop(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onDrop(dragAndDropController, draggable);
    indexedAdd(draggable, draggable);
  }

  public void onPreDropEnter(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropEnter(dragAndDropController, draggable);
    UIUtil.resetStylePositionStatic(dragAndDropController.getPostioningBox().getElement());
  }

  public void onPreDropLeave(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropLeave(dragAndDropController, draggable);
    getDropTargetPanel().remove(dragAndDropController.getPostioningBox());
  }

  public void onPreDropMove(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropMove(dragAndDropController, draggable);
    indexedAdd(draggable, dragAndDropController.getPostioningBox());
  }

  private void indexedAdd(Widget draggable, Widget widget) {
    Location draggableCenter = new Area(draggable, null).getCenter();
    for (Iterator iterator = this.dropTargetPanel.iterator(); iterator.hasNext();) {
      Widget child = (Widget) iterator.next();
      Area childArea = new Area(child, null);
      if (childArea.intersects(draggableCenter)) {
        int childIndex = this.dropTargetPanel.getWidgetIndex(child);
        if (childArea.inBottomRight(draggableCenter)) {
          // place the draggable after the intersecting child
          childIndex++;
        }
        int widgetIndex = this.dropTargetPanel.getWidgetIndex(widget);
        if (widgetIndex == -1
            || (widgetIndex != childIndex && widgetIndex != childIndex - 1)) {
          if (childIndex > widgetIndex) {
            //adjust index for removal of widget
            this.dropTargetPanel.insert(widget, childIndex - 1);
          } else {
            this.dropTargetPanel.insert(widget, childIndex);
          }
        }
        return;
      }
    }
    this.dropTargetPanel.add(widget);
  }

}
