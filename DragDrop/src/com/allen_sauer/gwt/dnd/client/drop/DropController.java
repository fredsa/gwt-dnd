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
package com.allen_sauer.gwt.dnd.client.drop;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;

/**
 * Create a DropController for each drop target on which draggable widgets can be dropped. Do not
 * forget to register each DropController with a
 * {@link com.allen_sauer.gwt.dnd.client.DragController}.
 */
public interface DropController {

  /**
   * Retrieve our drop target widget.
   * 
   * @return the widget representing the drop target associated with this controller
   */
  Widget getDropTarget();

  /**
   * Called when the draggable widget or its proxy is dropped on our drop target. Implementing
   * classes must attach the draggable widget to our drop target in a suitable manner.
   * 
   * @see #onPreviewDrop(DragContext)
   * 
   * @param context the current drag context
   */
  void onDrop(DragContext context);

  /**
   * Called when the draggable widget or its proxy engages our drop target. This occurs when the
   * widget area and the drop target area intersect and there are no drop targets which are
   * descendants of our drop target which also intersect with the widget. If there are, the widget
   * engages with the descendant drop target instead.
   * 
   * @see #onLeave(DragContext)
   * 
   * @param context the current drag context
   */
  void onEnter(DragContext context);

  /**
   * Called when the reference widget stops engaging our drop target by leaving the area of the page
   * occupied by our drop target, or after {@link #onDrop(DragContext)} to allow for any cleanup.
   * 
   * @see #onEnter(DragContext)
   * 
   * @param context the current drag context
   */
  void onLeave(DragContext context);

  /**
   * Called with each mouse movement while the reference widget is engaging our drop target.
   * {@link #onEnter(DragContext)} is called before this method is called.
   * 
   * @see #onEnter(DragContext)
   * @see #onLeave(DragContext)
   * 
   * @param context the current drag context
   */
  void onMove(DragContext context);

  /**
   * Called just prior to {@link #onDrop(DragContext)} to allow the drop operation to be cancelled
   * by throwing a {@link VetoDragException}.
   * 
   * @param context the current drag context
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  void onPreviewDrop(DragContext context) throws VetoDragException;
}