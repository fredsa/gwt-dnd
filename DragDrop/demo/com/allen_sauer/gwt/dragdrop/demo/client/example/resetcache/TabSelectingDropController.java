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
package com.allen_sauer.gwt.dragdrop.demo.client.example.resetcache;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.AbstractDragController;
import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.drop.AbstractDropController;
import com.allen_sauer.gwt.dragdrop.client.util.DOMUtil;

public class TabSelectingDropController extends AbstractDropController {
  private final int tabIndex;
  private final TabPanel tabPanel;

  public TabSelectingDropController(Widget tabWidgetDropTarget, TabPanel tabPanel, int tabIndex) {
    super(tabWidgetDropTarget);
    this.tabPanel = tabPanel;
    this.tabIndex = tabIndex;
  }

  public DragEndEvent onDrop(Widget reference, Widget draggable, DragController dragController) {
    DragEndEvent dragEndEvent = super.onDrop(reference, draggable, dragController);

    // assume content widget is a panel for now
    AbsolutePanel absolutePanel = (AbsolutePanel) tabPanel.getWidget(tabIndex);

    // temporarily (invisibly) add draggable to get dimensions
    DOM.setStyleAttribute(draggable.getElement(), "visibility", "hidden");
    absolutePanel.add(draggable, 0, 0);

    // move widget to random location, and restore visibility
    int left = Random.nextInt(DOMUtil.getClientWidth(absolutePanel.getElement()) - draggable.getOffsetWidth());
    int top = Random.nextInt(DOMUtil.getClientHeight(absolutePanel.getElement()) - draggable.getOffsetHeight());
    absolutePanel.add(draggable, left, top);
    DOM.setStyleAttribute(draggable.getElement(), "visibility", "");

    // return drag end event, which will have come from our makeDragEndEvent()
    return dragEndEvent;
  }

  public void onEnter(Widget reference, Widget draggable, DragController dragController) {
    super.onEnter(reference, draggable, dragController);
    tabPanel.selectTab(tabIndex);
    ((AbstractDragController) dragController).resetCache();
  }

  protected DragEndEvent makeDragEndEvent(Widget reference, Widget draggable, DragController dragController) {
    return new DragEndEvent(draggable, tabPanel.getWidget(tabIndex));
  }
}
