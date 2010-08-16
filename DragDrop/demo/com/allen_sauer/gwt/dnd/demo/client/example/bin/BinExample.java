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
package com.allen_sauer.gwt.dnd.demo.client.example.bin;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;

/**
 * {@link com.allen_sauer.gwt.dnd.client.drop.SimpleDropController} example.
 */
public final class BinExample extends Example {

  private static final String CSS_DEMO_BIN_EXAMPLE = "demo-BinExample";

  private AbsolutePositionDropController dropController;

  /**
   * Constructor.
   * @param dragController the drag controller to use
   */
  public BinExample(PickupDragController dragController) {
    super(dragController);
    addStyleName(CSS_DEMO_BIN_EXAMPLE);

    // use the containing panel as this composite's widget
    AbsolutePanel containingPanel = new AbsolutePanel();
    containingPanel.setPixelSize(500, 200);
    setWidget(containingPanel);

    // create a trash bin
    Bin trashBin = new TrashBin(120, 120);
    containingPanel.add(trashBin, 30, 30);

    // create a bin that won't accept trash
    Bin closedBin = new Bin(120, 120);
    containingPanel.add(closedBin, 350, 30);

    // add drop controller for trash bin
    DropController openTrashBinDropController = new BinDropController(trashBin);
    dragController.registerDropController(openTrashBinDropController);

    // add drop controller for closed bin
    DropController closedTrashBinDropController = new BinDropController(closedBin);
    dragController.registerDropController(closedTrashBinDropController);

    // create a drop controller for the containing panel
    dropController = new AbsolutePositionDropController(containingPanel);
    dragController.registerDropController(dropController);

    containingPanel.add(createDraggable(), 200, 20);
    containingPanel.add(createDraggable(), 240, 50);
    containingPanel.add(createDraggable(), 190, 100);
  }

  @Override
  public String getDescription() {
    return "Classic drop target which simply recognizes when a draggable widget is dropped on it.";
  }

  @Override
  public Class<?>[] getInvolvedClasses() {
    return new Class[] {
        BinExample.class, BinDropController.class, AbsolutePositionDropController.class,
        TrashBin.class, Bin.class,};
  }
}
