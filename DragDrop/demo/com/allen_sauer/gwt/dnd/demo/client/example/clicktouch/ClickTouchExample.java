package com.allen_sauer.gwt.dnd.demo.client.example.clicktouch;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;

public class ClickTouchExample extends Example {
  private AbsolutePositionDropController absolutePositionDropController;
  private DemoDragHandler demoDragHandler;

  public ClickTouchExample(PickupDragController dragController, DemoDragHandler demoDragHandler) {
    super(dragController);
    this.demoDragHandler = demoDragHandler;

    // use the drop target as this composite's widget
    AbsolutePanel positioningDropTarget = new AbsolutePanel();
    positioningDropTarget.setPixelSize(500, 300);
    setWidget(positioningDropTarget);

    positioningDropTarget.add(
        new HTML("\u21fd Note, you must first set <b>Drag Sentitivity &gt; 0</b>"), 5, 5);

    // instantiate our drop controller
    absolutePositionDropController = new AbsolutePositionDropController(positioningDropTarget);
    dragController.registerDropController(absolutePositionDropController);
  }

  @Override
  public String getDescription() {
    return "Example showing how click events and touch events can be recognized in draggables.";
  }

  @Override
  public Class<?>[] getInvolvedClasses() {
    return new Class[] {ClickTouchExample.class, ClickTouchButton.class,};
  }

  private Widget createDraggableButton() {
    ClickTouchButton button = new ClickTouchButton(demoDragHandler);
    getDragController().makeDraggable(button);
    return button;
  }

  @Override
  protected void onInitialLoad() {
    absolutePositionDropController.drop(createDraggableButton(), 60, 10);
    absolutePositionDropController.drop(createDraggableButton(), 10, 90);
    absolutePositionDropController.drop(createDraggableButton(), 160, 160);
  }
}
