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
package com.mycompany.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;

/**
 * Illustrative example.
 */
public class MyApplication1 implements EntryPoint {

  /**
   * Main entry point method.
   */
  public void onModuleLoad() {
    // ensure the document BODY has dimensions in standards mode
    RootPanel.get().setPixelSize(600, 600);

    // create a DragController to manage drag-n-drop actions
    // note: This creates an implicit DropController for the boundary panel
    PickupDragController dragController = new PickupDragController(RootPanel.get(), true);

    // add a new image to the boundary panel and make it draggable
    Image img = new Image("http://code.google.com/webtoolkit/logo-185x175.png");
    RootPanel.get().add(img, 40, 30);
    dragController.makeDraggable(img);
  }
}
