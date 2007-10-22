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
package com.allen_sauer.gwt.dragdrop.demo.client.example.window;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

import com.allen_sauer.gwt.dragdrop.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

/**
 * Demonstrate the ability to capture
 * {@link com.allen_sauer.gwt.dragdrop.client.DragStartEvent DragStartEvents}
 * and {@link com.allen_sauer.gwt.dragdrop.client.DragEndEvent DragEndEvents}.
 */
public final class WindowExample extends Example {
  private static final String CSS_DEMO_RESIZE_EXAMPLE = "demo-WindowExample";
  private static final String GWT_HOME_PAGE = "http://code.google.com/webtoolkit/";

  public WindowExample(DemoDragHandler demoDragHandler) {
    addStyleName(CSS_DEMO_RESIZE_EXAMPLE);

    // use the boundary panel as this composite's widget
    final AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(600, 300);
    setWidget(boundaryPanel);
    
    boundaryPanel.add(new Label("hi"), 10, 10);

    // initialize window controller which provides drag and resize windows
    WindowController windowController = new WindowController(boundaryPanel);
    windowController.getPickupDragController().addDragHandler(demoDragHandler);

    // create the first panel
    HTML header1 = new HTML("An draggable &amp; resizable panel");
    WindowPanel windowPanel1 = new WindowPanel(windowController, header1, getLargeHTML());
    boundaryPanel.add(windowPanel1, 20, 20);

    // create the second panel
    HTML header2 = new HTML("A draggable &amp; resizable <code>IFRAME</code>");
    Frame iframe2 = getIframe(GWT_HOME_PAGE);
    WindowPanel windowPanel2 = new WindowPanel(windowController, header2, iframe2);
    boundaryPanel.add(windowPanel2, 50, 80);
  }

  public String getDescription() {
    return "Resize an embedded Widget using a composite of Grid and ScrollPanel.";
  }

  public Class[] getInvolvedClasses() {
    return new Class[] {
        WindowExample.class, WindowController.class, WindowPanel.class, ResizeDropController.class, ResizeDragController.class,};
  }

  private Frame getIframe(String url) {
    Frame iframe = new Frame() {
      protected void onLoad() {
        super.onLoad();
        // IE work around for disappearing IFRAME when parent is re-attached
        DOM.setStyleAttribute(getElement(), "display", "none");
        DOM.setStyleAttribute(getElement(), "display", "");
      }
    };
    DOM.setElementAttribute(iframe.getElement(), "frameBorder", "no");
    iframe.setUrl(url);
    iframe.addStyleName("demo-WindowPanel-iframe");
    return iframe;
  }

  private HTML getLargeHTML() {
    String t = "You can resize this panel by any of the four edges or corners.<br>";
    for (int i = 0; i < 3; i++) {
      t += "<br>The quick brown fox jumped over the lazy dog.";
    }
    HTML html = new HTML(t);
    html.addStyleName("demo-resize-html");
    return html;
  }
}
