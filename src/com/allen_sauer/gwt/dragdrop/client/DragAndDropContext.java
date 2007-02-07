/*
 * Copyright 2006 Fred Sauer
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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

/**
 * Control basic drag-and-drop capabilities, although each drop target utilizes
 * a {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DropController} to
 * address specific target requirements.
 */
public class DragAndDropContext implements SourcesDragAndDropEvents {

  /**
   * Helper class to deal with draggable widget mouse events.
   */
  private class MouseHandler implements MouseListener {

    private DropController dropController;
    private boolean inDrag;
    private Location initialDraggableLocation;
    private Widget initialDraggableParent;
    private int initialMouseX;
    private int initialMouseY;

    public void onMouseDown(Widget sender, int x, int y) {
      this.initialMouseX = x;
      this.initialMouseY = y;

      Widget draggable = DragAndDropContext.this.draggableWidget;
      if (DragAndDropContext.this.dragAndDropListeners != null) {
        if (!DragAndDropContext.this.dragAndDropListeners.firePreventDragStart(draggable)) {
          return;
        }
        DragAndDropContext.this.dragAndDropListeners.fireDragStart(draggable);
      }
      draggable.addStyleName("dragdrop-dragging");

      // Store initial draggable parent and coordinates in case we have to abort
      this.initialDraggableParent = draggable.getParent();
      this.initialDraggableLocation = new Location(draggable, DragAndDropContext.this.boundryPanel);
      DragAndDropContext.this.boundryPanel.add(draggable, this.initialDraggableLocation.getLeft(),
          this.initialDraggableLocation.getTop());

      // TODO calculate actual borders of positioningBox
      DragAndDropContext.this.postioningBox.setPixelSize(sender.getOffsetWidth() - 2, sender.getOffsetHeight() - 2);

      DOM.setCapture(sender.getElement());
      this.inDrag = true;
      try {
        move(sender, x, y);
      } catch (RuntimeException ex) {
        this.inDrag = false;
        DOM.releaseCapture(sender.getElement());
        throw ex;
      }
    }

    public void onMouseEnter(Widget sender) {
    }

    public void onMouseLeave(Widget sender) {
    }

    public void onMouseMove(Widget sender, int x, int y) {
      if (!this.inDrag) {
        return;
      }
      try {
        move(sender, x, y);
      } catch (RuntimeException ex) {
        cancelDrag();
        throw ex;
      }
    }

    public void onMouseUp(Widget sender, int x, int y) {
      if (!this.inDrag) {
        return;
      }
      try {
        DOM.releaseCapture(sender.getElement());

        move(sender, x, y);
        this.inDrag = false;
        DragAndDropContext.this.draggableWidget.removeStyleName("dragdrop-dragging");

        // Determine the interested controller at our present location
        DropController newDropController = DropControllerCollection.singleton().getIntersectDropController(sender,
            DragAndDropContext.this.boundryPanel);

        // Is the controller at this location different than the last one?
        if (this.dropController != newDropController) {
          if (this.dropController != null) {
            this.dropController.onLeave(DragAndDropContext.this);
          }
          this.dropController = newDropController;
        }

        // Is there a controller willing to handle our request?
        if (this.dropController == null) {
          cancelDrag();
          return;
        }

        // Does anyone wish to veto this request?
        if (DragAndDropContext.this.dragAndDropListeners != null) {
          if (DragAndDropContext.this.dragAndDropListeners.firePreventDrop(DragAndDropContext.this.draggableWidget,
              this.dropController.getDropTargetPanel())) {
            cancelDrag();
            return;
          }
        }

        // Does the controller allow the drop?
        if (!this.dropController.onDrop(DragAndDropContext.this)) {
          cancelDrag();
          return;
        }

        // Notify listeners that drop occurred
        if (DragAndDropContext.this.dragAndDropListeners != null) {
          DragAndDropContext.this.dragAndDropListeners.fireDrop(DragAndDropContext.this.draggableWidget,
              this.dropController.getDropTargetPanel());
        }

      } catch (RuntimeException ex) {
        // cleanup in case anything goes wrong
        cancelDrag();
        throw ex;
      } finally {
        this.dropController = null;
      }
    }

    private void cancelDrag() {
      // Do this first so it always happens
      DOM.releaseCapture(DragAndDropContext.this.draggableWidget.getElement());

      if (this.dropController != null) {
        this.dropController.onLeave(DragAndDropContext.this);
      }

      this.inDrag = false;
      if (this.initialDraggableParent instanceof AbsolutePanel) {
        Location parentLocation = new Location(this.initialDraggableParent, DragAndDropContext.this.boundryPanel);
        ((AbsolutePanel) this.initialDraggableParent).add(DragAndDropContext.this.draggableWidget,
            this.initialDraggableLocation.getLeft() - parentLocation.getLeft(), this.initialDraggableLocation.getTop()
                - parentLocation.getTop());
      } else {
        DragAndDropContext.this.boundryPanel.add(DragAndDropContext.this.draggableWidget,
            this.initialDraggableLocation.getLeft(), this.initialDraggableLocation.getTop());
      }
      this.dropController = null;
    }

    private void move(Widget sender, int x, int y) {
      Widget draggable = DragAndDropContext.this.draggableWidget;
      Location senderLocation = new Location(draggable, DragAndDropContext.this.boundryPanel);

      int desiredLeft = (x - this.initialMouseX) + senderLocation.getLeft();
      int desiredTop = (y - this.initialMouseY) + senderLocation.getTop();

      DragAndDropContext.this.boundryPanel.setWidgetPosition(draggable, desiredLeft, desiredTop);

      DropController newDropController = DropControllerCollection.singleton().getIntersectDropController(draggable,
          DragAndDropContext.this.boundryPanel);
      if (this.dropController == newDropController) {
        if (this.dropController != null) {
          this.dropController.onMove(DragAndDropContext.this);
        }
      } else {
        if (this.dropController != null) {
          this.dropController.onLeave(DragAndDropContext.this);
        }
        this.dropController = newDropController;
        if (this.dropController != null) {
          this.dropController.onEnter(DragAndDropContext.this);
          this.dropController.onMove(DragAndDropContext.this);
        }
      }
    }
  }

  private AbsolutePanel boundryPanel;
  private DragAndDropListenerCollection dragAndDropListeners;
  private Widget draggableWidget;
  private SimplePanel postioningBox = new SimplePanel();

  public DragAndDropContext(Widget draggableWidget, AbsolutePanel boundryPanel) {
    this.draggableWidget = draggableWidget;
    this.boundryPanel = boundryPanel != null ? boundryPanel : RootPanel.get();
    if (draggableWidget instanceof SourcesMouseEvents) {
      ((SourcesMouseEvents) draggableWidget).addMouseListener(new MouseHandler());
    } else {
      throw new RuntimeException("draggableWidget must implement SourcesMouseEvents");
    }
    draggableWidget.addStyleName("dragdrop-draggable");
    this.postioningBox.addStyleName("dragdrop-positioning-box");
  }

  public void addDragAndDropListener(DragAndDropListener listener) {
    if (this.dragAndDropListeners == null) {
      this.dragAndDropListeners = new DragAndDropListenerCollection();
    }
    this.dragAndDropListeners.add(listener);
  }

  public AbsolutePanel getBoundryPanel() {
    return this.boundryPanel;
  }

  public Widget getDraggable() {
    return this.draggableWidget;
  }

  public SimplePanel getPostioningBox() {
    return this.postioningBox;
  }

  public void removeDragAndDropListener(DragAndDropListener listener) {
    if (this.dragAndDropListeners != null) {
      this.dragAndDropListeners.remove(listener);
    }
  }

}
