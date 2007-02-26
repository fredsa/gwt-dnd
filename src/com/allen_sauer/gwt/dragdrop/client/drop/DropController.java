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

/**
 * Create a DropController for each drop target on which draggable widgets can be dropped.
 * Do not forget to register each DropController with a
 * {@link com.allen_sauer.gwt.dragdrop.client.DragController}.
 */
public interface DropController {

  public abstract Widget getDropTarget();

  public abstract String getDropTargetStyleName();

  public abstract void onDrop(Widget reference, Widget draggable, DragController dragController);

  public abstract void onEnter(Widget reference, Widget draggable, DragController dragController);

  public abstract void onLeave(Widget draggable, DragController dragController);

  public abstract void onMove(Widget reference, Widget draggable, DragController dragController);

  public abstract boolean onPreviewDrop(Widget reference, Widget draggable, DragController dragController);

}