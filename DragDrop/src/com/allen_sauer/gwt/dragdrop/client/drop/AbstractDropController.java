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

import com.allen_sauer.gwt.dragdrop.client.DragContext;
import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;

/**
 * Base class for typical drop controllers. Contains some basic functionality
 * like adjust widget styles.
 */
public abstract class AbstractDropController implements DropController {
  /**
   * @deprecated Use {@link #PRIVATE_CSS_DROP_TARGET_ENGAGE} instead
   */
  protected static final String CSS_DROP_TARGET_ENGAGE;

  private static final String CSS_DROP_TARGET = "dragdrop-dropTarget";
  private static final String PRIVATE_CSS_DROP_TARGET_ENGAGE = "dragdrop-dropTarget-engage";

  static {
    CSS_DROP_TARGET_ENGAGE = PRIVATE_CSS_DROP_TARGET_ENGAGE;
  }

  private Widget dropTarget;

  public AbstractDropController(Widget dropTarget) {
    this.dropTarget = dropTarget;
    dropTarget.addStyleName(CSS_DROP_TARGET);
  }

  /**
   * @deprecated No longer part of gwt-dnd 2.x API.
   */
  public DragController getCurrentDragController() {
    throw new UnsupportedOperationException();
  }

  public Widget getDropTarget() {
    return dropTarget;
  }

  /**
   * @deprecated Never part of the a released API.
   */
  public final String getDropTargetEngageStyleName() {
    throw new UnsupportedOperationException();
  }

  /**
   * @deprecated No longer part of gwt-dnd 2.x API.
   */
  public final String getDropTargetStyleName() {
    throw new UnsupportedOperationException();
  }

  /**
   * When overriding this method's drop behavior, be sure to also override
   * {@link #makeDragEndEvent(DragContext)}, which is
   * called as a part of this method invocation to create the return value.
   */
  public DragEndEvent onDrop(DragContext context) {
    return makeDragEndEvent(context);
  }

  public final DragEndEvent onDrop(Widget reference, Widget draggable, DragController dragController) {
    throw new UnsupportedOperationException();
  }

  public void onEnter(DragContext context) {
    dropTarget.addStyleName(PRIVATE_CSS_DROP_TARGET_ENGAGE);
  }

  public final void onEnter(Widget reference, Widget draggable, DragController dragController) {
    throw new UnsupportedOperationException();
  }

  public void onLeave(DragContext context) {
    dropTarget.removeStyleName(PRIVATE_CSS_DROP_TARGET_ENGAGE);
  }

  public final void onLeave(Widget draggable, DragController dragController) {
    throw new UnsupportedOperationException();
  }

  public final void onLeave(Widget reference, Widget draggable, DragController dragController) {
    throw new UnsupportedOperationException();
  }

  public void onMove(DragContext context) {
  }

  public final void onMove(int x, int y, Widget reference, Widget draggable, DragController dragController) {
    throw new UnsupportedOperationException();
  }

  public final void onMove(Widget reference, Widget draggable, DragController dragController) {
    throw new UnsupportedOperationException();
  }

  public void onPreviewDrop(DragContext context) throws VetoDropException {
  }

  public final void onPreviewDrop(Widget reference, Widget draggable, DragController dragController) throws VetoDropException {
    throw new UnsupportedOperationException();
  }

  /**
   * Called by {@link AbstractDropController#onDrop(DragContext)} to
   * generate a {@link DragEndEvent}. Implementing classes should override this method when
   * then override the {@link AbstractDropController#onDrop(DragContext)}
   * behavior.
   * 
   * @param context the current drag context from {@link #onDrop(DragContext)}
   * @return the new DragEndEvent
   */
  protected DragEndEvent makeDragEndEvent(DragContext context) {
    return new DragEndEvent(context);
  }

  /**
   * @deprecated Use {@link #makeDragEndEvent(DragContext)} instead.
   */
  protected final DragEndEvent makeDragEndEvent(Widget reference, Widget draggable, DragController dragController) {
    throw new UnsupportedOperationException();
  }
}
