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

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

public class ResizeExample extends Example {

  private ResizeDragController resizeDragController;

  public ResizeExample() {
    super();
    resizeDragController = new ResizeDragController(RootPanel.get());
    setDragController(resizeDragController);

    ScrollPanel scrollPanel = new ScrollPanel();
    scrollPanel.setPixelSize(200, 100);
    scrollPanel.add(getLargeHTML());
    scrollPanel.addStyleName("demo-resize-panel");

    ResizePanel resizePanel = new ResizePanel(resizeDragController, scrollPanel);
    setWidget(resizePanel);
  }

  public Class getControllerClass() {
    return ResizeDragController.class;
  }

  public String getDescription() {
    return "Resize an embedded ScrollPanel";
  }

  private HTML getLargeHTML() {
    String t = "";
    for (int i = 0; i < 50; i++) {
      t += "<p>The quick brown fox jumped over the lazy dog.</p>";
    }
    return new HTML(t);
  }
}
