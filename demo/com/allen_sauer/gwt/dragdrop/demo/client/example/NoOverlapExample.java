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
import com.allen_sauer.gwt.dragdrop.client.drop.NoOverlapDropController;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.NoOverlapDropController} example.
 */
public final class NoOverlapExample extends Example {
  private NoOverlapDropController noOverlapDropController;

  public NoOverlapExample(DragController dragController) {
    super(dragController);
    AbsolutePanel noOverlapDropTarget = new AbsolutePanel();
    noOverlapDropTarget.setPixelSize(400, 200);
    setWidget(noOverlapDropTarget);
    noOverlapDropController = new NoOverlapDropController(noOverlapDropTarget);
    dragController.registerDropController(noOverlapDropController);
  }

  public Class getControllerClass() {
    return NoOverlapDropController.class;
  }

  public String getDescription() {
    return "Widgets cannot be dropped on top of (overlapping) other dropped widgets.";
  }

  protected void onLoad() {
    super.onLoad();
    noOverlapDropController.drop(createDraggable(), 10, 10);
    noOverlapDropController.drop(createDraggable(), 90, 60);
    noOverlapDropController.drop(createDraggable(), 190, 50);
  }
}
