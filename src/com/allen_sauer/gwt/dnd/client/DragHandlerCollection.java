/*
 * Copyright 2008 Fred Sauer
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
package com.allen_sauer.gwt.dnd.client;

import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Helper class for controllers that accept
 * {@link com.allen_sauer.gwt.dnd.client.DragHandler DragHandlers}. This
 * subclass of ArrayList assumes that all items added to it will be of type
 * {@link com.allen_sauer.gwt.dnd.client.DragHandler}.
 */
public class DragHandlerCollection extends ArrayList<DragHandler> {

  /**
   * @deprecated Use {@link #fireDragEnd(DragEndEvent)} instead.
   */
  public final void fireDragEnd(DragContext context) {
  }

  /**
   * Fires a {@link DragHandler#onDragEnd(DragEndEvent)} on all handlers in the
   * collection.
   * 
   * @param dragEndEvent the event
   */
  public void fireDragEnd(DragEndEvent dragEndEvent) {
    for (Iterator<DragHandler> it = iterator(); it.hasNext();) {
      DragHandler handler = it.next();
      handler.onDragEnd(dragEndEvent);
    }
  }

  /**
   * @deprecated Use {@link #fireDragStart(DragStartEvent)} instead.
   */
  public final void fireDragStart(DragContext context) {
  }

  /**
   * Fires a {@link DragHandler#onDragStart(DragStartEvent)} on all handlers in
   * the collection.
   * 
   * @param dragStartEvent the event
   */
  public void fireDragStart(DragStartEvent dragStartEvent) {
    for (Iterator<DragHandler> it = iterator(); it.hasNext();) {
      DragHandler handler = it.next();
      handler.onDragStart(dragStartEvent);
    }
  }

  /**
   * @deprecated Use {@link #fireDragStart(DragStartEvent)} instead.
   */
  public final void fireDragStart(Widget sender) {
    throw new UnsupportedOperationException();
  }

  /**
   * @deprecated Use {@link #firePreviewDragEnd(DragEndEvent)} instead.
   */
  public final void firePreviewDragEnd(DragContext context) throws VetoDragException {
  }

  /**
   * Fires a {@link DragHandler#onPreviewDragEnd(DragEndEvent)} on all handlers
   * in the collection.
   * 
   * @param dragEndEvent the event
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  public void firePreviewDragEnd(DragEndEvent dragEndEvent) throws VetoDragException {
    for (Iterator<DragHandler> it = iterator(); it.hasNext();) {
      DragHandler handler = it.next();
      handler.onPreviewDragEnd(dragEndEvent);
    }
  }

  /**
   * @deprecated Use {@link #firePreviewDragEnd(DragEndEvent)} instead.
   */
  public final void firePreviewDragEnd(Widget sender, Widget dropTarget) throws VetoDragException {
    throw new UnsupportedOperationException();
  }

  /**
   * @deprecated Use {@link #firePreviewDragStart(DragStartEvent)} instead.
   */
  public final void firePreviewDragStart(DragContext context) throws VetoDragException {
  }

  /**
   * Fires a {@link DragHandler#onPreviewDragStart(DragStartEvent)} on all
   * handlers in the collection.
   * 
   * @param dragStartEvent the event
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  public void firePreviewDragStart(DragStartEvent dragStartEvent) throws VetoDragException {
    for (Iterator<DragHandler> it = iterator(); it.hasNext();) {
      DragHandler handler = it.next();
      handler.onPreviewDragStart(dragStartEvent);
    }
  }

  /**
   * @deprecated Use {@link #firePreviewDragStart(DragStartEvent)} instead.
   */
  public final void firePreviewDragStart(Widget sender) throws VetoDragException {
    throw new UnsupportedOperationException();
  }
}
