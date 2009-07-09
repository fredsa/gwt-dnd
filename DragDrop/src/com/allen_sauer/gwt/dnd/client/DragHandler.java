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
package com.allen_sauer.gwt.dnd.client;

import java.util.EventListener;

/**
 * {@link EventListener} interface for drag-and-drop events.
 */
public interface DragHandler extends EventListener {

  /**
   * Fired when drag operation terminates.
   * 
   * @param event an event object containing information about the drag operation
   */
  void onDragEnd(DragEndEvent event);

  /**
   * Fired when drag is initiated.
   * 
   * @param event an event object containing information about the drag operation
   */
  void onDragStart(DragStartEvent event);

  /**
   * Fired before {@link #onDragEnd(DragEndEvent)} is fired and provides an opportunity for any
   * registered DragHandler to throw {@link VetoDragException} to prevent the operation.
   * 
   * @param event an event object containing information about the drag operation
   * @throws VetoDragException when the drag operation is unacceptable
   */
  void onPreviewDragEnd(DragEndEvent event) throws VetoDragException;

  /**
   * Fired before {@link #onDragStart(DragStartEvent)} is fired and provides an opportunity for any
   * registered DragHandler to throw {@link VetoDragException} to prevent the operation.
   * 
   * @param event an event object containing information about the drag operation
   * @throws VetoDragException when the drag operation is unacceptable
   */
  void onPreviewDragStart(DragStartEvent event) throws VetoDragException;
}
