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

import com.google.gwt.user.client.ui.Widget;

/**
 * Contains the context of the current drag operation. This may involve the
 * dragging of one or more widgets.
 */
public class DragContext {

  private DragController dragController;
  private Widget draggableWidget;

  public DragContext(Widget draggableWidget, DragController dragController) {
    this.dragController = dragController;
    this.draggableWidget = draggableWidget;
    //    if (draggableWidget instanceof SourcesMouseEvents) {
    //      ((SourcesMouseEvents) draggableWidget).addMouseListener(new MouseHandler(
    //          this));
    //    } else {
    //      throw new RuntimeException(
    //          "draggableWidget must implement SourcesMouseEvents");
    //    }
    //    draggableWidget.addStyleName("dragdrop-draggable");
  }

  public DragController getDragController() {
    return dragController;
  }

  public Widget getDraggable() {
    return this.draggableWidget;
  }
}
