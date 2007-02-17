package com.allen_sauer.gwt.dragdrop.demo.client.example;

import com.google.gwt.user.client.ui.Label;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController} example.
 */
public class IndexedExample extends Example {

  private static final String STYLE_DEMO_LABEL = "demo-flow-label";

  private IndexedDropController indexedDropController;

  public IndexedExample(DragController dragController) {
    super(dragController);
    IndexedFlowPanel flowPanelDropTarget = new IndexedFlowPanel();
    flowPanelDropTarget.setWidth("400px");
    setWidget(flowPanelDropTarget);
    indexedDropController = new IndexedDropController(flowPanelDropTarget);
    dragController.registerDropController(indexedDropController);
  }

  public String getDescription() {
    return "Allows drop to occur anywhere among the children of a supported <code>IndexedPanel</code>.";
  }

  public Class getDropControllerClass() {
    return IndexedDropController.class;
  }

  protected void onLoad() {
    super.onLoad();
    for (int i = 1; i <= 5; i++) {
      Label label = new Label("Draggable child #" + i);
      label.addStyleName(STYLE_DEMO_LABEL);
      getDragController().makeDraggable(label);
      indexedDropController.drop(label);
    }
    indexedDropController.drop(createDraggable());
  }

}
