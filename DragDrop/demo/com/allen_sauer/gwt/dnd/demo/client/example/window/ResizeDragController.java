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
package com.allen_sauer.gwt.dnd.demo.client.example.window;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.AbstractDragController;
import com.allen_sauer.gwt.dnd.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dnd.demo.client.example.window.WindowPanel.DirectionConstant;

import java.util.HashMap;

final class ResizeDragController extends AbstractDragController {

  private static final int MIN_WIDGET_SIZE = 10;

  private HashMap<Widget, DirectionConstant> directionMap = new HashMap<Widget, DirectionConstant>();

  private WindowPanel windowPanel = null;

  public ResizeDragController(AbsolutePanel boundaryPanel) {
    super(boundaryPanel);
  }

  public void dragMove() {
    int direction = ((ResizeDragController) context.dragController).getDirection(context.draggable).directionBits;
    if ((direction & WindowPanel.DIRECTION_NORTH) != 0) {
      int delta = context.draggable.getAbsoluteTop() - context.desiredDraggableY;
      if (delta != 0) {
        int contentHeight = windowPanel.getContentHeight();
        int newHeight = Math.max(contentHeight + delta, MIN_WIDGET_SIZE);
        if (newHeight != contentHeight) {
          windowPanel.moveBy(0, contentHeight - newHeight);
        }
        windowPanel.setContentSize(windowPanel.getContentWidth(), newHeight);
      }
    } else if ((direction & WindowPanel.DIRECTION_SOUTH) != 0) {
      int delta = context.desiredDraggableY - context.draggable.getAbsoluteTop();
      if (delta != 0) {
        windowPanel.setContentSize(windowPanel.getContentWidth(), windowPanel.getContentHeight()
            + delta);
      }
    }
    if ((direction & WindowPanel.DIRECTION_WEST) != 0) {
      int delta = context.draggable.getAbsoluteLeft() - context.desiredDraggableX;
      if (delta != 0) {
        int contentWidth = windowPanel.getContentWidth();
        int newWidth = Math.max(contentWidth + delta, MIN_WIDGET_SIZE);
        if (newWidth != contentWidth) {
          windowPanel.moveBy(contentWidth - newWidth, 0);
        }
        windowPanel.setContentSize(newWidth, windowPanel.getContentHeight());
      }
    } else if ((direction & WindowPanel.DIRECTION_EAST) != 0) {
      int delta = context.desiredDraggableX - context.draggable.getAbsoluteLeft();
      if (delta != 0) {
        windowPanel.setContentSize(windowPanel.getContentWidth() + delta,
            windowPanel.getContentHeight());
      }
    }
  }

  @Override
  public void dragStart() {
    super.dragStart();
    windowPanel = (WindowPanel) context.draggable.getParent().getParent();
  }

  public void makeDraggable(Widget widget, WindowPanel.DirectionConstant direction) {
    super.makeDraggable(widget);
    directionMap.put(widget, direction);
  }

  protected BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel,
      boolean allowDroppingOnBoundaryPanel) {
    if (allowDroppingOnBoundaryPanel) {
      throw new IllegalArgumentException();
    }
    return new BoundaryDropController(boundaryPanel, false);
  }

  private DirectionConstant getDirection(Widget draggable) {
    return directionMap.get(draggable);
  }
}
