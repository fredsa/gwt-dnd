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
package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.TextArea;

import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.DragHandler;
import com.allen_sauer.gwt.dragdrop.client.DragStartEvent;
import com.allen_sauer.gwt.dragdrop.client.VetoDragException;

public final class DemoDragHandler implements DragHandler {
  private final TextArea eventTextArea;

  public DemoDragHandler(TextArea eventTextArea) {
    this.eventTextArea = eventTextArea;
  }

  public void onDragEnd(DragEndEvent event) {
    log("onDragEnd: " + event);
  }

  public void onDragStart(DragStartEvent event) {
    clear(eventTextArea);
    log("onDragStart: " + event);
  }

  public void onPreviewDragEnd(DragEndEvent event) throws VetoDragException {
    log("onPreviewDragEnd: " + event);
  }

  public void onPreviewDragStart(DragStartEvent event) throws VetoDragException {
    log("onPreviewDragStart: " + event);
  }

  private void clear(final TextArea eventTextArea) {
    eventTextArea.setText("");
  }

  private void log(String text) {
    eventTextArea.setText(eventTextArea.getText() + (eventTextArea.getText().length() == 0 ? "" : "\n") + text);
    eventTextArea.setCursorPos(eventTextArea.getText().length());
  }
}