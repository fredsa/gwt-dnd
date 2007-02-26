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

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.demo.client.RedBoxDraggableWidget;

/**
 * Abstract class representing a drag-and-drop example.
 */
public abstract class Example extends SimplePanel {

  protected static final String STYLE_NOT_ENGAGABLE = "dragdrop-not-engagable";

  private static final String STYLE_DEMO_EXAMPLE_PANEL = "demo-example-panel";

  private DragController dragController;

  public Example(DragController dragController) {
    this.dragController = dragController;
  }

  public abstract String getDescription();

  public DragController getDragController() {
    return dragController;
  }

  public abstract Class getDropControllerClass();

  protected Widget createDraggable() {
    Widget redBox = new RedBoxDraggableWidget();
    dragController.makeDraggable(redBox);
    return redBox;
  }

  protected void onLoad() {
    super.onLoad();
    addStyleName(STYLE_DEMO_EXAMPLE_PANEL);
  }
}