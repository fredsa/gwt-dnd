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
package com.allen_sauer.gwt.dnd.client.drop;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;

/**
 * Base class for typical drop controllers. Contains some basic functionality
 * like adjust widget styles.
 */
public abstract class AbstractDropController implements DropController {

  /**
   * CSS style name applied to drop targets.
   */
  private static final String CSS_DROP_TARGET = "dragdrop-dropTarget";

  /**
   * CSS style name which is applied to drop targets which are being actively
   * engaged by the current drag operation.
   */
  private static final String PRIVATE_CSS_DROP_TARGET_ENGAGE = "dragdrop-dropTarget-engage";

  /**
   * The drop target.
   */
  private Widget dropTarget;

  public AbstractDropController(Widget dropTarget) {
    this.dropTarget = dropTarget;
    dropTarget.addStyleName(CSS_DROP_TARGET);
  }

  public Widget getDropTarget() {
    return dropTarget;
  }

  public void onDrop(DragContext context) {
  }

  public void onEnter(DragContext context) {
    dropTarget.addStyleName(PRIVATE_CSS_DROP_TARGET_ENGAGE);
  }

  public void onLeave(DragContext context) {
    dropTarget.removeStyleName(PRIVATE_CSS_DROP_TARGET_ENGAGE);
  }

  public void onMove(DragContext context) {
  }

  public void onPreviewDrop(DragContext context) throws VetoDragException {
  }
}
