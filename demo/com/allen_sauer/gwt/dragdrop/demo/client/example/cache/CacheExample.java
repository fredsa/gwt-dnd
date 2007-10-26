package com.allen_sauer.gwt.dragdrop.demo.client.example.cache;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

final public class CacheExample extends Example {
  private static final String CSS_DEMO_CACHE_EXAMPLE = "demo-CacheExample";

  private static final String CSS_DEMO_CACHE_EXAMPLE_DRAGGABLE = "demo-CacheExample-draggable";

  private static final String CSS_DEMO_CACHE_EXAMPLE_TAB_PANEL = "demo-CacheExample-TabPanel";

  private AbsolutePositionDropController containerDropController;

  public CacheExample(DragController dragController) {
    super(dragController);
    addStyleName(CSS_DEMO_CACHE_EXAMPLE);

    String[] colors = {"#AAAAFF", "#AAFFAA", "#FFAAAA", "#FFFFAA", "#FFAAFF", "#AAFFFF",};
    // use the containing panel as this composite's widget
    AbsolutePanel containingPanel = new AbsolutePanel();
    containingPanel.setPixelSize(600, 300);
    setWidget(containingPanel);

    // create a tab panel and populate with a few tabs
    TabPanel tabPanel = new TabPanel();
    tabPanel.addStyleName(CSS_DEMO_CACHE_EXAMPLE_TAB_PANEL);
    tabPanel.setPixelSize(300, 200);
    containingPanel.add(tabPanel, 40, 20);

    for (int i = 0; i < colors.length; i++) {
      // create a simple panel for the tab content
      AbsolutePanel contentPanel = new AbsolutePanel();
      contentPanel.setHeight("200px");
      DOM.setStyleAttribute(contentPanel.getElement(), "backgroundColor", colors[i]);

      // create a tab widget
      HTML tabWidget = new HTML("Tab #" + (i + 1));
      tabWidget.setWordWrap(false);
      DOM.setStyleAttribute(tabWidget.getElement(), "backgroundColor", colors[i]);
      tabPanel.add(contentPanel, tabWidget);

      // add drop controller to allow automatic tab selection and dropping on tab
      TabSelectingDropController tabSelectingDropController = new TabSelectingDropController(tabWidget, tabPanel, i);
      dragController.registerDropController(tabSelectingDropController);

      // create a sample draggable
      Widget draggableLabel = new Label("Drag me to another tab");
      DOM.setStyleAttribute(draggableLabel.getElement(), "backgroundColor", colors[i]);
      draggableLabel.addStyleName(CSS_DEMO_CACHE_EXAMPLE_DRAGGABLE);

      // make label draggable
      dragController.makeDraggable(draggableLabel);

      // determine random location within target panel
      int left = Random.nextInt(200);
      int top = Random.nextInt(150);
      contentPanel.add(draggableLabel, left, top);

      // create a drop controller for the simple panel
      AbsolutePositionDropController dropController = new AbsolutePositionDropController(contentPanel);
      dragController.registerDropController(dropController);
    }
    tabPanel.selectTab(0);

    // create a drop controller for the containing panel
    containerDropController = new AbsolutePositionDropController(containingPanel);
    dragController.registerDropController(containerDropController);
  }

  public String getDescription() {
    return "Demonstrate a use case for DragController.resetCache() due to drop target changes while dragging.";
  }

  public Class[] getInvolvedClasses() {
    return new Class[] {CacheExample.class, TabSelectingDropController.class, AbsolutePositionDropController.class,};
  }
}
