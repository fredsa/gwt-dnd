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

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragController;

class DragStartSensitivityBehaviorPanel extends BehaviorPanel {
  private final DragController dragController;
  private TextBox textBox;

  public DragStartSensitivityBehaviorPanel(final DragController dragController) {
    super("Drag Start Sensitivity", "getBehaviorDragStartSensitivity() / setBehaviorDragStartSensitivity(int)");
    this.dragController = dragController;

    HorizontalPanel panel = new HorizontalPanel();
    panel.setSpacing(5);
    Label label = new Label("Number of Pixels");
    textBox = new TextBox();
    textBox.setWidth("3em");
    panel.add(label);
    panel.add(textBox);
    panel.setTitle("DragController#setBehaviorDragStartSensitivity(int)");
    add(panel);

    textBox.setText("" + dragController.getBehaviorDragStartSensitivity());

    textBox.addKeyboardListener(new KeyboardListenerAdapter() {
      public void onKeyUp(Widget sender, char keyCode, int modifiers) {
        fix();
      }
    });
    textBox.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        fix();
        textBox.selectAll();
      }
    });
    textBox.addChangeListener(new ChangeListener() {
      public void onChange(Widget sender) {
        fix();
      }
    });
  }

  private void fix() {
    try {
      dragController.setBehaviorDragStartSensitivity(Integer.parseInt(textBox.getText()));
    } catch (NumberFormatException ex) {
      // ignore
    }
  }
}
