/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dnd.client;

import java.util.ArrayList;

/**
 * Helper class for controllers that accept {@link com.allen_sauer.gwt.dnd.client.DragHandler
 * DragHandlers}. This subclass of ArrayList assumes that all items added to it will be of type
 * {@link com.allen_sauer.gwt.dnd.client.DragHandler}.
 */
@SuppressWarnings("serial")
public class DragHandlerCollection extends ArrayList<DragHandler> {

  /**
   * Fires a {@link DragHandler#onDragEnd(DragEndEvent)} on all handlers in the collection.
   * 
   * @param dragEndEvent the event
   */
  public void fireDragEnd(DragEndEvent dragEndEvent) {
    for (DragHandler handler : this) {
      handler.onDragEnd(dragEndEvent);
    }
  }

  /**
   * Fires a {@link DragHandler#onDragStart(DragStartEvent)} on all handlers in the collection.
   * 
   * @param dragStartEvent the event
   */
  public void fireDragStart(DragStartEvent dragStartEvent) {
    for (DragHandler handler : this) {
      handler.onDragStart(dragStartEvent);
    }
  }

  /**
   * Fires a {@link DragHandler#onPreviewDragEnd(DragEndEvent)} on all handlers in the collection.
   * 
   * @param dragEndEvent the event
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  public void firePreviewDragEnd(DragEndEvent dragEndEvent) throws VetoDragException {
    for (DragHandler handler : this) {
      handler.onPreviewDragEnd(dragEndEvent);
    }
  }

  /**
   * Fires a {@link DragHandler#onPreviewDragStart(DragStartEvent)} on all handlers in the
   * collection.
   * 
   * @param dragStartEvent the event
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  public void firePreviewDragStart(DragStartEvent dragStartEvent) throws VetoDragException {
    for (DragHandler handler : this) {
      handler.onPreviewDragStart(dragStartEvent);
    }
  }
}
