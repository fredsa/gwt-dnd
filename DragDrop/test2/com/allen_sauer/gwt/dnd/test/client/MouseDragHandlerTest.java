/*
 * Copyright 2010 Fred Sauer
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

package com.allen_sauer.gwt.dnd.test.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;

public class MouseDragHandlerTest extends GWTTestCase {
  private abstract class Step {

    Step() {
      new Timer() {

        @Override
        public void run() {
          try {
            Step.this.run();
          } catch (Throwable e) {
            fail("#" + e.toString() + "#");
          }
        }
      }.schedule(getDelayMillis());
    }

    abstract void run();
  }

  private static final String id = "gwt-debug-box1";

  private static native String getCompatMode() /*-{
    return $doc.compatMode;
  }-*/;

  private int stepDelayMillis;

  @Override
  public String getModuleName() {
    return "com.allen_sauer.gwt.dnd.test.DragDropTest";
  }

  public void testMouseDown() {
    delayTestFinish(5000);
    final Element elem = Document.get().getElementById(id);
    Document.get().createClickEvent(0, 10, 10, 10, 10, false, false, false, false);

    NativeEvent evt;

    final int oldX = elem.getAbsoluteLeft();
    final int oldY = elem.getAbsoluteTop();

    evt = Document.get().createMouseDownEvent(0, 10, 10, 10, 10, false, false, false, false, 1);
    elem.dispatchEvent(evt);

    assertEquals("draggable x after mouse down", oldX, elem.getAbsoluteLeft());
    assertEquals("draggabel y after mouse down", oldY, elem.getAbsoluteTop());

    new Step() {
      @Override
      void run() {
        NativeEvent evt = Document.get().createMouseMoveEvent(
            0, 20, 20, 200, 200, false, false, false, false, 1);
        elem.dispatchEvent(evt);

        assertEquals("draggable x after mouse move", oldX + 190, elem.getAbsoluteLeft());
        assertEquals("draggabel y after mouse move", oldY + 190, elem.getAbsoluteTop());
      }
    };

    new Step() {
      @Override
      void run() {
        NativeEvent evt = Document.get().createMouseUpEvent(
            0, 20, 20, 100, 100, false, false, false, false, 1);
        elem.dispatchEvent(evt);

        assertEquals("draggable x after mouse up", oldX + 90, elem.getAbsoluteLeft());
        assertEquals("draggable y after mouse up", oldY + 90, elem.getAbsoluteTop());
      }
    };

  }

  @Override
  protected void gwtSetUp() throws Exception {
    stepDelayMillis = 0;
    RootPanel rootPanel = RootPanel.get();
    rootPanel.add(new HTML("DragDropTest is in <b>" + getCompatMode() + "</b> mode."));
    rootPanel.getElement().getStyle().setHeight(800, Unit.PX);
    PickupDragController dragController = new PickupDragController(rootPanel, true);
    for (int i = 0; i < 8; i++) {
      FocusPanel panel = new FocusPanel();
      panel.getElement().getStyle().setBackgroundColor("red");
      String id = "box" + i;
      panel.ensureDebugId(id);
      panel.add(new Label(id));
      panel.setPixelSize(100, 100);
      rootPanel.add(panel, i * 125, 0);
      dragController.makeDraggable(panel);
    }
  }

  private int getDelayMillis() {
    stepDelayMillis += 200;
    return stepDelayMillis;
  }
}
