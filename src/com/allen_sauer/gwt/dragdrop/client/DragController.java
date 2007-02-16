package com.allen_sauer.gwt.dragdrop.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.BoundryDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

import java.util.HashMap;

/**
 * Controls basic drag-and-drop capabilities for a single drag-and-drop area.
 * Each area is bound by an AbsolutePanel, which is RootPanel.get() by default.
 * Each drop target utilizes a
 * {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DragController} to
 * address specific target requirements.
 */
public class DragController implements SourcesDragEvents {

  protected static final String STYLE_DRAGGING = "dragdrop-dragging";
  protected static final String STYLE_PROXY = "dragdrop-proxy";
  private static final String STYLE_DRAGGABLE = "dragdrop-draggable";

  private static HashMap widgetControllers = new HashMap();

  // TODO remove this method as it is barely used
  public static DragController getDragController(Widget widget) {
    return (DragController) widgetControllers.get(widget);
  }

  private BoundryDropController boundryDropController;
  private AbsolutePanel boundryPanel;
  private Widget currentDraggable;
  private Widget draggableProxy;
  private DragHandlerCollection dragHandlers;
  private boolean dragProxyEnabled = true;
  private DropControllerCollection dropControllerCollection = new DropControllerCollection();
  private Location initialDraggableBoundryPanelLocation;
  private Widget initialDraggableParent;
  private Location initialDraggableParentLocation;

  public DragController(AbsolutePanel boundryPanel) {
    this.boundryPanel = boundryPanel != null ? boundryPanel : RootPanel.get();
    boundryDropController = newBoundryDropController(boundryPanel);
    registerDropController(boundryDropController);
  }

  public void addDragHandler(DragHandler handler) {
    if (dragHandlers == null) {
      dragHandlers = new DragHandlerCollection();
    }
    dragHandlers.add(handler);
  }

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @param dropTarget widget on which draggable was dropped. 
   *        <code>null</code> if drag was canceled
   */
  public void dragEnd(Widget draggable, Widget dropTarget) {
    if (dragHandlers != null) {
      dragHandlers.fireDragEnd(draggable, dropTarget);
    }
    draggable.removeStyleName(STYLE_DRAGGING);
    currentDraggable = null;
    if (dragProxyEnabled) {
      draggableProxy.removeFromParent();
      draggableProxy = null;
    } else {
      // move draggable to original location
      if (initialDraggableParent instanceof AbsolutePanel) {
        //      Location parentLocation = new Location(initialDraggableParent, boundryPanel);
        ((AbsolutePanel) initialDraggableParent).add(draggable, initialDraggableParentLocation.getLeft(),
            initialDraggableParentLocation.getTop());
      } else {
        // TODO instead try to add to original parent panel in a different way
        boundryPanel.add(draggable, initialDraggableBoundryPanelLocation.getLeft(), initialDraggableBoundryPanelLocation.getTop());
      }
    }
  }

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   */
  public void dragStart(Widget draggable) {
    if (dragHandlers != null) {
      dragHandlers.fireDragStart(draggable);
    }
    draggable.addStyleName(STYLE_DRAGGING);
    currentDraggable = draggable;
    if (dragProxyEnabled) {
      draggableProxy = newDraggableProxy(draggable);
    }
    // Store initial draggable parent and coordinates in case we have to abort
    initialDraggableParent = draggable.getParent();
    initialDraggableParentLocation = new Location(draggable, initialDraggableParent);
    initialDraggableBoundryPanelLocation = new Location(draggable, boundryPanel);
    boundryPanel.add(getDraggableOrProxy(), initialDraggableBoundryPanelLocation.getLeft(),
        initialDraggableBoundryPanelLocation.getTop());
  }

  public BoundryDropController getBoundryDropController() {
    return this.boundryDropController;
  }

  public AbsolutePanel getBoundryPanel() {
    return boundryPanel;
  }

  /**
   * @return widget which will move as part of the drag operation.
   *         May be the actual draggable widget or an appropriate proxy widget.
   */
  public Widget getDraggableOrProxy() {
    return isDragProxyEnabled() ? draggableProxy : currentDraggable;
  }

  public boolean isDragProxyEnabled() {
    return dragProxyEnabled;
  }

  /**
   * Attaches a {@link MouseDragHandler} (which is a
   * {@link com.google.gwt.user.client.ui.MouseListener}) to the widget and
   * adds the {@link #STYLE_DRAGGABLE} style to the widget. Call this method for
   * each which that should be made draggable by this DragController.
   * 
   * @param widget the widget to be made draggable
   */
  public void makeDraggable(Widget widget) {
    if (widget instanceof SourcesMouseEvents) {
      ((SourcesMouseEvents) widget).addMouseListener(new MouseDragHandler(this));
    } else {
      throw new RuntimeException("widget must implement SourcesMouseEvents to be draggable");
    }
    widget.addStyleName(STYLE_DRAGGABLE);
    widgetControllers.put(widget, this);
  }

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  public void previewDragEnd(Widget draggable, Widget dropTarget) throws VetoDragException {
    if (dragHandlers != null) {
      dragHandlers.firePreviewDragEnd(draggable, dropTarget);
    }
  }

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  public void previewDragStart(Widget draggable) throws VetoDragException {
    if (dragHandlers != null) {
      dragHandlers.firePreviewDragStart(draggable);
    }
  }

  public void registerDropController(DropController dropController) {
    dropControllerCollection.add(dropController);
  }

  public void removeDragHandler(DragHandler handler) {
    if (dragHandlers != null) {
      dragHandlers.remove(handler);
    }
  }

  public void setDragProxyEnabled(boolean dragProxyEnabled) {
    this.dragProxyEnabled = dragProxyEnabled;
  }

  protected BoundryDropController newBoundryDropController(AbsolutePanel boundryPanel) {
    return new BoundryDropController(boundryPanel, true);
  }

  protected Widget newDraggableProxy(Widget draggable) {
    HTML proxy;
    proxy = new HTML("Drag Proxy (testing)");
    proxy.addStyleName(STYLE_PROXY);
    // TODO calculate actual CSS borders
    proxy.setPixelSize(currentDraggable.getOffsetWidth() - 0, currentDraggable.getOffsetHeight() - 0);
    return proxy;
  }

  DropController getIntersectDropController(Widget widget) {
    DropController dropController = dropControllerCollection.getIntersectDropController(widget, boundryPanel);
    return dropController != null ? dropController : boundryDropController;
  }
}
