/*
 * Copyright 2008 Fred Sauer
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
package com.allen_sauer.gwt.dnd.demo.client.example.duallist;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;

import java.util.ArrayList;

/**
 * DragController for {@link DualListExample}.
 */
class ListBoxDragController extends PickupDragController {

  ListBoxDragController(DualListBox dualListBox) {
    super(dualListBox, false);
    setBehaviorDragProxy(true);
    setBehaviorMultipleSelection(true);
  }

  @Override
  public void dragEnd() {
    // process drop first
    super.dragEnd();

    if (context.vetoException == null) {
      // remove original items
      MouseListBox currentMouseListBox = (MouseListBox) context.draggable.getParent().getParent();
      while (!context.selectedWidgets.isEmpty()) {
        Widget widget = context.selectedWidgets.get(0);
        toggleSelection(widget);
        currentMouseListBox.remove(widget);
      }
    }
  }

  @Override
  public void previewDragStart() throws VetoDragException {
    super.previewDragStart();
    if (context.selectedWidgets.isEmpty()) {
      throw new VetoDragException();
    }
  }

  @Override
  public void setBehaviorDragProxy(boolean dragProxyEnabled) {
    if (!dragProxyEnabled) {
      throw new IllegalArgumentException();
    }
    super.setBehaviorDragProxy(dragProxyEnabled);
  }

  @Override
  public void toggleSelection(Widget draggable) {
    super.toggleSelection(draggable);
    MouseListBox currentMouseListBox = (MouseListBox) draggable.getParent().getParent();
    ArrayList<Widget> otherWidgets = new ArrayList<Widget>();
    for (Widget widget : context.selectedWidgets) {
      if (widget.getParent().getParent() != currentMouseListBox) {
        otherWidgets.add(widget);
      }
    }
    for (Widget widget : otherWidgets) {
      super.toggleSelection(widget);
    }
  }

  @Override
  protected Widget newDragProxy(DragContext context) {
    MouseListBox currentMouseListBox = (MouseListBox) context.draggable.getParent().getParent();
    MouseListBox proxyMouseListBox = new MouseListBox(context.selectedWidgets.size());
    proxyMouseListBox.setWidth(DOMUtil.getClientWidth(currentMouseListBox.getElement()) + "px");
    for (Widget widget : context.selectedWidgets) {
      HTML htmlClone = new HTML(DOM.getInnerHTML(widget.getElement()));
      proxyMouseListBox.add(htmlClone);
    }
    return proxyMouseListBox;
  }

  ArrayList<Widget> getSelectedWidgets(MouseListBox mouseListBox) {
    ArrayList<Widget> widgetList = new ArrayList<Widget>();
    for (Widget widget : context.selectedWidgets) {
      if (widget.getParent().getParent() == mouseListBox) {
        widgetList.add(widget);
      }
    }
    return widgetList;
  }
}
