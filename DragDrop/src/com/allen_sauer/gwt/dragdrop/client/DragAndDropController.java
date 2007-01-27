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
 * a
 * {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DropController}
 * to address specific target requirements.
 */
public class DragAndDropController implements SourcesDragAndDropEvents {

  /**
   * Helper class to deal with draggable widget mouse events.
   */
  private class MouseHandler implements MouseListener {

    private DropController dropController;
    private boolean inDrag;
    private int initialMouseX;
    private int initialMouseY;
    private int initialDraggableX;
    private int initialDraggableY;

    public MouseHandler() {
      DragAndDropController.this.postioningBox.addStyleName("dragdrop-positioning-box dragdrop-hidden");
      DragAndDropController.this.boundryPanel.add(DragAndDropController.this.postioningBox);
    }

    private void move(Widget sender, int x, int y) {
      Widget draggable = DragAndDropController.this.draggableWidget;
      Location senderLocation = new Location(draggable,
          DragAndDropController.this.boundryPanel);

      int desiredLeft = (x - this.initialMouseX) + senderLocation.getLeft();
      int desiredTop = (y - this.initialMouseY) + senderLocation.getTop();

      DragAndDropController.this.boundryPanel.setWidgetPosition(draggable,
          desiredLeft, desiredTop);

      DropController newDropController = DropControllerCollection.singleton().getIntersectDropController(
          draggable, DragAndDropController.this.boundryPanel);
      if (this.dropController == newDropController) {
        if (this.dropController != null) {
          this.dropController.onPreDropMove(DragAndDropController.this, draggable);
        }
      } else {
        if (this.dropController != null) {
          this.dropController.onPreDropLeave(DragAndDropController.this, draggable);
        }
        this.dropController = newDropController;
        if (this.dropController != null) {
          this.dropController.onPreDropEnter(DragAndDropController.this, draggable);
          this.dropController.onPreDropMove(DragAndDropController.this, draggable);
        }
      }
    }

    public void onMouseDown(Widget sender, int x, int y) {
      this.initialMouseX = x;
      this.initialMouseY = y;

      Widget draggable = DragAndDropController.this.draggableWidget;
      if (DragAndDropController.this.dragAndDropListeners != null) {
        if (!DragAndDropController.this.dragAndDropListeners.firePreDragStart(draggable)) {
          return;
        }
        DragAndDropController.this.dragAndDropListeners.fireDragStart(draggable);
      }
      draggable.addStyleName("dragdrop-dragging");

      Location draggableLocation = new Location(draggable,
          DragAndDropController.this.boundryPanel);
      DragAndDropController.this.boundryPanel.add(draggable,
          draggableLocation.getLeft(), draggableLocation.getTop());

      DOM.setCapture(sender.getElement());

      // TODO calculate actual borders of positioningBox
      DragAndDropController.this.postioningBox.setPixelSize(
          sender.getOffsetWidth() - 2, sender.getOffsetHeight() - 2);

      this.inDrag = true;
      move(sender, x, y);
    }

    public void onMouseEnter(Widget sender) {
    }

    public void onMouseLeave(Widget sender) {
    }

    public void onMouseMove(Widget sender, int x, int y) {
      // UIUtil.debug(x + ", " + y);
      if (this.inDrag) {
        move(sender, x, y);
      }
    }

    public void onMouseUp(Widget sender, int x, int y) {
      if (this.inDrag) {
        move(sender, x, y);
        if (this.dropController != null) {
          this.dropController.onPreDropLeave(DragAndDropController.this, sender);
        }
        this.dropController = DropControllerCollection.singleton().getIntersectDropController(
            sender, DragAndDropController.this.boundryPanel);
        if (this.dropController != null) {
          this.dropController.onDrop(DragAndDropController.this, sender);
          if (DragAndDropController.this.dragAndDropListeners != null) {
            DragAndDropController.this.dragAndDropListeners.fireDrop(
                DragAndDropController.this.draggableWidget,
                this.dropController.getDropTargetPanel());
          }
          this.dropController = null;
        } else {
          DragAndDropController.this.boundryPanel.add(
              DragAndDropController.this.draggableWidget,
              this.initialDraggableX, this.initialDraggableY);
        }
      }
      DOM.releaseCapture(sender.getElement());
      this.inDrag = false;
      DragAndDropController.this.postioningBox.addStyleName("dragdrop-hidden");
      DragAndDropController.this.draggableWidget.removeStyleName("dragdrop-dragging");
    }
  }

  private AbsolutePanel boundryPanel;
  private Widget draggableWidget;
  private DragAndDropListenerCollection dragAndDropListeners;
  private SimplePanel postioningBox = new SimplePanel();

  public DragAndDropController(Widget draggableWidget,
      AbsolutePanel boundryPanel) {
    this.draggableWidget = draggableWidget;
    this.boundryPanel = boundryPanel != null ? boundryPanel : RootPanel.get();
    if (draggableWidget instanceof SourcesMouseEvents) {
      ((SourcesMouseEvents) draggableWidget).addMouseListener(new MouseHandler());
    } else {
      throw new RuntimeException(
          "draggableWidget must implement SourcesMouseEvents");
    }
    draggableWidget.addStyleName("dragdrop-draggable");
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

  public SimplePanel getPostioningBox() {
    return this.postioningBox;
  }

  public void removeDragAndDropListener(DragAndDropListener listener) {
    if (this.dragAndDropListeners != null) {
      this.dragAndDropListeners.remove(listener);
    }
  }

}
