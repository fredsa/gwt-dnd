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

import com.allen_sauer.gwt.dnd.client.DragController;

class ConstrainedToBoundaryBehaviorPanel extends BehaviorPanel {

  public ConstrainedToBoundaryBehaviorPanel(final DragController dragController) {
    super("Drag Operations",
        "getBehaviorConstrainedToBoundaryPanel() / setBehaviorConstrainedToBoundaryPanel(boolean)");

    final RadioButton constrainedButton = newButton("Constrained by Boundary",
        "DragController#setBehaviorConstrainedToBoundaryPanel(true)");
    final RadioButton unconstrainedButton = newButton("Unconstrained",
        "DragController#setBehaviorConstrainedToBoundaryPanel(false)");

    add(constrainedButton);
    add(unconstrainedButton);

    if (dragController.getBehaviorConstrainedToBoundaryPanel()) {
      constrainedButton.setValue(true);
    } else {
      unconstrainedButton.setValue(true);
    }

    ClickHandler handler = new ClickHandler() {
      public void onClick(ClickEvent event) {
        dragController.setBehaviorConstrainedToBoundaryPanel(constrainedButton.getValue());
      }
    };

    constrainedButton.addClickHandler(handler);
    unconstrainedButton.addClickHandler(handler);
  }
}
