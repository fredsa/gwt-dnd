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
package com.allen_sauer.gwt.dragdrop.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.UIUtil;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

/**
 * DragController used for drag-and-drop operations where a draggable widget
 * or drag proxy is temporarily picked up and dragged around the boundary panel.
 */
public class PickupDragController extends AbstractDragController {

  protected static final String STYLE_PROXY = "dragdrop-proxy";
  protected static final String STYLE_MOVABLE_PANEL = "dragdrop-movable-panel";

  private Widget currentDraggable;
  private Widget draggableProxy;
  private boolean dragProxyEnabled = false;
  private SimplePanel movablePanel;

  /**
   * Create a new pickup-and-move style drag controller. Allows widgets or a suitable proxy
   * to be temporarily picked up and moved around the specified boundary panel.
   * 
   * @param boundaryPanel the desired boundary panel or null if entire page is to be included
   * @param allowDroppingOnBoundaryPanel whether or not boundary panel should allow dropping
   */
  public PickupDragController(AbsolutePanel boundaryPanel, boolean allowDroppingOnBoundaryPanel) {
    super(boundaryPanel, allowDroppingOnBoundaryPanel);
  }

  public void dragEnd(Widget draggable, Widget dropTarget) {
    super.dragEnd(draggable, dropTarget);
    // in case MouseDragHandler calls us twice due to DropController exception
    if (currentDraggable != null) {
      currentDraggable = null;
      if (draggableProxy != null) {
        draggableProxy.removeFromParent();
        draggableProxy = null;
      } else {
        if (dropTarget == null) {
          restoreDraggableLocation(draggable);
        }
      }
      restoreDraggableStyle(draggable);
      movablePanel.removeFromParent();
      movablePanel = null;
    }
  }

  public void dragStart(Widget draggable) {
    super.dragStart(draggable);
    currentDraggable = draggable;
    draggableProxy = maybeNewDraggableProxy(draggable);
    saveDraggableLocationAndStyle(draggable);
    Location location = new WidgetLocation(draggable, getBoundaryPanel());
    movablePanel = new SimplePanel();
    movablePanel.addStyleName(STYLE_MOVABLE_PANEL);
    getBoundaryPanel().add(movablePanel, location.getLeft(), location.getTop());

    final Widget innerWidget = draggableProxy != null ? draggableProxy : currentDraggable;
    UIUtil.resetStylePositionStatic(innerWidget.getElement());
    movablePanel.setWidget(innerWidget);
  }

  public Widget getMovableWidget() {
    return movablePanel;
  }

  public boolean isDragProxyEnabled() {
    return dragProxyEnabled;
  }

  public void setDragProxyEnabled(boolean dragProxyEnabled) {
    this.dragProxyEnabled = dragProxyEnabled;
  }

  protected Widget maybeNewDraggableProxy(Widget draggable) {
    if (isDragProxyEnabled()) {
      HTML proxy;
      proxy = new HTML("this is a Drag Proxy");
      proxy.addStyleName(STYLE_PROXY);
      proxy.setPixelSize(currentDraggable.getOffsetWidth(), currentDraggable.getOffsetHeight());
      return proxy;
    } else {
      return null;
    }
  }
}
