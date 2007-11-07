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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

import java.util.HashMap;
import java.util.Iterator;

/**
 * DragController used for drag-and-drop operations where a draggable widget or
 * drag proxy is temporarily picked up and dragged around the boundary panel.
 */
public class PickupDragController extends AbstractDragController {
  private static class SavedWidgetInfo {
    int initialDraggableIndex;
    String initialDraggableMargin;
    Widget initialDraggableParent;
    Location initialDraggableParentLocation;
  }

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
  private Widget movablePanel;

  private HashMap savedWidgetInfoMap;

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
          restoreSelectedWidgetsLocation();
        }
      }
      restoreSelectedWidgetsStyle();
      movablePanel.removeFromParent();
      movablePanel = null;
    }
  }

  public Widget dragStart(Widget draggable) {
    super.dragStart(draggable);
    currentDraggable = draggable;

    saveSelectedWidgetsLocationAndStyle();
    if (getBehaviorDragProxy()) {
      movablePanel = newDragProxy(draggable);
    } else {
      AbsolutePanel container = new AbsolutePanel();
      DOM.setStyleAttribute(container.getElement(), "overflow", "visible");

      // TODO better way to deal with constrained to boundary panel behavior
      container.setPixelSize(draggable.getOffsetWidth(), draggable.getOffsetHeight());

      for (Iterator iterator = getSelectedWidgets().iterator(); iterator.hasNext();) {
        Widget widget = (Widget) iterator.next();
        if (widget != draggable) {
          WidgetArea widgetArea = new WidgetArea(widget, draggable);
          container.add(widget, widgetArea.getLeft(), widgetArea.getTop());
        }
      }
      container.add(draggable, 0, 0);
      movablePanel = container;
    }
    movablePanel.addStyleName(PRIVATE_CSS_MOVABLE_PANEL);

    // add movablePanel to boundary panel
    WidgetLocation currentDraggableLocation = new WidgetLocation(currentDraggable, getBoundaryPanel());
    getBoundaryPanel().add(movablePanel, currentDraggableLocation.getLeft(), currentDraggableLocation.getTop());

    return movablePanel;
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

  /**
   * @deprecated Use {@link #getBehaviorDragProxy()} instead.
   */
  public boolean isDragProxyEnabled() {
    return getBehaviorDragProxy();
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
  public void setDragProxyEnabled(boolean dragProxyEnabled) {
    setBehaviorDragProxy(dragProxyEnabled);
  }

  /**
   * @deprecated Use {@link #newDragProxy(Widget)} and {@link #setBehaviorDragProxy(boolean)} instead.
   */
  protected final Widget maybeNewDraggableProxy(Widget draggable) {
    throw new UnsupportedOperationException();
  }

  /**
   * Called by {@link PickupDragController#dragStart(Widget)} when {@link #getBehaviorDragProxy()}
   * returns <code>true</code> to allow subclasses to provide their own drag proxies.
   * 
   * @param draggable the draggable widget
   * @return a new drag proxy
   */
  protected Widget newDragProxy(Widget draggable) {
    AbsolutePanel container = new AbsolutePanel();
    DOM.setStyleAttribute(container.getElement(), "overflow", "visible");

    for (Iterator iterator = getSelectedWidgets().iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();
      WidgetArea widgetArea = new WidgetArea(widget, draggable);
      Widget proxy = new SimplePanel();
      proxy.setPixelSize(widget.getOffsetWidth(), widget.getOffsetHeight());
      proxy.addStyleName(PRIVATE_CSS_PROXY);
      container.add(proxy, widgetArea.getLeft(), widgetArea.getTop());
    }

    return container;
  }

  /**
   * Restore the selected widgets to their original location.
   * 
   * @see #saveSelectedWidgetsLocationAndStyle()
   * @see #restoreSelectedWidgetsStyle()
   */
  protected void restoreSelectedWidgetsLocation() {
    for (Iterator iterator = getSelectedWidgets().iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();
      SavedWidgetInfo info = (SavedWidgetInfo) savedWidgetInfoMap.get(widget);

      // TODO simplify after enhancement for issue 1112 provides InsertPanel interface
      // http://code.google.com/p/google-web-toolkit/issues/detail?id=1112
      if (info.initialDraggableParent instanceof AbsolutePanel) {
        ((AbsolutePanel) info.initialDraggableParent).add(widget, info.initialDraggableParentLocation.getLeft(),
            info.initialDraggableParentLocation.getTop());
      } else if (info.initialDraggableParent instanceof HorizontalPanel) {
        ((HorizontalPanel) info.initialDraggableParent).insert(widget, info.initialDraggableIndex);
      } else if (info.initialDraggableParent instanceof VerticalPanel) {
        ((VerticalPanel) info.initialDraggableParent).insert(widget, info.initialDraggableIndex);
      } else if (info.initialDraggableParent instanceof FlowPanel) {
        ((FlowPanel) info.initialDraggableParent).insert(widget, info.initialDraggableIndex);
      } else if (info.initialDraggableParent instanceof SimplePanel) {
        ((SimplePanel) info.initialDraggableParent).setWidget(widget);
      } else {
        throw new RuntimeException("Unable to handle initialDraggableParent " + GWT.getTypeName(info.initialDraggableParent));
      }
    }
  }

  /**
   * Restore the selected widgets with their original style.
   * 
   * @see #saveSelectedWidgetsLocationAndStyle()
   * @see #restoreSelectedWidgetsLocation()
   */
  protected void restoreSelectedWidgetsStyle() {
    for (Iterator iterator = getSelectedWidgets().iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();
      SavedWidgetInfo info = (SavedWidgetInfo) savedWidgetInfoMap.get(widget);

      if (info.initialDraggableMargin != null && info.initialDraggableMargin.length() != 0) {
        DOM.setStyleAttribute(widget.getElement(), "margin", info.initialDraggableMargin);
      }
    }
  }

  /**
   * Save the selected widgets' current location in case they much
   * be restored due to a canceled drop.
   * 
   * @see #restoreSelectedWidgetsLocation()
   */
  protected void saveSelectedWidgetsLocationAndStyle() {
    savedWidgetInfoMap = new HashMap();
    for (Iterator iterator = getSelectedWidgets().iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();

      SavedWidgetInfo info = new SavedWidgetInfo();
      info.initialDraggableParent = widget.getParent();

      // TODO simplify after enhancement for issue 1112 provides InsertPanel interface
      // http://code.google.com/p/google-web-toolkit/issues/detail?id=1112
      if (info.initialDraggableParent instanceof AbsolutePanel) {
        info.initialDraggableParentLocation = new WidgetLocation(widget, info.initialDraggableParent);
      } else if (info.initialDraggableParent instanceof HorizontalPanel) {
        info.initialDraggableIndex = ((HorizontalPanel) info.initialDraggableParent).getWidgetIndex(widget);
      } else if (info.initialDraggableParent instanceof VerticalPanel) {
        info.initialDraggableIndex = ((VerticalPanel) info.initialDraggableParent).getWidgetIndex(widget);
      } else if (info.initialDraggableParent instanceof FlowPanel) {
        info.initialDraggableIndex = ((FlowPanel) info.initialDraggableParent).getWidgetIndex(widget);
      } else if (info.initialDraggableParent instanceof SimplePanel) {
        // save nothing
      } else {
        throw new RuntimeException(
            "Unable to handle 'initialDraggableParent instanceof "
                + GWT.getTypeName(info.initialDraggableParent)
                + "'; Please create your own DragController and override saveDraggableLocationAndStyle() and restoreDraggableLocation()");
      }

      info.initialDraggableMargin = DOM.getStyleAttribute(widget.getElement(), "margin");
      if (info.initialDraggableMargin != null && info.initialDraggableMargin.length() != 0) {
        DOM.setStyleAttribute(widget.getElement(), "margin", "0px");
      }
      savedWidgetInfoMap.put(widget, info);
    }
  }
}
