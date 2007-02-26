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
package com.allen_sauer.gwt.dragdrop.demo.client.example;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController} example.
 */
public class AbsolutePositionExample extends Example {

  private AbsolutePositionDropController absolutePositionDropController;

  public AbsolutePositionExample(DragController dragController) {
    super(dragController);
    AbsolutePanel positioningDropTarget = new AbsolutePanel();
    positioningDropTarget.setPixelSize(400, 200);
    setWidget(positioningDropTarget);
    absolutePositionDropController = new AbsolutePositionDropController(positioningDropTarget);
    dragController.registerDropController(absolutePositionDropController);
  }

  public String getDescription() {
    return "Draggable widgets can be placed anywhere on the gray drop target.";
  }

  public Class getDropControllerClass() {
    return AbsolutePositionDropController.class;
  }

  protected void onLoad() {
    super.onLoad();
    absolutePositionDropController.drop(createDraggable(), 10, 30);
    absolutePositionDropController.drop(createDraggable(), 60, 8);
    absolutePositionDropController.drop(createDraggable(), 190, 60);
  }
}
