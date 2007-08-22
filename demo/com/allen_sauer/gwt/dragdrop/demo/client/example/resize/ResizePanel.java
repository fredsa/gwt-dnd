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
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

final class ResizePanel extends SimplePanel {

  /**
   * ResizePanel direction constant, used in
   * {@link ResizeDragController#makeDraggable(com.google.gwt.user.client.ui.Widget, com.allen_sauer.gwt.dragdrop.demo.client.example.resize.ResizePanel.DirectionConstant)}.
   */
  public static class DirectionConstant {
    public final int directionBits;
    public final String directionLetters;

    private DirectionConstant(int directionBits, String directionLetters) {
      this.directionBits = directionBits;
      this.directionLetters = directionLetters;
    }
  }

  /**
   * Specifies that resizing occur at the east edge.
   */
  public static final int DIRECTION_EAST = 0x0001;

  /**
   * Specifies that resizing occur at the both edge.
   */
  public static final int DIRECTION_NORTH = 0x0002;

  /**
   * Specifies that resizing occur at the south edge.
   */
  public static final int DIRECTION_SOUTH = 0x0004;

  /**
   * Specifies that resizing occur at the west edge.
   */
  public static final int DIRECTION_WEST = 0x0008;

  /**
   * Specifies that resizing occur at the east edge.
   */
  public static final DirectionConstant EAST = new DirectionConstant(DIRECTION_EAST, "e");

  /**
   * Specifies that resizing occur at the both edge.
   */
  public static final DirectionConstant NORTH = new DirectionConstant(DIRECTION_NORTH, "n");

  /**
   * Specifies that resizing occur at the north-east edge.
   */
  public static final DirectionConstant NORTH_EAST = new DirectionConstant(DIRECTION_NORTH | DIRECTION_EAST, "ne");

  /**
   * Specifies that resizing occur at the north-west edge.
   */
  public static final DirectionConstant NORTH_WEST = new DirectionConstant(DIRECTION_NORTH | DIRECTION_WEST, "nw");

  /**
   * Specifies that resizing occur at the south edge.
   */
  public static final DirectionConstant SOUTH = new DirectionConstant(DIRECTION_SOUTH, "s");

  /**
   * Specifies that resizing occur at the south-east edge.
   */
  public static final DirectionConstant SOUTH_EAST = new DirectionConstant(DIRECTION_SOUTH | DIRECTION_EAST, "se");

  /**
   * Specifies that resizing occur at the south-west edge.
   */
  public static final DirectionConstant SOUTH_WEST = new DirectionConstant(DIRECTION_SOUTH | DIRECTION_WEST, "sw");

  /**
   * Specifies that resizing occur at the west edge.
   */
  public static final DirectionConstant WEST = new DirectionConstant(DIRECTION_WEST, "w");

  private static final int BORDER_THICKNESS = 5;

  private static final String CSS_DEMO_RESIZE_EDGE = "demo-resize-edge";
  private int contentHeight;
  private int contentWidth;
  private Widget eastWidget;
  private Grid grid = new Grid(3, 3);
  private Widget northWidget;
  private ResizeDragController resizeDragController;
  private ScrollPanel scrollPanel;
  private Widget southWidget;
  private Widget westWidget;

  public ResizePanel(ResizeDragController resizeDragController, Widget widget) {
    this.resizeDragController = resizeDragController;
    scrollPanel = new ScrollPanel(widget);

    grid.setCellSpacing(0);
    grid.setCellPadding(0);
    add(grid);

    setupCell(0, 0, NORTH_WEST);
    northWidget = setupCell(0, 1, NORTH);
    setupCell(0, 2, NORTH_EAST);

    westWidget = setupCell(1, 0, WEST);
    grid.setWidget(1, 1, scrollPanel);
    eastWidget = setupCell(1, 2, EAST);

    setupCell(2, 0, SOUTH_WEST);
    southWidget = setupCell(2, 1, SOUTH);
    setupCell(2, 2, SOUTH_EAST);
  }

  public int getContentHeight() {
    return contentHeight;
  }

  public int getContentWidth() {
    return contentWidth;
  }

  public ScrollPanel getScrollPanel() {
    return scrollPanel;
  }

  public void moveBy(int right, int down) {
    AbsolutePanel parent = (AbsolutePanel) getParent();
    // TODO Use parent.getWidgetLeft/getWidgetTop after <a href="http://code.google.com/p/google-web-toolkit/issues/detail?id=757">GWT issue 757</a> is fixed
    Location location = new WidgetLocation(this, parent);
    int left = location.getLeft() + right;
    int top = location.getTop() + down;
    parent.setWidgetPosition(this, left, top);
  }

  public void setContentSize(int width, int height) {
    if (width != contentWidth) {
      contentWidth = width;
      northWidget.setPixelSize(contentWidth, BORDER_THICKNESS);
      southWidget.setPixelSize(contentWidth, BORDER_THICKNESS);
    }
    if (height != contentHeight) {
      contentHeight = height;
      westWidget.setPixelSize(BORDER_THICKNESS, contentHeight);
      eastWidget.setPixelSize(BORDER_THICKNESS, contentHeight);
    }
    scrollPanel.setPixelSize(contentWidth, contentHeight);
  }

  protected void onLoad() {
    super.onLoad();
    setContentSize(scrollPanel.getOffsetWidth(), scrollPanel.getOffsetHeight());
  }

  private Widget setupCell(int row, int col, DirectionConstant direction) {
    final FocusPanel widget = new FocusPanel();
    widget.setPixelSize(BORDER_THICKNESS, BORDER_THICKNESS);
    grid.setWidget(row, col, widget);
    resizeDragController.makeDraggable(widget, direction);
    grid.getCellFormatter().addStyleName(row, col, CSS_DEMO_RESIZE_EDGE + " demo-resize-" + direction.directionLetters);
    return widget;
  }
}
