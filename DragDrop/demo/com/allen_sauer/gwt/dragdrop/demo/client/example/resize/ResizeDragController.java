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
package com.allen_sauer.gwt.dragdrop.demo.client.example.resize;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.VetoDragException;

public class ResizeDragController extends DragController {

  public ResizeDragController(AbsolutePanel boundryPanel) {
    super(boundryPanel);
  }

  public void dragStart(Widget draggable) {
    super.dragStart(draggable);
    DOM.setStyleAttribute(draggable.getElement(), "border", "1px solid green");
  }

  public void previewDragStart(Widget draggable) throws VetoDragException {
    super.previewDragStart(draggable);
  }
}
