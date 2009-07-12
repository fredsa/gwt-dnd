/*
 * Copyright 2009 Fred Sauer
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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RadioButton;

import com.allen_sauer.gwt.dnd.client.PickupDragController;

class MultipleSelectionBehaviorPanel extends BehaviorPanel {

  public MultipleSelectionBehaviorPanel(final PickupDragController dragController) {
    super("Multiple Selections",
        "getBehaviorMultipleSelection() / setBehaviorMultipleSelection(boolean)");

    final RadioButton constrainedButton = newButton(
        "Allow via <code>CTRL</code>/<code>META</code>-click",
        "DragController#setBehaviorMultipleSelection(true)");
    final RadioButton unconstrainedButton = newButton("Single widget drag only",
        "DragController#setBehaviorMultipleSelection(false)");

    add(constrainedButton);
    add(unconstrainedButton);

    if (dragController.getBehaviorMultipleSelection()) {
      constrainedButton.setValue(true);
    } else {
      unconstrainedButton.setValue(true);
    }

    ClickHandler listener = new ClickHandler() {
      public void onClick(ClickEvent event) {
        dragController.setBehaviorMultipleSelection(constrainedButton.getValue());
      }
    };

    constrainedButton.addClickHandler(listener);
    unconstrainedButton.addClickHandler(listener);
  }
}
