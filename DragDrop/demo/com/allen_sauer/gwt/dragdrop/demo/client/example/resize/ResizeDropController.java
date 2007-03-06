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

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;

final class ResizeDropController extends BoundaryDropController {

  private static final int MIN_WIDGET_SIZE = 10;

  private ResizePanel resizePanel;

  public ResizeDropController(AbsolutePanel boundaryPanel) {
    super(boundaryPanel, false);
  }

  public void onEnter(Widget reference, Widget draggable, DragController dragController) {
    super.onEnter(reference, draggable, dragController);
    resizePanel = ((ResizeDragController) dragController).getResizePanel();
  }

  public void onLeave(Widget draggable, DragController dragController) {
    super.onLeave(draggable, dragController);
    resizePanel = null;
  }

  public void onMove(Widget reference, Widget draggable, DragController dragController) {
    super.onMove(reference, draggable, dragController);

    int direction = ((ResizeDragController) dragController).getDirection(draggable).getDirectionBits();
    if ((direction & ResizePanel.DIRECTION_NORTH) != 0) {
      int delta = draggable.getAbsoluteTop() - reference.getAbsoluteTop();
      if (delta != 0) {
        int contentHeight = resizePanel.getContentHeight();
        int newHeight = Math.max(contentHeight + delta, MIN_WIDGET_SIZE);
        if (newHeight != contentHeight) {
          resizePanel.moveBy(0,  contentHeight - newHeight);
        }
        resizePanel.setContentSize(resizePanel.getContentWidth(), newHeight);
      }
    } else if ((direction & ResizePanel.DIRECTION_SOUTH) != 0) {
      int delta = reference.getAbsoluteTop() - draggable.getAbsoluteTop();
      if (delta != 0) {
        resizePanel.setContentSize(resizePanel.getContentWidth(), resizePanel.getContentHeight() + delta);
      }
    }
    if ((direction & ResizePanel.DIRECTION_WEST) != 0) {
      int delta = draggable.getAbsoluteLeft() - reference.getAbsoluteLeft();
      if (delta != 0) {
        int contentWidth = resizePanel.getContentWidth();
        int newWidth = Math.max(contentWidth + delta, MIN_WIDGET_SIZE);
        if (newWidth != contentWidth) {
          resizePanel.moveBy(contentWidth - newWidth, 0);
        }
        resizePanel.setContentSize(newWidth, resizePanel.getContentHeight());
      }
    } else if ((direction & ResizePanel.DIRECTION_EAST) != 0) {
      int delta = reference.getAbsoluteLeft() - draggable.getAbsoluteLeft();
      if (delta != 0) {
        resizePanel.setContentSize(resizePanel.getContentWidth() + delta, resizePanel.getContentHeight());
      }
    }
  }
}
