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
 * Create a DropController for each drop target on which draggable widgets can
 * be dropped. Do not forget to register each DropController with a
 * {@link com.allen_sauer.gwt.dragdrop.client.DragController}.
 */
public interface DropController {

  /**
   * Retrieve our drop target widget.
   * 
   * @return the widget representing the drop target associated with this
   *         controller
   */
  abstract Widget getDropTarget();

  /**
   * Retrieve the desired CSS style name to be applied to our drop target.
   * 
   * @return the CSS style name to be used by
   *         {@link Widget#addStyleName(String)} as this controller is
   *         constructed
   */
  abstract String getDropTargetStyleName();

  /**
   * Called when the draggable widget or its proxy is dropped on our drop
   * target. Implementing classes must attach the draggable widget to our drop
   * target in a suitable manner.
   * 
   * @see #onPreviewDrop(Widget, Widget, DragController)
   * 
   * @param reference the widget (either the actual draggable widget or a
   *            suitable proxy widget) which is physically currently at the
   *            desired drop position
   * @param draggable the actual draggable widget to which the drop operation
   *            applies
   * @param dragController the {@link DragController} coordinating the current
   *            drag-and-drop operation
   * @return event representing the drop action
   */
  abstract DragEndEvent onDrop(Widget reference, Widget draggable, DragController dragController);

  /**
   * Called when the draggable widget or its proxy engages our drop target. This
   * occurs when the widget area and the drop target area intersect and there
   * are no drop targets which are descendants of our drop target which also
   * intersect with the widget. If there are, the widget engages with the
   * descendant drop target instead.
   * 
   * @see #onLeave(Widget, DragController)
   * 
   * @param reference the widget (either the actual draggable widget or a
   *            suitable proxy widget) which is currently engaging
   *            (intersecting) with our drop target
   * @param draggable the actual draggable widget to which the eventual drop
   *            operation would apply
   * @param dragController the {@link DragController} coordinating the current
   *            drag-and-drop operation
   */
  abstract void onEnter(Widget reference, Widget draggable, DragController dragController);

  /**
   * Called when the reference widget stops engaging our drop target by leaving
   * the area of the page occupied by our drop target.
   * 
   * @see #onEnter(Widget, Widget, DragController)
   * 
   * @param draggable the actual draggable widget to which the eventual drop
   *            operation would've applied
   * @param dragController the {@link DragController} coordinating the current
   *            drag-and-drop operation
   */
  abstract void onLeave(Widget draggable, DragController dragController);

  /**
   * Called with each mouse movement while the reference widget is engaging our
   * drop target. {@link #onEnter(Widget, Widget, DragController)} is called
   * before this method is called.
   * 
   * @see #onEnter(Widget, Widget, DragController)
   * @see #onLeave(Widget, DragController)
   * 
   * @param reference the widget (either the actual draggable widget or a
   *            suitable proxy widget) which is currently engaging
   *            (intersecting) with our drop target
   * @param draggable the actual draggable widget to which the eventual drop
   *            operation would apply
   * @param dragController the {@link DragController} coordinating the current
   *            drag-and-drop operation
   */
  abstract void onMove(Widget reference, Widget draggable, DragController dragController);

  /**
   * Called just prior to {@link #onDrop(Widget, Widget, DragController)} to
   * allow the drop operation to be cancelled by throwing a
   * {@link VetoDropException}.
   * 
   * @param reference the widget (either the actual draggable widget or a
   *            suitable proxy widget) which is physically currently at the
   *            desired drop position
   * @param draggable the actual draggable widget to which the drop operation
   *            applies
   * @param dragController the {@link DragController} coordinating the current
   *            drag-and-drop operation
   * @throws VetoDropException when the proposed drop operation is unacceptable
   */
  abstract void onPreviewDrop(Widget reference, Widget draggable, DragController dragController) throws VetoDropException;
}