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

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragAndDropController;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.UIUtil;

import java.util.Iterator;

/**
 * A {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DropController}
 * for instances of
 * {@link com.allen_sauer.gwt.dragdrop.demo.client.IndexedFlowPanel}.
 */
public class IndexedDropController extends PositioningDropController {

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
    UIUtil.positionStatic(draggable.getElement());
  }

  public void onPreDropEnter(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropEnter(dragAndDropController, draggable);
    UIUtil.positionStatic(dragAndDropController.getPostioningBox().getElement());
    indexedAdd(draggable, dragAndDropController.getPostioningBox());
  }

  public void onPreDropLeave(DragAndDropController dragAndDropController,
      Widget draggable) {
    super.onPreDropLeave(dragAndDropController, draggable);
    getDropTargetPanel().remove(dragAndDropController.getPostioningBox());
  }

  private void indexedAdd(Widget draggable, Widget widget) {
    Location draggableCenter = new Area(draggable, null).getCenter();
    for (Iterator iterator = this.dropTargetPanel.iterator(); iterator.hasNext();) {
      Widget element = (Widget) iterator.next();
      Area elementArea = new Area(element, null);
      if (elementArea.intersects(draggableCenter)) {
        this.dropTargetPanel.insert(widget,
            this.dropTargetPanel.getWidgetIndex(element));
        return;
      }
    }
    this.dropTargetPanel.add(widget);
  }

}
