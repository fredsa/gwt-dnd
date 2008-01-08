/*
 * Copyright 2007 Fred Sauer
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

import com.allen_sauer.gwt.dnd.client.util.StringUtil;

import java.util.EventObject;

/**
 * {@link EventObject} containing information about the end of a drag.
 */
public class DragEndEvent extends EventObject {
  private final transient DragContext context;

  public DragEndEvent(DragContext context) {
    super(context.draggable);
    this.context = context;
    assert context.finalDropController == null == (context.vetoException != null);
  }

  /**
   * Determine the drag context at the end of the drag operation.
   * 
   * @return the drag context
   */
  public DragContext getContext() {
    return context;
  }

  /**
   * @deprecated Use {@link #getContext()} instead.
   */
  public Widget getDropTarget() {
    return context.dropController == null ? null : context.dropController.getDropTarget();
  }

  /**
   * Return a string representation of this event.
   */
  public String toString() {
    String dropTargetText;
    if (context.finalDropController != null) {
      dropTargetText = "dropTarget="
          + StringUtil.getShortTypeName(context.finalDropController.getDropTarget());
    } else {
      dropTargetText = "[cancelled: " + context.vetoException + "]";
    }
    return "DragEndEvent(" + dropTargetText + ", context="
        + StringUtil.getShortTypeName(getSource()) + ")";
  }
}
