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

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RadioButton;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;

class BidiBehaviorPanel extends BehaviorPanel {

  public BidiBehaviorPanel(final DragController dragController) {
    super("Bidi", "Force rtl/ltr text direction");

    final RadioButton ltrButton = newButton("Left to right", "direction: ltr");
    final RadioButton rtlButton = newButton("Right to left", "direction: rtl");

    add(ltrButton);
    add(rtlButton);

    if ("rtl".equals(DOMUtil.getEffectiveStyle(Document.get().getBody(), "direction"))) {
      rtlButton.setValue(true);
    } else {
      ltrButton.setValue(true);
    }

    ClickHandler handler = new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        BodyElement body = Document.get().getBody();
        if (rtlButton.getValue()) {
          body.addClassName("rtl");
          body.removeClassName("ltr");
        } else {
          body.addClassName("ltr");
          body.removeClassName("rtl");
        }
      }
    };

    ltrButton.addClickHandler(handler);
    rtlButton.addClickHandler(handler);
  }
}
