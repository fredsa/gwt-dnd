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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RadioButton;

import com.allen_sauer.gwt.dnd.client.PickupDragController;

class DragProxyBehaviorPanel extends BehaviorPanel {

  public DragProxyBehaviorPanel(final PickupDragController dragController) {
    super("Drag Proxy Behavior", "getBehaviorDragProxy() / setBehaviorDragProxy(boolean)");

    final RadioButton classicButton = newButton("Widget is dragged",
        "PickupDragController#setBehaviorDragProxy(false)");
    final RadioButton proxyButton = newButton("Use drag proxies",
        "PickupDragController#setBehaviorDragProxy(true)");

    add(classicButton);
    add(proxyButton);

    if (dragController.getBehaviorDragProxy()) {
      proxyButton.setValue(true);
    } else {
      classicButton.setValue(true);
    }

    ClickHandler handler = new ClickHandler() {
      public void onClick(ClickEvent event) {
        dragController.setBehaviorDragProxy(proxyButton.getValue());
      }
    };

    classicButton.addClickHandler(handler);
    proxyButton.addClickHandler(handler);
  }
}
