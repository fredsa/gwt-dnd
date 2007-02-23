package com.allen_sauer.gwt.dragdrop.demo.client.example;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.demo.client.RedBoxDraggableWidget;

/**
 * Abstract class representing a drag-and-drop example.
 */
public abstract class Example extends SimplePanel {

  protected static final String STYLE_NOT_ENGAGABLE = "dragdrop-not-engagable";

  private static final String STYLE_DEMO_EXAMPLE_PANEL = "demo-example-panel";

  private DragController dragController;

  public Example(DragController dragController) {
    this.dragController = dragController;
  }

  public abstract String getDescription();

  public DragController getDragController() {
    return dragController;
  }

  public abstract Class getDropControllerClass();

  protected Widget createDraggable() {
    Widget redBox = new RedBoxDraggableWidget();
    dragController.makeDraggable(redBox);
    return redBox;
  }

  protected void onLoad() {
    super.onLoad();
    addStyleName(STYLE_DEMO_EXAMPLE_PANEL);
  }
}