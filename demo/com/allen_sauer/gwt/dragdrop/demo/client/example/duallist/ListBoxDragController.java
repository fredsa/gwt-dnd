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
package com.allen_sauer.gwt.dragdrop.demo.client.example.duallist;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.VetoDragException;

public class ListBoxDragController extends PickupDragController {

  private MouseListBox currentDraggableListBox;

  public ListBoxDragController(DualListBox dualListBox) {
    super(dualListBox, false);
  }

  public void dragEnd(Widget draggable, Widget dropTarget) {
    super.dragEnd(draggable, dropTarget);
  }

  public void dragStart(Widget draggable) {
    currentDraggableListBox = (MouseListBox) draggable;
    super.dragStart(draggable);
  }

  public MouseListBox getCurrentDraggableListBox() {
    return currentDraggableListBox;
  }

  public Widget getMovableWidget() {
    return super.getMovableWidget();
  }

  public void notifyDragEnd(DragEndEvent dragEndEvent) {
    super.notifyDragEnd(dragEndEvent);
    // cleanup 
    currentDraggableListBox = null;
  }

  public void previewDragStart(Widget draggable) throws VetoDragException {
    if (((MouseListBox) draggable).getSelectedWidgetCount() == 0) {
      throw new VetoDragException();
    }
    super.previewDragStart(draggable);
  }

  protected Widget maybeNewDraggableProxy(Widget draggable) {
    MouseListBox proxyMouseListBox = new MouseListBox(currentDraggableListBox.getSelectedWidgetCount());
    proxyMouseListBox.addStyleName(STYLE_PROXY);
    DualListBox.copyOrmoveItems(currentDraggableListBox, proxyMouseListBox, true, DualListBox.OPERATION_COPY);
    return proxyMouseListBox;
  }
}
