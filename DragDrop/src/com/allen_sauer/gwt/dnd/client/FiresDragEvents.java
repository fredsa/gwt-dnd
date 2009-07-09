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

/**
 * A class that implements this interface sources the events defined by the
 * {@link com.allen_sauer.gwt.dnd.client.DragHandler} interface.
 */
public interface FiresDragEvents {

  /**
   * Add another {@link DragHandler} to listen for drag related events.
   * 
   * @param handler the handler to add
   */
  void addDragHandler(DragHandler handler);

  /**
   * Remove a previously added {@link DragHandler}.
   * 
   * @param handler the handler to remove
   */
  void removeDragHandler(DragHandler handler);
}
