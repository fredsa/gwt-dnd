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

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.PickupDragController;

class DragProxyBehaviorPanel extends BehaviorPanel {
  public DragProxyBehaviorPanel(final PickupDragController dragController) {
    super("Drag Proxy Behavior", "getBehaviorDragProxy() / setBehaviorDragProxy(boolean)");

    final RadioButton classicButton = newButton("Widget is dragged", "PickupDragController#setBehaviorDragProxy(false)");
    final RadioButton proxyButton = newButton("Use a drag proxy", "PickupDragController#setBehaviorDragProxy(true)");

    add(classicButton);
    add(proxyButton);

    if (dragController.getBehaviorDragProxy()) {
      proxyButton.setChecked(true);
    } else {
      classicButton.setChecked(true);
    }

    ClickListener listener = new ClickListener() {
      public void onClick(Widget sender) {
        dragController.setBehaviorDragProxy(proxyButton.isChecked());
      }
    };

    classicButton.addClickListener(listener);
    proxyButton.addClickListener(listener);
  }
}
