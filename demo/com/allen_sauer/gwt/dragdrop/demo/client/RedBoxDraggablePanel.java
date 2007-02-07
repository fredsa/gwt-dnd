/*
 * Copyright 2006 Fred Sauer
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
package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * Demonstrate a draggable panel.
 */
public class RedBoxDraggablePanel extends FocusPanel {

  private static int counter;
  public static final int DRAGGABLE_SIZE = 65;

  public RedBoxDraggablePanel() {
    setPixelSize(DRAGGABLE_SIZE, DRAGGABLE_SIZE);
    setWidget(new HTML("<i>drag me!</i> draggable widget #" + ++counter, true));
  }

  // TODO cancel text selection operation in Firefox
  public void onBrowserEvent(Event event) {
    if ((DOM.eventGetType(event) & Event.MOUSEEVENTS) != 0) {
      // TODO handle this in library instead of demo application code
      DOM.eventPreventDefault(event);
    }
    super.onBrowserEvent(event);
  }

  protected void onLoad() {
    super.onLoad();
    addStyleName("red-box-draggable-panel");
  }
}
