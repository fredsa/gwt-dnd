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

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class ResizePanel extends ComplexPanel {

  private Grid grid = new Grid(3, 3);
  private ScrollPanel scrollPanel;
  private ResizeDragController resizeDragController;

  public ResizePanel(ResizeDragController resizeDragController, ScrollPanel scrollPanel) {
    this.resizeDragController = resizeDragController;
    this.scrollPanel = scrollPanel;

    grid.setCellSpacing(0);
    setElement(grid.getElement());

    setupCell(0, 0, "demo-resize edge top left top-left");
    setupCell(0, 1, "demo-resize edge top top-center");
    setupCell(0, 2, "demo-resize edge top right top-right");

    setupCell(1, 0, "demo-resize edge left left-center");
    grid.setWidget(1, 1, scrollPanel);
    setupCell(1, 2, "demo-resize edge right right-center");

    setupCell(2, 0, "demo-resize edge bottom left bottom-left");
    setupCell(2, 1, "demo-resize edge bottom bottom-center");
    setupCell(2, 2, "demo-resize edge bottom right bottom-right");
  }

  public ScrollPanel getScrollPanel() {
    return this.scrollPanel;
  }

  protected void onLoad() {
    super.onLoad();
    scrollPanel.setPixelSize(scrollPanel.getOffsetWidth() / 2, scrollPanel.getOffsetHeight() / 2);
  }

  private void setupCell(int row, int col, String styleName) {
    Widget widget = new FocusPanel();
    grid.setWidget(row, col, widget);
    resizeDragController.makeDraggable(widget);
    widget.setHeight("100%");
    widget.setWidth("100%");
    grid.getCellFormatter().addStyleName(row, col, styleName);
  }
}
