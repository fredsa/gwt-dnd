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
package com.allen_sauer.gwt.dnd.demo.client;

import com.google.gwt.user.client.ui.HTML;

/**
 * Demonstrate a draggable widget.
 */
public final class RedBoxDraggableWidget extends HTML {

  private static int counter;

  private static final String CSS_DEMO_RED_BOX_DRAGGABLE_WIDGET = "demo-red-box-draggable-widget";

  private static final int DRAGGABLE_SIZE = 65;

  public RedBoxDraggableWidget() {
    setPixelSize(DRAGGABLE_SIZE, DRAGGABLE_SIZE);
    setHTML("<i>drag me!</i> draggable widget #" + ++counter);
  }

  @Override
  protected void onLoad() {
    super.onLoad();
    addStyleName(CSS_DEMO_RED_BOX_DRAGGABLE_WIDGET);
  }
}
