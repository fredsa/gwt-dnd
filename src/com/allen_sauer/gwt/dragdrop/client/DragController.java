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

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;

/**
 * Interface which implementing drag controllers much implement.
 * 
 * <p>Be sure to also create one or more
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.DropController DropControllers}
 * and {@link #registerDropController(DropController) register} them.</p>
 */
public interface DragController extends SourcesDragEvents {

  abstract void addDragHandler(DragHandler handler);

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @param dropTarget widget on which draggable was dropped. 
   *        <code>null</code> if drag was canceled
   */
  abstract void dragEnd(Widget draggable, Widget dropTarget);

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   */
  abstract void dragStart(Widget draggable);

  abstract AbsolutePanel getBoundaryPanel();

  abstract DropController getIntersectDropController(Widget draggableProxy);

  /**
   * @return widget which will move as part of the drag operation.
   *         May be the actual draggable widget or an appropriate proxy widget.
   */
  abstract Widget getMovableWidget();

  /**
   * Attaches a {@link MouseDragHandler} (which is a
   * {@link com.google.gwt.user.client.ui.MouseListener}) to the widget and
   * adds the {@link #STYLE_DRAGGABLE} style to the widget. Call this method for
   * each which that should be made draggable by this DragController.
   * 
   * @param widget the widget to be made draggable
   */
  abstract void makeDraggable(Widget widget);

  /**
   * Similar to {@link #makeDraggable(Widget)}, but allow separate, child
   * to be specified as the drag handle by which the first widget can be dragged.
   * 
   * @param draggable the widget to be made draggable
   * @param dragHandle the widget by which widget can be dragged
   */
  abstract void makeDraggable(Widget draggable, Widget dragHandle);

  /**
   * Performs the reverse of {@link #makeDraggable(Widget)}, detaching the
   * {@link MouseDragHandler} from the widget and removing the
   * {@link #STYLE_DRAGGABLE} style from the widget. Call this method for
   * each which that should be made draggable by this DragController.
   * 
   * @param widget the widget to be made draggable
   */
  abstract void makeNotDraggable(Widget widget);

  abstract BoundaryDropController newBoundaryDropController(AbsolutePanel boundaryPanel);

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @param dropTarget widget on which draggable was dropped. 
   *        <code>null</code> if drag was canceled
   */
  abstract void notifyDragEnd(Widget draggable, Widget dropTarget);

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  abstract void previewDragEnd(Widget draggable, Widget dropTarget) throws VetoDragException;

  /**
   * Call back method for {@link MouseDragHandler}.
   * 
   * @param draggable widget which was being dragged
   * @throws VetoDragException if the proposed operation is unacceptable
   */
  abstract void previewDragStart(Widget draggable) throws VetoDragException;

  abstract void registerDropController(DropController dropController);

  abstract void removeDragHandler(DragHandler handler);
}