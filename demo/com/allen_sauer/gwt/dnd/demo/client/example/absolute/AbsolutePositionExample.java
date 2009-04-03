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
package com.allen_sauer.gwt.dnd.demo.client.example.absolute;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;

/**
 * {@link com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController}
 * example.
 */
public final class AbsolutePositionExample extends Example {

  private static final String CSS_DEMO_ABSOLUTE_POSITION_EXAMPLE = "demo-AbsolutePositionExample";

  private AbsolutePositionDropController absolutePositionDropController;

  public AbsolutePositionExample(PickupDragController dragController) {
    super(dragController);
    addStyleName(CSS_DEMO_ABSOLUTE_POSITION_EXAMPLE);

    // use the drop target as this composite's widget
    AbsolutePanel positioningDropTarget = new AbsolutePanel();
    positioningDropTarget.setPixelSize(400, 200);
    setWidget(positioningDropTarget);

    // instantiate our drop controller
    absolutePositionDropController = new AbsolutePositionDropController(positioningDropTarget);
    dragController.registerDropController(absolutePositionDropController);
  }

  @Override
  public String getDescription() {
    return "Draggable widgets can be placed anywhere on the gray drop target.";
  }

  @Override
  public Class<?>[] getInvolvedClasses() {
    return new Class[] {AbsolutePositionExample.class, AbsolutePositionDropController.class,};
  }

  @Override
  protected void onInitialLoad() {
    absolutePositionDropController.drop(createDraggable(), 10, 30);
    absolutePositionDropController.drop(createDraggable(), 60, 8);
    absolutePositionDropController.drop(createDraggable(), 190, 60);
  }
}
