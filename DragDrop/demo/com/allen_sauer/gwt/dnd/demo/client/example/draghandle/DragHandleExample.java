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
package com.allen_sauer.gwt.dnd.demo.client.example.draghandle;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;

/**
 * Example illustrating drag handles, so that widgets inside draggable can still
 * interact normally with the mouse.
 */
public class DragHandleExample extends Example {
  private static final String CSS_DEMO_DRAG_HANDLE_EXAMPLE = "demo-DragHandleExample";
  private static final String CSS_DEMO_DRAG_HANDLE_EXAMPLE_HEADER = "demo-DragHandleExample-header";
  private static final String CSS_DEMO_DRAG_HANDLE_EXAMPLE_PANEL = "demo-DragHandleExample-panel";
  private static final String CSS_DEMO_DRAG_HANDLE_EXAMPLE_TEXTAREA = "demo-DragHandleExample-textarea";
  private PickupDragController dragController;

  public DragHandleExample(DemoDragHandler demoDragHandler) {
    addStyleName(CSS_DEMO_DRAG_HANDLE_EXAMPLE);

    // use the boundary panel as this composite's widget
    final AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(600, 400);
    setWidget(boundaryPanel);

    // create the title bar
    Label header = new Label("Title/Header (Drag Handle)");
    header.addStyleName(CSS_DEMO_DRAG_HANDLE_EXAMPLE_HEADER);

    // add some text
    HTML content = new HTML("This is a <code>VerticalPanel</code> which can be dragged by its header,"
        + " i.e. the title/header widget.");

    // add an editable text area
    final TextArea textArea = new TextArea();
    textArea.addStyleName(CSS_DEMO_DRAG_HANDLE_EXAMPLE_TEXTAREA);
    // textArea.setSize("20em", "5em");
    textArea.setText("You can click in this TextArea to get focus without causing the panel to be dragged.");

    // add a clickable button
    Button button = new Button("Click me");
    button.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        textArea.setText(textArea.getText() + " Click!");
      }
    });

    // create a panel to hold all our widgets
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.setSpacing(2);
    verticalPanel.addStyleName(CSS_DEMO_DRAG_HANDLE_EXAMPLE_PANEL);
    verticalPanel.add(header);
    verticalPanel.add(content);
    verticalPanel.add(textArea);
    verticalPanel.add(button);
    boundaryPanel.add(verticalPanel, 20, 20);

    // instantiate our drag controller
    dragController = new PickupDragController(boundaryPanel, true);
    dragController.addDragHandler(demoDragHandler);
    dragController.setBehaviorConstrainedToBoundaryPanel(true);
    dragController.setBehaviorMultipleSelection(false);

    // instantiate our drop controller
    AbsolutePositionDropController dropController = new AbsolutePositionDropController(boundaryPanel);
    dragController.registerDropController(dropController);

    // make the panel draggable by its header
    dragController.makeDraggable(verticalPanel, header);
  }

  public String getDescription() {
    return "Demonstrate how a draggable widget can be dragged by a child drag handle widget.";
  }

  public Class[] getInvolvedClasses() {
    return new Class[] {DragHandleExample.class, PickupDragController.class, AbsolutePositionDropController.class,};
  }
}
