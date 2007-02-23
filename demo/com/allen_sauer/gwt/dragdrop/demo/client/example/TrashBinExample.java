package com.allen_sauer.gwt.dragdrop.demo.client.example;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.allen_sauer.gwt.dragdrop.demo.client.Bin;
import com.allen_sauer.gwt.dragdrop.demo.client.TrashBin;
import com.allen_sauer.gwt.dragdrop.demo.client.TrashBinDropController;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.SimpleDropController} example.
 */
public class TrashBinExample extends Example {

  AbsolutePositionDropController dropController;

  public TrashBinExample(DragController dragController) {
    super(dragController);
    AbsolutePanel containingPanel = new AbsolutePanel();
    containingPanel.addStyleName(STYLE_NOT_ENGAGABLE);
    containingPanel.setPixelSize(500, 200);
    setWidget(containingPanel);

    Bin trashBin = new TrashBin(120, 120);
    containingPanel.add(trashBin, 30, 30);

    Bin closedBin = new Bin(120, 120);
    containingPanel.add(closedBin, 350, 30);

    DropController openTrashBinDropController = new TrashBinDropController(trashBin);
    dragController.registerDropController(openTrashBinDropController);

    DropController closedTrashBinDropController = new TrashBinDropController(closedBin);
    dragController.registerDropController(closedTrashBinDropController);

    dropController = new AbsolutePositionDropController(containingPanel);
    dragController.registerDropController(dropController);
  }

  public String getDescription() {
    return "Classic drop target which simply recognizes when a draggable widget is dropped on it.";
  }

  public Class getDropControllerClass() {
    return TrashBinDropController.class;
  }

  protected void onLoad() {
    super.onLoad();
    dropController.drop(createDraggable(), 200, 20);
    dropController.drop(createDraggable(), 240, 50);
    dropController.drop(createDraggable(), 190, 100);
  }
}
