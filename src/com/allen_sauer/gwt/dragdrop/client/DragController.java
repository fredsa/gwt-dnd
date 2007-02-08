package com.allen_sauer.gwt.dragdrop.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
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

  private static HashMap widgetControllers = new HashMap();

  public static DragController getDragController(Widget widget) {
    return (DragController) widgetControllers.get(widget);
  }

  private AbsolutePanel boundryPanel;
  private DragAndDropListenerCollection dragAndDropListeners;

  public DragController(AbsolutePanel boundryPanel) {
    this.boundryPanel = boundryPanel != null ? boundryPanel : RootPanel.get();
  }

  public void addDragAndDropListener(DragAndDropListener listener) {
    if (this.dragAndDropListeners == null) {
      this.dragAndDropListeners = new DragAndDropListenerCollection();
    }
    this.dragAndDropListeners.add(listener);
  }

  public AbsolutePanel getBoundryPanel() {
    return boundryPanel;
  }

  public DragAndDropListenerCollection getDragAndDropListeners() {
    if (this.dragAndDropListeners == null) {
      this.dragAndDropListeners = new DragAndDropListenerCollection();
    }
    return dragAndDropListeners;
  }

  public void makeDraggable(Widget widget) {
    if (widget instanceof SourcesMouseEvents) {
      ((SourcesMouseEvents) widget).addMouseListener(new MouseHandler(widget, this));
    } else {
      throw new RuntimeException(
          "draggableWidget must implement SourcesMouseEvents");
    }
    widget.addStyleName("dragdrop-draggable");
    widgetControllers.put(widget, this);
  }

  public void removeDragAndDropListener(DragAndDropListener listener) {
    if (this.dragAndDropListeners != null) {
      this.dragAndDropListeners.remove(listener);
    }
  }
}
