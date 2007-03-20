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

import java.util.EventObject;

/**
 * Event containing information about the end of a drag.
 * 
 * TODO Add position information
 */
public class DragEndEvent extends EventObject {

  private Widget dropTarget;

  public DragEndEvent(Object source, Widget dropTarget) {
    super(source);
    this.dropTarget = dropTarget;
  }

  /**
   * @return the drop target widget is the drop is successful or <code>null</code>
   * if the drop was disallowed
   */
  public Widget getDropTarget() {
    return dropTarget;
  }
}
