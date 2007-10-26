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
package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.util.Location;

/**
 * A {@link DropController} for the {@link com.google.gwt.user.client.ui.Panel}
 * which contains a given draggable widget.
 */
public class BoundaryDropController extends AbsolutePositionDropController {
  private boolean allowDropping;

  public BoundaryDropController(AbsolutePanel dropTarget, boolean allowDropping) {
    super(dropTarget);
    this.allowDropping = allowDropping;
  }

  public String getDropTargetStyleName() {
    return "dragdrop-boundary";
  }

  public DragEndEvent onDrop(Widget reference, Widget draggable, DragController dragController) {
    DragEndEvent dragEndEvent = super.onDrop(reference, draggable, dragController);
    return dragEndEvent;
  }

  protected Location getConstrainedLocation(Widget reference, Widget draggable, Widget widget) {
    return allowDropping ? super.getConstrainedLocation(reference, draggable, widget) : null;
  }
}
