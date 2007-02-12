package com.allen_sauer.gwt.dragdrop.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;

/**
 * Controls basic drag-and-drop capabilities for a single drag-and-drop area.
 * Each area is bound by an AbsolutePanel, which is RootPanel.get() by default.
 * Each drop target utilizes a
 * {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DropController} to
 * address specific target requirements.
 */
public class DragController implements SourcesDragAndDropEvents {

  private static final String STYLE_DRAGGABLE = "dragdrop-draggable";
  private static final String STYLE_DRAGGING = "dragdrop-dragging";
  private static final String STYLE_PROXY = "dragdrop-proxy";

  private static HashMap widgetControllers = new HashMap();

  // TODO remove this method as it is barely used
  public static DragController getDragController(Widget widget) {
    return (DragController) widgetControllers.get(widget);
  }

  private AbsolutePanel boundryPanel;

  private Widget currentDraggable;

  private DragAndDropListenerCollection dragAndDropListeners;

  private transient Widget draggableProxy;
  private boolean dragProxyEnabled = false;

  public DragController(AbsolutePanel boundryPanel) {
    this.boundryPanel = boundryPanel != null ? boundryPanel : RootPanel.get();
  }

  public void addDragAndDropListener(DragAndDropListener listener) {
    if (dragAndDropListeners == null) {
      dragAndDropListeners = new DragAndDropListenerCollection();
    }
    dragAndDropListeners.add(listener);
  }

  public boolean drag(Widget draggable) {
    if (dragAndDropListeners != null) {
      dragAndDropListeners.fireDragStart(draggable);
    }
    draggable.addStyleName(STYLE_DRAGGING);
    currentDraggable = draggable;
    createDraggableProxy(draggable);
    return true;
  }

  public void drop(Widget draggable, Widget dropTarget) {
    if (dragAndDropListeners != null) {
      dragAndDropListeners.fireDrop(draggable, dropTarget);
    }
    draggable.removeStyleName(STYLE_DRAGGING);
    currentDraggable = null;
    if (dragProxyEnabled) {
      draggableProxy.removeFromParent();
      draggableProxy = null;
    }
  }

  public void dropCanceled(Widget draggable, Widget dropTarget) {
    if (dragAndDropListeners != null) {
      dragAndDropListeners.fireDropCanceled(draggable);
    }
    draggable.removeStyleName(STYLE_DRAGGING);
    currentDraggable = null;
    if (dragProxyEnabled) {
      draggableProxy.removeFromParent();
      draggableProxy = null;
    }
  }

  public AbsolutePanel getBoundryPanel() {
    return boundryPanel;
  }

  public Widget getDraggableProxy() {
    return dragProxyEnabled ? draggableProxy : currentDraggable;
  }

  public boolean isDragAllowed(Widget draggable) {
    if (dragAndDropListeners != null) {
      if (!dragAndDropListeners.fireIsDragAllowed(draggable)) {
        return false;
      }
    }
    return true;
  }

  public boolean isDragProxyEnabled() {
    return this.dragProxyEnabled;
  }

  public boolean isDropAllowed(Widget draggable, Widget dropTarget) {
    if (dragAndDropListeners != null) {
      if (!dragAndDropListeners.fireIsDropAllowed(draggable, dropTarget)) {
        return false;
      }
    }
    return true;
  }

  public void makeDraggable(Widget widget) {
    if (widget instanceof SourcesMouseEvents) {
      ((SourcesMouseEvents) widget).addMouseListener(new MouseDragHandler(this));
    } else {
      throw new RuntimeException("widget must implement SourcesMouseEvents to be draggable");
    }
    widget.addStyleName(STYLE_DRAGGABLE);
    widgetControllers.put(widget, this);
  }

  public void removeDragAndDropListener(DragAndDropListener listener) {
    if (dragAndDropListeners != null) {
      dragAndDropListeners.remove(listener);
    }
  }

  public void setDragProxyEnabled(boolean dragProxyEnabled) {
    this.dragProxyEnabled = dragProxyEnabled;
  }

  private void createDraggableProxy(Widget draggable) {
    draggableProxy = new HTML("Drag Proxy (testing)");
    draggableProxy.addStyleName(STYLE_PROXY);
    // TODO calculate actual CSS borders
    draggableProxy.setPixelSize(currentDraggable.getOffsetWidth() - 0, currentDraggable.getOffsetHeight() - 0);
  }
}
