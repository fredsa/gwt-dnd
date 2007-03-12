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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

/**
 * DragController used for drag-and-drop operations where a draggable widget
 * or drag proxy is temporarily picked up and dragged around the boundary panel.
 */
public class PickupDragController extends AbstractDragController {

  protected static final String STYLE_PROXY = "dragdrop-proxy";

  private Widget currentDraggable;
  private Widget draggableProxy;
  private boolean dragProxyEnabled = false;
  private Location initialDraggableBoundaryPanelLocation;
  private Widget initialDraggableParent;
  private Location initialDraggableParentLocation;

  private int initialDraggableIndex;

  /**
   * Create a new pickup-and-move style drag controller. Allows widgets or a suitable proxy
   * to be temporarily picked up and moved around the specified boundary panel.
   * 
   * @param boundaryPanel the desired boundary panel or null if entire page is to be included
   */
  public PickupDragController(AbsolutePanel boundaryPanel) {
    super(boundaryPanel);
  }

  public void dragEnd(Widget draggable, Widget dropTarget) {
    super.dragEnd(draggable, dropTarget);
    currentDraggable = null;
    if (draggableProxy != null) {
      draggableProxy.removeFromParent();
      draggableProxy = null;
    } else {
      if (dropTarget == null) {
        // move draggable to original location

        // TODO simplify after enhancement for issue 616
        // http://code.google.com/p/google-web-toolkit/issues/detail?id=616
        if (initialDraggableParent instanceof AbsolutePanel) {
          //      Location parentLocation = new Location(initialDraggableParent, boundaryPanel);
          ((AbsolutePanel) initialDraggableParent).add(draggable, initialDraggableParentLocation.getLeft(),
              initialDraggableParentLocation.getTop());
        } else if (initialDraggableParent instanceof DeckPanel) {
          ((DeckPanel) initialDraggableParent).insert(draggable, initialDraggableIndex);
        } else if (initialDraggableParent instanceof HorizontalPanel) {
          ((HorizontalPanel) initialDraggableParent).insert(draggable, initialDraggableIndex);
        } else if (initialDraggableParent instanceof VerticalPanel) {
          ((VerticalPanel) initialDraggableParent).insert(draggable, initialDraggableIndex);
        } else if (initialDraggableParent instanceof IndexedFlowPanel) {
          ((IndexedFlowPanel) initialDraggableParent).insert(draggable, initialDraggableIndex);
        } else {
          throw new RuntimeException("Unable to handle initialDraggableParent " + GWT.getTypeName(initialDraggableParent));
        }
      }
    }
  }

  public void dragStart(Widget draggable) {
    super.dragStart(draggable);
    currentDraggable = draggable;
    draggableProxy = maybeNewDraggableProxy(draggable);
    // Store initial draggable parent and coordinates in case we have to abort
    initialDraggableParent = draggable.getParent();

    // TODO simplify after enhancement for issue 616
    // http://code.google.com/p/google-web-toolkit/issues/detail?id=616
    if (initialDraggableParent instanceof AbsolutePanel) {
      initialDraggableParentLocation = new Location(draggable, initialDraggableParent);
    } else if (initialDraggableParent instanceof DeckPanel) {
      initialDraggableIndex = ((DeckPanel) initialDraggableParent).getWidgetIndex(draggable);
    } else if (initialDraggableParent instanceof HorizontalPanel) {
      initialDraggableIndex = ((HorizontalPanel) initialDraggableParent).getWidgetIndex(draggable);
    } else if (initialDraggableParent instanceof VerticalPanel) {
      initialDraggableIndex = ((VerticalPanel) initialDraggableParent).getWidgetIndex(draggable);
    } else if (initialDraggableParent instanceof IndexedFlowPanel) {
      initialDraggableIndex = ((IndexedFlowPanel) initialDraggableParent).getWidgetIndex(draggable);
    } else {
      throw new RuntimeException("Unable to handle initialDraggableParent " + GWT.getTypeName(initialDraggableParent));
    }
    initialDraggableBoundaryPanelLocation = new Location(draggable, getBoundaryPanel());
    if (getMovableWidget().getParent() == getBoundaryPanel()) {
      // causes widget to be place on top of all other widgets in the boundary panel
      getMovableWidget().removeFromParent();
    }
    getBoundaryPanel().add(getMovableWidget(), initialDraggableBoundaryPanelLocation.getLeft(),
        initialDraggableBoundaryPanelLocation.getTop());
  }

  public Widget getMovableWidget() {
    return draggableProxy != null ? draggableProxy : currentDraggable;
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
