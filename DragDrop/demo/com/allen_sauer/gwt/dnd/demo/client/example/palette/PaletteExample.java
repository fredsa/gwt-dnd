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
package com.allen_sauer.gwt.dnd.demo.client.example.palette;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;

/**
 * Example of a widget palette that creates widget copies on demand.
 */
public final class PaletteExample extends Example {
  private static final String CSS_DEMO_PALETTE_EXAMPLE = "demo-PaletteExample";
  private PickupDragController dragController;

  public PaletteExample(DemoDragHandler demoDragHandler) {
    addStyleName(CSS_DEMO_PALETTE_EXAMPLE);

    // use the boundary panel as this composite's widget
    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(500, 300);
    setWidget(boundaryPanel);

    dragController = new PickupDragController(boundaryPanel, true);
    dragController.setBehaviorMultipleSelection(false);
    dragController.setBehaviorDragProxy(false);
    dragController.setBehaviorConstrainedToBoundaryPanel(true);
    dragController.addDragHandler(demoDragHandler);

    PalettePanel palette = new PalettePanel(dragController);
    palette.add(new PaletteWidget(new Label("New Label")));
    palette.add(new PaletteWidget(new RadioButton("name", "New Radio Button")));
    palette.add(new PaletteWidget(new CheckBox("New Check Box")));
    boundaryPanel.add(palette, 10, 10);
  }

  public String getDescription() {
    return "Example illustrating a palette of widgets which are cloned at the start of a drag.";
  }

  public Class[] getInvolvedClasses() {
    return new Class[] {PaletteExample.class, PalettePanel.class, PaletteWidget.class,};
  }
}
