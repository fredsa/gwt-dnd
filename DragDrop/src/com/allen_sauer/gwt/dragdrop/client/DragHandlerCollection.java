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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Helper class for controllers that accept
 * {@link com.allen_sauer.gwt.dragdrop.client.DragHandler DragHandlers}. This
 * subclass of ArrayList assumes that all items added to it will be of type
 * {@link com.allen_sauer.gwt.dragdrop.client.DragHandler}.
 */
public class DragHandlerCollection extends ArrayList {

  /**
   * Fires a {@link DragHandler#onDragEbd(DragEndEvent)} on
   * all handlers in the collection.
   * 
   * @param sender the object sending the event
   * @param dropTarget the widget on which the drag operation terminated.
   *        <code>null</code> if drag was canceled
   */
  public void fireDragEnd(Widget sender, Widget dropTarget) {
    DragEndEvent event = new DragEndEvent(sender, dropTarget);

    for (Iterator it = iterator(); it.hasNext();) {
      DragHandler handler = (DragHandler) it.next();
      handler.onDragEnd(event);
    }
  }

  /**
   * Fires a {@link DragHandler#onDragStart(DragStartEvent)} on
   * all handlers in the collection.
   * 
   * @param sender the object sending the event
   */
  public void fireDragStart(Widget sender) {
    DragStartEvent event = new DragStartEvent(sender);

    for (Iterator it = iterator(); it.hasNext();) {
      DragHandler handler = (DragHandler) it.next();
      handler.onDragStart(event);
    }
  }

  /**
   * Fires a {@link DragHandler#onPreviewDragEnd(DragEndEvent)} on
   * all handlers in the collection.
   * 
   * @param sender the object sending the event
   * @param dropTarget the widget on which the drag operation terminated.
   *        <code>null</code> if drag was canceled
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  public void firePreviewDragEnd(Widget sender, Widget dropTarget) throws VetoDragException {
    DragEndEvent event = new DragEndEvent(sender, dropTarget);

    for (Iterator it = iterator(); it.hasNext();) {
      DragHandler handler = (DragHandler) it.next();
      handler.onPreviewDragEnd(event);
    }
  }

  /**
   * Fires a {@link DragHandler#onPreviewDragStart(DragStartEvent)} on
   * all handlers in the collection.
   * 
   * @param sender the object sending the event
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  public void firePreviewDragStart(Widget sender) throws VetoDragException {
    DragStartEvent event = new DragStartEvent(sender);

    for (Iterator it = iterator(); it.hasNext();) {
      DragHandler handler = (DragHandler) it.next();
      handler.onPreviewDragStart(event);
    }
  }

}
