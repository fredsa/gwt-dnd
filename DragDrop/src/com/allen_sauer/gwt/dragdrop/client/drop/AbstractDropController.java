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

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;

/**
 * Base class for typical drop controllers. Contains some basic functionality
 * like adjust widget styles.
 */
public abstract class AbstractDropController implements DropController {
  protected static final String CSS_DROP_TARGET_ENGAGE = "dragdrop-dropTarget-engage";

  private static final String CSS_DROP_TARGET = "dragdrop-dropTarget";

  private Widget dropTarget;

  public AbstractDropController(Widget dropTarget) {
    this.dropTarget = dropTarget;
    dropTarget.addStyleName(getDropTargetStyleName());
  }

  public Widget getDropTarget() {
    return dropTarget;
  }

  public String getDropTargetStyleName() {
    return CSS_DROP_TARGET;
  }

  /**
   * When overriding this method's drop behavior, be sure to also override
   * {@link #makeDragEndEvent(Widget, Widget, DragController)}.
   * 
   * @see com.allen_sauer.gwt.dragdrop.client.drop.DropController#onDrop(com.google.gwt.user.client.ui.Widget, com.google.gwt.user.client.ui.Widget, com.allen_sauer.gwt.dragdrop.client.DragController)
   */
  public DragEndEvent onDrop(Widget reference, Widget draggable, DragController dragController) {
    dropTarget.removeStyleName(CSS_DROP_TARGET_ENGAGE);
    return makeDragEndEvent(reference, draggable, dragController);
  }

  public void onEnter(Widget reference, Widget draggable, DragController dragController) {
    dropTarget.addStyleName(CSS_DROP_TARGET_ENGAGE);
  }

  public void onLeave(Widget draggable, DragController dragController) {
    dropTarget.removeStyleName(CSS_DROP_TARGET_ENGAGE);
  }

  public void onMove(int x, int y, Widget reference, Widget draggable, DragController dragController) {
  }

  public void onPreviewDrop(Widget reference, Widget draggable, DragController dragController) throws VetoDropException {
  }

  /**
   * Called by {@link AbstractDropController#onDrop(Widget, Widget, DragController)} to
   * generate a {@link DragEndEvent}. Implementing classes should override this method when
   * then override the {@link AbstractDropController#onDrop(Widget, Widget, DragController)}
   * behavior.
   * 
   * @param reference the reference widget from {@link #onDrop(Widget, Widget, DragController)}
   * @param draggable the draggable widget from {@link #onDrop(Widget, Widget, DragController)}
   * @param dragController the dragController from {@link #onDrop(Widget, Widget, DragController)}
   * @return
   */
  protected DragEndEvent makeDragEndEvent(Widget reference, Widget draggable, DragController dragController) {
    return new DragEndEvent(draggable, dropTarget);
  }
}
