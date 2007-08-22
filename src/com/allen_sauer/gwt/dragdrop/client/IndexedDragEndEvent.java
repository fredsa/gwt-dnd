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
package com.allen_sauer.gwt.dragdrop.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * {@link DragEndEvent} containing information about the end of a drag
 * for drop targets which are {@link com.google.gwt.user.client.ui.IndexedPanel IndexedPanels}.
 */
public class IndexedDragEndEvent extends DragEndEvent {

  private int index;

  public IndexedDragEndEvent(Object source, Widget dropTarget, int index) {
    super(source, dropTarget);
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public String toString() {
    return "IndexedDragEndEvent(index=" + index + ")";
  }
}
