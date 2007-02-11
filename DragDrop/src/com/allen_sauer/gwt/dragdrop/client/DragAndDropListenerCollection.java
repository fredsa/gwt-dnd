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

import java.util.Iterator;
import java.util.Vector;

/**
 * A helper class for implementers of the
 * {@link com.allen_sauer.gwt.dragdrop.client.SourcesDragAndDropEvents}
 * interface. This subclass of Vector assumes that all objects added to it will
 * be of type {@link com.allen_sauer.gwt.dragdrop.client.DragAndDropListener}.
 */
public class DragAndDropListenerCollection extends Vector {

  private static final long serialVersionUID = 1L;

  public void fireDragStart(Widget draggable) {
    for (Iterator it = iterator(); it.hasNext();) {
      DragAndDropListener listener = (DragAndDropListener) it.next();
      listener.onDragStart(draggable);
    }
  }

  public void fireDrop(Widget draggable, Widget dropTarget) {
    for (Iterator it = iterator(); it.hasNext();) {
      DragAndDropListener listener = (DragAndDropListener) it.next();
      listener.onDrop(draggable, dropTarget);
    }
  }

  public boolean fireIsDragAllowed(Widget draggable) {
    for (Iterator it = iterator(); it.hasNext();) {
      DragAndDropListener listener = (DragAndDropListener) it.next();
      if (listener.onIsDragAllowed(draggable)) {
        return true;
      }
    }
    return false;
  }

  public boolean fireIsDropAllowed(Widget draggable, Widget dropTarget) {
    for (Iterator it = iterator(); it.hasNext();) {
      DragAndDropListener listener = (DragAndDropListener) it.next();
      if (listener.onIsDropAllowed(draggable)) {
        return true;
      }
    }
    return false;
  }

}
