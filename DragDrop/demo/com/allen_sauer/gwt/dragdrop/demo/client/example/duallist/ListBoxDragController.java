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

import com.allen_sauer.gwt.dragdrop.client.DragContext;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.VetoDragException;

/**
 * DragController for {@link DualListExample}.
 */
public class ListBoxDragController extends PickupDragController {
  private static final String CSS_DEMO_DUAL_LIST_EXAMPLE_DRAG_PROXY = "demo-DualListExample-drag-proxy";

  public ListBoxDragController(DualListBox dualListBox) {
    super(dualListBox, false);
    setBehaviorDragProxy(true);
    setBehaviorMultipleSelection(false);
  }

  public void previewDragEnd() throws VetoDragException {
    if (((MouseListBox) context.draggable).getSelectedWidgetCount() == 0) {
      throw new VetoDragException();
    }
    super.previewDragEnd();
  }

  public void setBehaviorDragProxy(boolean dragProxyEnabled) {
    if (!dragProxyEnabled) {
      // TODO implement drag proxy behavior
      throw new IllegalArgumentException();
    }
    super.setBehaviorDragProxy(dragProxyEnabled);
  }

  protected Widget newDragProxy(DragContext context) {
    MouseListBox currentDraggableListBox = (MouseListBox) context.draggable;
    MouseListBox proxyMouseListBox = new MouseListBox(currentDraggableListBox.getSelectedWidgetCount());
    proxyMouseListBox.setWidth(currentDraggableListBox.getOffsetWidth() + "px");
    proxyMouseListBox.addStyleName(CSS_DEMO_DUAL_LIST_EXAMPLE_DRAG_PROXY);
    DualListBox.copyOrmoveItems(currentDraggableListBox, proxyMouseListBox, true, DualListBox.OPERATION_COPY);
    return proxyMouseListBox;
  }
}
