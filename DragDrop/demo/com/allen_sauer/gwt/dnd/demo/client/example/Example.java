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
package com.allen_sauer.gwt.dnd.demo.client.example;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.client.util.StringUtil;

/**
 * Class representing a single drag-and-drop example.
 */
public abstract class Example extends SimplePanel {

  private static final String CSS_DEMO_EXAMPLE_PANEL = "demo-example-panel";

  private DragController dragController;

  private boolean initialLoaded = false;

  /**
   * Constructor for examples which create their own drag controller.
   */
  public Example() {
    addStyleName(CSS_DEMO_EXAMPLE_PANEL);
  }

  /**
   * Constructor for examples which utilize the common drag controller.
   * 
   * @param dragController the shared drag controller
   */
  public Example(DragController dragController) {
    this();
    this.dragController = dragController;
  }

  /**
   * Get a brief description of this example.
   * 
   * @return the description
   */
  public abstract String getDescription();

  /**
   * Get our DragController.
   * 
   * @return the drag controller.
   */
  public DragController getDragController() {
    return dragController;
  }

  public String getHistoryToken() {
    return StringUtil.getShortTypeName(getInvolvedClasses()[0]);
  }

  /**
   * Return the classes involved in this example.
   * 
   * @return an array of involved classes
   */
  public abstract Class<?>[] getInvolvedClasses();

  /**
   * Convenience method to create a default draggable widget. The widget is
   * automatically made draggable by calling
   * {@link DragController#makeDraggable(Widget)}.
   * 
   * @return a new draggable widget
   */
  protected Widget createDraggable() {
    return DraggableFactory.createDraggableRedBox(dragController);
  }

  /**
   * Called when {@link #onLoad()} is called for the first time.
   */
  protected void onInitialLoad() {
  }

  /**
   * Calls {@link #onInitialLoad()} when called for the first time.
   */
  @Override
  protected void onLoad() {
    super.onLoad();
    if (!initialLoaded) {
      onInitialLoad();
      initialLoaded = true;
    }
  }
}