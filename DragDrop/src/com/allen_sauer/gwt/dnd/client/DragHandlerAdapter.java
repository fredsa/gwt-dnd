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
package com.allen_sauer.gwt.dnd.client;

/**
 * Convenience class for anonymous inner classes not wishing to define all
 * methods required by the {@link DragHandler} interface.
 */
public class DragHandlerAdapter implements DragHandler {

  public void onDragEnd(DragEndEvent event) {
  }

  public void onDragStart(DragStartEvent event) {
  }

  public void onPreviewDragEnd(DragEndEvent event) throws VetoDragException {
  }

  public void onPreviewDragStart(DragStartEvent event) throws VetoDragException {
  }

}
