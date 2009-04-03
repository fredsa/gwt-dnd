/*
 * Copyright 2009 Fred Sauer
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
package com.allen_sauer.gwt.dnd.demo.client.example;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.demo.client.RedBoxDraggableWidget;

/**
 * Shared utility methods to produce draggable widgets.
 */
public class DraggableFactory {

  /**
   * Create a new draggable pumpkin image.
   * 
   * @param dragController the drag controller
   * @return the new widget
   */
  public static Widget createDraggablePumpkinImage(DragController dragController) {
    Image image = new Image("images/99pumpkin2-65x58.jpg");
    dragController.makeDraggable(image);
    return image;
  }

  /**
   * Create a new draggable red box widget.
   * 
   * @param dragController the drag controller
   * @return the new widget
   */
  public static Widget createDraggableRedBox(DragController dragController) {
    Widget redBox = new RedBoxDraggableWidget();
    dragController.makeDraggable(redBox);
    return redBox;
  }
}
