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
package com.allen_sauer.gwt.dragdrop.demo.client.example.draghandle;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dragdrop.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

public class DragHandleExample extends Example {

  private static final String CSS_DEMO_DRAG_HANDLE_EXAMPLE_HEADER = "demo-DragHandleExample-header";
  private static final String CSS_DEMO_DRAG_HANDLE_EXAMPLE_PANEL = "demo-DragHandleExample-panel";
  private static final String CSS_DEMO_DRAG_HANDLE_EXAMPLE_TEXTAREA = "demo-DragHandleExample-textarea";
  private DragController dragController;

  public DragHandleExample(DemoDragHandler demoDragHandler) {
    final AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(500, 200);

    // title bar
    Label header = new Label("Title/Header (Drag Handle)");
    header.addStyleName(CSS_DEMO_DRAG_HANDLE_EXAMPLE_HEADER);

    // some text
    HTML content = new HTML("This is a <code>VerticalPanel</code> which can be dragged by its header,"
        + "i.e. the <code>Label</code> which makes up the top cell in the panel.");

    // an editable text area
    final TextArea textArea = new TextArea();
    textArea.addStyleName(CSS_DEMO_DRAG_HANDLE_EXAMPLE_TEXTAREA);
    //    textArea.setSize("20em", "5em");
    textArea.setText("You can click in this TextArea to get focus without causing the panel to be dragged.");

    // a button
    Button button = new Button("Click me");
    button.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        textArea.setText(textArea.getText() + " Click!");
      }
    });

    // a panel to hold all our widgets
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.setSpacing(2);
    verticalPanel.addStyleName(CSS_DEMO_DRAG_HANDLE_EXAMPLE_PANEL);
    verticalPanel.add(header);
    verticalPanel.add(content);
    verticalPanel.add(textArea);
    verticalPanel.add(button);

    dragController = new PickupDragController(boundaryPanel, true);
    dragController.addDragHandler(demoDragHandler);
    AbsolutePositionDropController dropController = new AbsolutePositionDropController(boundaryPanel);
    dragController.registerDropController(dropController);
    dragController.makeDraggable(verticalPanel, header);

    setWidget(boundaryPanel);
    boundaryPanel.add(verticalPanel, 20, 20);
  }

  public Class getControllerClass() {
    return BoundaryDropController.class;
  }

  public String getDescription() {
    return "Demonstrate how a draggable widget can be dragged by a child drag handle widget<br>"
        + "(currently only implements the 'classic' drag behavior).";
  }
}
