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
package com.allen_sauer.gwt.dnd.demo.client.example.window;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;

import com.allen_sauer.gwt.dnd.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;

/**
 * Demonstrate the ability to capture
 * {@link com.allen_sauer.gwt.dnd.client.DragStartEvent DragStartEvents}
 * and {@link com.allen_sauer.gwt.dnd.client.DragEndEvent DragEndEvents}.
 */
public final class WindowExample extends Example {

  private static final String CSS_DEMO_RESIZE_EXAMPLE = "demo-WindowExample";

  /**
   * IFRAME URL.
   * Note: don't use Google Code Project Hosting or other urchin enabled site.
   */
  private static final String IFRAME_URL = "http://allen-sauer.com/gwt/";

  public WindowExample(DemoDragHandler demoDragHandler) {
    addStyleName(CSS_DEMO_RESIZE_EXAMPLE);

    // use the boundary panel as this composite's widget
    final AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(600, 400);
    setWidget(boundaryPanel);

    // initialize window controller which provides drag and resize windows
    WindowController windowController = new WindowController(boundaryPanel);
    windowController.getPickupDragController().addDragHandler(demoDragHandler);

    // create the first panel
    HTML header1 = new HTML("An draggable &amp; resizable panel");
    HTML html1 = new HTML(makeText().replaceAll("\n", "<br>\n"));
    html1.addStyleName("demo-resize-html");
    WindowPanel windowPanel1 = new WindowPanel(windowController, header1, html1, true);
    boundaryPanel.add(windowPanel1, 20, 20);

    // create the second panel
    HTML header2 = new HTML("A draggable &amp; resizable <code>IFRAME</code>");
    Frame iframe2 = getIframe(IFRAME_URL);
    WindowPanel windowPanel2 = new WindowPanel(windowController, header2, iframe2, false);
    boundaryPanel.add(windowPanel2, 50, 80);

    // create the third panel
    HTML header3 = new HTML("A draggable &amp; resizable <code>IMG</code>");
    Image image3 = new Image("images/99pumpkin2-65x58.jpg");
    image3.setPixelSize(65, 58);
    image3.addStyleName("demo-resize-html");
    WindowPanel windowPanel3 = new WindowPanel(windowController, header3, image3, false);
    boundaryPanel.add(windowPanel3, 70, 30);

    // create the fourth panel
    HTML header4 = new HTML("A draggable &amp; resizable <code>TEXTAREA</code>");
    TextArea textArea4 = new TextArea();
    textArea4.addStyleName("demo-resize-html");
    textArea4.setText(makeText());
    WindowPanel windowPanel4 = new WindowPanel(windowController, header4, textArea4, false);
    boundaryPanel.add(windowPanel4, 10, 120);
  }

  @Override
  public String getDescription() {
    return "Resize an embedded Widget using a composite of Grid and ScrollPanel.";
  }

  @Override
  public Class<?>[] getInvolvedClasses() {
    return new Class[] {
        WindowExample.class, WindowController.class, WindowPanel.class, ResizeDragController.class,};
  }

  private Frame getIframe(String url) {
    Frame iframe = new Frame() {

      @Override
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

  private String makeText() {
    String t = "You can resize this panel by any of the four edges or corners.\n";
    for (int i = 0; i < 3; i++) {
      t += "The quick brown fox jumped over the lazy dog.\n";
    }
    return t;
  }
}
