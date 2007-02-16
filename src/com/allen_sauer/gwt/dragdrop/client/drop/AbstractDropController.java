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
package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;

/**
 * Base class for typical drop controllers.
 * Contains some basic functionalty like adjust widget styles.
 */
public abstract class AbstractDropController implements DropController {

  private static final String STLE_ENGAGE = "dragdrop-engage";
  private static final String STYLE_DROP_TARGET = "dragdrop-dropTarget";

  private Widget dropTarget;

  public AbstractDropController(Widget dropTarget) {
    this.dropTarget = dropTarget;
    dropTarget.addStyleName(getDropTargetStyleName());
  }

  public void drop(Widget widget) {
    dropTarget.removeStyleName(STLE_ENGAGE);
  }

  public Widget getDropTarget() {
    return dropTarget;
  }

  public String getDropTargetStyleName() {
    return STYLE_DROP_TARGET;
  }

  public void onDrop(Widget reference, Widget draggable, DragController dragController) {
    dropTarget.removeStyleName(STLE_ENGAGE);
  }

  public void onEnter(Widget reference, Widget draggable, DragController dragController) {
    dropTarget.addStyleName(STLE_ENGAGE);
  }

  public void onLeave(Widget draggable, DragController dragController) {
    dropTarget.removeStyleName(STLE_ENGAGE);
  }

  public void onMove(Widget reference, Widget draggable, DragController dragController) {
  }
}
