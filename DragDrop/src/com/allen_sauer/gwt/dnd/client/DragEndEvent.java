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

import com.allen_sauer.gwt.dnd.client.util.StringUtil;

/**
 * {@link java.util.EventObject} containing information about the end of a drag.
 */
@SuppressWarnings("serial")
public class DragEndEvent extends DragEvent {

  public DragEndEvent(DragContext context) {
    super(context);
  }

  /**
   * Return a string representation of this event.
   * 
   * @return string representation of this event
   */
  @Override
  public String toString() {
    String dropTargetText;
    if (context.finalDropController != null) {
      dropTargetText = "dropTarget="
          + StringUtil.getShortTypeName(context.finalDropController.getDropTarget());
    } else {
      dropTargetText = "[cancelled]";
    }
    return "DragEndEvent(" + dropTargetText + ", source=" + getSourceShortTypeName() + ")";
  }
}
