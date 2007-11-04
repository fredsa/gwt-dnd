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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

/**
 * DragController used for drag-and-drop operations where a draggable widget or
 * drag proxy is temporarily picked up and dragged around the boundary panel.
 */
public class PickupDragController extends AbstractDragController {
  /**
   * @deprecated Use {@link #PRIVATE_CSS_MOVABLE_PANEL} instead
   */
  protected static final String CSS_MOVABLE_PANEL;

  /**
   * @deprecated Use {@link #PRIVATE_CSS_PROXY} instead
   */
  protected static final String CSS_PROXY;

  private static final String PRIVATE_CSS_MOVABLE_PANEL = "dragdrop-movable-panel";
  private static final String PRIVATE_CSS_PROXY = "dragdrop-proxy";

  static {
    CSS_MOVABLE_PANEL = PRIVATE_CSS_MOVABLE_PANEL;
    CSS_PROXY = PRIVATE_CSS_PROXY;
  }

  private Widget currentDraggable;
  private Widget draggableProxy;
  private boolean dragProxyEnabled = false;
  private int initialDraggableIndex;
  private String initialDraggableMargin;
  private Widget initialDraggableParent;
  private Location initialDraggableParentLocation;
  private SimplePanel movablePanel;

  /**
   * Create a new pickup-and-move style drag controller. Allows widgets or a
   * suitable proxy to be temporarily picked up and moved around the specified
   * boundary panel.
   * 
   * @param boundaryPanel the desired boundary panel or null if entire page is
   *            to be included
   * @param allowDroppingOnBoundaryPanel whether or not boundary panel should
   *            allow dropping
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
    movablePanel.addStyleName(PRIVATE_CSS_MOVABLE_PANEL);
    if (draggableProxy == null) {
      movablePanel.setPixelSize(draggable.getOffsetWidth(), draggable.getOffsetHeight());
    }
    getBoundaryPanel().add(movablePanel, location.getLeft(), location.getTop());

    final Widget innerWidget = draggableProxy != null ? draggableProxy : currentDraggable;
    movablePanel.setWidget(innerWidget);
  }

  /**
   * Determine whether or not this controller automatically creates a drag proxy
   * for each drag operation. Whether or not a drag proxy is used is ultimately
   * determined by the return value of {@link #maybeNewDraggableProxy(Widget)}
   * 
   * @return <code>true</code> if drag proxy behavior is enabled
   */
  public boolean getBehaviorDragProxy() {
    return dragProxyEnabled;
  }

  public Widget getMovableWidget() {
    return movablePanel;
  }

  /**
   * @deprecated Use {@link #getBehaviorDragProxy()} instead.
   */
  public final boolean isDragProxyEnabled() {
    throw new UnsupportedOperationException();
  }

  /**
   * Set whether or not this controller should automatically create a drag proxy
   * for each drag operation. Whether or not a drag proxy is used is ultimately
   * determined by the return value of {@link #maybeNewDraggableProxy(Widget)}.
   * 
   * @param dragProxyEnabled <code>true</code> to enable drag proxy behavior
   */
  public void setBehaviorDragProxy(boolean dragProxyEnabled) {
    this.dragProxyEnabled = dragProxyEnabled;
  }

  /**
   * @deprecated Use {@link #setBehaviorDragProxy(boolean)} instead.
   */
  public final void setDragProxyEnabled(boolean dragProxyEnabled) {
    throw new UnsupportedOperationException();
  }

  /**
   * Called from {@link #dragStart(Widget)} to optionally create a new drag proxy widget.
   * 
   * @param draggable the draggable which may be references in order to create the proxy
   * @return a draggable proxy widget or <code>null</code> if no drag proxy is desired
   */
  protected Widget maybeNewDraggableProxy(Widget draggable) {
    if (getBehaviorDragProxy()) {
      FocusPanel proxy;
      proxy = new FocusPanel();
      proxy.addStyleName(PRIVATE_CSS_PROXY);
      proxy.setPixelSize(currentDraggable.getOffsetWidth(), currentDraggable.getOffsetHeight());
      return proxy;
    } else {
      return null;
    }
  }

  /**
   * Restore the draggable to its original location
   * 
   * @see #saveDraggableLocationAndStyle(Widget)
   * @see #restoreDraggableStyle(Widget)
   * 
   * @param draggable the widget to be restored to its original location
   */
  protected void restoreDraggableLocation(Widget draggable) {
    // TODO simplify after enhancement for issue 1112 provides InsertPanel interface
    // http://code.google.com/p/google-web-toolkit/issues/detail?id=1112
    if (initialDraggableParent instanceof AbsolutePanel) {
      ((AbsolutePanel) initialDraggableParent).add(draggable, initialDraggableParentLocation.getLeft(),
          initialDraggableParentLocation.getTop());
    } else if (initialDraggableParent instanceof HorizontalPanel) {
      ((HorizontalPanel) initialDraggableParent).insert(draggable, initialDraggableIndex);
    } else if (initialDraggableParent instanceof VerticalPanel) {
      ((VerticalPanel) initialDraggableParent).insert(draggable, initialDraggableIndex);
    } else if (initialDraggableParent instanceof FlowPanel) {
      ((FlowPanel) initialDraggableParent).insert(draggable, initialDraggableIndex);
    } else if (initialDraggableParent instanceof SimplePanel) {
      ((SimplePanel) initialDraggableParent).setWidget(draggable);
    } else {
      throw new RuntimeException("Unable to handle initialDraggableParent " + GWT.getTypeName(initialDraggableParent));
    }
  }

  /**
   * Restore the draggable to its original style
   * 
   * @see #saveDraggableLocationAndStyle(Widget)
   * @see #restoreDraggableLocation(Widget)
   * 
   * @param draggable the widget to be restored to its original location
   */
  protected void restoreDraggableStyle(Widget draggable) {
    if (initialDraggableMargin != null && initialDraggableMargin.length() != 0) {
      DOM.setStyleAttribute(draggable.getElement(), "margin", initialDraggableMargin);
    }
  }

  /**
   * Save the draggable's current location in case we need to restore it later.
   * 
   * @see #restoreDraggableLocation(Widget)
   * 
   * @param draggable the widget for which the location must be saved
   */
  protected void saveDraggableLocationAndStyle(Widget draggable) {
    initialDraggableParent = draggable.getParent();

    // TODO simplify after enhancement for issue 1112 provides InsertPanel interface
    // http://code.google.com/p/google-web-toolkit/issues/detail?id=1112
    if (initialDraggableParent instanceof AbsolutePanel) {
      initialDraggableParentLocation = new WidgetLocation(draggable, initialDraggableParent);
    } else if (initialDraggableParent instanceof HorizontalPanel) {
      initialDraggableIndex = ((HorizontalPanel) initialDraggableParent).getWidgetIndex(draggable);
    } else if (initialDraggableParent instanceof VerticalPanel) {
      initialDraggableIndex = ((VerticalPanel) initialDraggableParent).getWidgetIndex(draggable);
    } else if (initialDraggableParent instanceof FlowPanel) {
      initialDraggableIndex = ((FlowPanel) initialDraggableParent).getWidgetIndex(draggable);
    } else if (initialDraggableParent instanceof SimplePanel) {
      // save nothing
    } else {
      throw new RuntimeException("Unable to handle 'initialDraggableParent instanceof " + GWT.getTypeName(initialDraggableParent)
          + "'; Please create your own DragController and override saveDraggableLocationAndStyle() and restoreDraggableLocation()");
    }
    initialDraggableMargin = DOM.getStyleAttribute(draggable.getElement(), "margin");
    if (initialDraggableMargin != null && initialDraggableMargin.length() != 0) {
      DOM.setStyleAttribute(draggable.getElement(), "margin", "0px");
    }
  }
}
