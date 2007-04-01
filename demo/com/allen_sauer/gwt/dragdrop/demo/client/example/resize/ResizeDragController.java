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
package com.allen_sauer.gwt.dragdrop.demo.client.example.resize;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.AbstractDragController;
import com.allen_sauer.gwt.dragdrop.client.VetoDragException;
import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;
import com.allen_sauer.gwt.dragdrop.demo.client.example.resize.ResizePanel.DirectionConstant;

import java.util.HashMap;

final class ResizeDragController extends AbstractDragController {

  private Widget dummyMovableWidget = new HTML();
  private HashMap directionMap = new HashMap();
  private ResizePanel resizePanel;

  public ResizeDragController(AbsolutePanel boundaryPanel) {
    super(boundaryPanel);
    DOM.setStyleAttribute(dummyMovableWidget.getElement(), "visibility", "hidden");
  }

  public void dragEnd(Widget draggable, Widget dropTarget) {
    super.dragEnd(draggable, dropTarget);
    dummyMovableWidget.removeFromParent();
  }

  public void dragStart(Widget draggable) {
    super.dragStart(draggable);
    resizePanel = (ResizePanel) draggable.getParent().getParent();
    Location location = new WidgetLocation(draggable, getBoundaryPanel());
    getBoundaryPanel().add(dummyMovableWidget, location.getLeft(), location.getTop());
  }

  public DirectionConstant getDirection(Widget draggable) {
    return (DirectionConstant) directionMap.get(draggable);
  }

  public Widget getMovableWidget() {
    return dummyMovableWidget;
  }

  public ResizePanel getResizePanel() {
    return resizePanel;
  }

  public void makeDraggable(Widget widget, ResizePanel.DirectionConstant direction) {
    super.makeDraggable(widget);
    directionMap.put(widget, direction);
  }

  public BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel) {
    return new ResizeDropController(boundaryPanel);
  }

  public void previewDragEnd(Widget draggable, Widget dropTarget) throws VetoDragException {
    // we don't actually use the drop side of the drag-and-drop operation
    throw new VetoDragException();
  }
}
