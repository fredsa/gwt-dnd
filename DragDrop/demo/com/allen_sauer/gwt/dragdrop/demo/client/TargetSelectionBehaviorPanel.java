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

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragController.TargetSelectionMethod;

class TargetSelectionBehaviorPanel extends BehaviorPanel {
  public TargetSelectionBehaviorPanel(final DragController dragController) {
    super("Target Selection Method", "getBehaviorTargetSelection() / setBehaviorTargetSelection(TargetSelectionMethod)");

    final RadioButton mousePositionRadioButton = newButton("Mouse position",
        "DragController#setBehaviorTargetSelection(TargetSelectionMethod.MOUSE_POSITION)");
    final RadioButton widgetCenterRadioButton = newButton("Widget center",
        "DragController#setBehaviorTargetSelection(TargetSelectionMethod.WIDGET_CENTER)");

    add(mousePositionRadioButton);
    add(widgetCenterRadioButton);

    TargetSelectionMethod targetSelectionMethod = dragController.getBehaviorTargetSelection();
    if (targetSelectionMethod == TargetSelectionMethod.MOUSE_POSITION) {
      mousePositionRadioButton.setChecked(true);
    } else if (targetSelectionMethod == TargetSelectionMethod.WIDGET_CENTER) {
      widgetCenterRadioButton.setChecked(true);
    } else {
      throw new IllegalStateException();
    }

    ClickListener targetSelectionClickListener = new ClickListener() {
      public void onClick(Widget sender) {
        if (mousePositionRadioButton.isChecked()) {
          dragController.setBehaviorTargetSelection(TargetSelectionMethod.MOUSE_POSITION);
        } else if (widgetCenterRadioButton.isChecked()) {
          dragController.setBehaviorTargetSelection(TargetSelectionMethod.WIDGET_CENTER);
        } else {
          throw new IllegalStateException();
        }
      }
    };

    mousePositionRadioButton.addClickListener(targetSelectionClickListener);
    widgetCenterRadioButton.addClickListener(targetSelectionClickListener);
  }
}
