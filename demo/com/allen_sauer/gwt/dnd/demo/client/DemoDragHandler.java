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
package com.allen_sauer.gwt.dnd.demo.client;

import com.google.gwt.user.client.ui.HTML;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.VetoDragException;

/**
 * Shared drag handler which display events as they are received by the various
 * drag controllers.
 */
public final class DemoDragHandler implements DragHandler {
  private static final String BLUE = "#4444BB";
  private static final String GREEN = "#44BB44";
  private static final String RED = "#BB4444";

  private final HTML eventTextArea;

  public DemoDragHandler(HTML dragHandlerHTML) {
    eventTextArea = dragHandlerHTML;
  }

  public void onDragEnd(DragEndEvent event) {
    log("onDragEnd: " + event, RED);
  }

  public void onDragStart(DragStartEvent event) {
    log("onDragStart: " + event, GREEN);
  }

  public void onPreviewDragEnd(DragEndEvent event) throws VetoDragException {
    log("<br>onPreviewDragEnd: " + event, BLUE);
  }

  public void onPreviewDragStart(DragStartEvent event) throws VetoDragException {
    clear();
    log("onPreviewDragStart: " + event, BLUE);
  }

  private void clear() {
    eventTextArea.setHTML("");
  }

  private void log(String text, String color) {
    eventTextArea.setHTML(eventTextArea.getHTML() + (eventTextArea.getHTML().length() == 0 ? "" : "<br>") + "<span style='color:"
        + color + "'>" + text + "</span>");
  }
}