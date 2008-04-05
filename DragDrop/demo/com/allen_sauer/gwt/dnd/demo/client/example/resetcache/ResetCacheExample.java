/*
 * Copyright 2008 Fred Sauer
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
package com.allen_sauer.gwt.dnd.demo.client.example.resetcache;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;

public final class ResetCacheExample extends Example {

  private static final String CSS_DEMO_CACHE_EXAMPLE = "demo-ResetCacheExample";

  private static final String CSS_DEMO_CACHE_EXAMPLE_DRAGGABLE = "demo-ResetCacheExample-draggable";

  private static final String CSS_DEMO_CACHE_EXAMPLE_TAB_PANEL = "demo-ResetCacheExample-TabPanel";

  private AbsolutePositionDropController containerDropController;

  public ResetCacheExample(PickupDragController dragController) {
    super(dragController);
    addStyleName(CSS_DEMO_CACHE_EXAMPLE);

    // some colors to go with each tab
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
      TabSelectingDropController tabSelectingDropController = new TabSelectingDropController(
          tabWidget, tabPanel, i);
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

      // create a drop controller for the containing panel
      containerDropController = new AbsolutePositionDropController(containingPanel);
      dragController.registerDropController(containerDropController);
    }
    tabPanel.selectTab(0);

    // create a drop controller for the containing panel
    containerDropController = new AbsolutePositionDropController(containingPanel);
    dragController.registerDropController(containerDropController);
  }

  @Override
  public String getDescription() {
    return "Demonstrate a use case for <code>DragController.resetCache()</code> due to drop target changes while dragging.";
  }

  @Override
  public Class<?>[] getInvolvedClasses() {
    return new Class[] {
        ResetCacheExample.class, TabSelectingDropController.class,
        AbsolutePositionDropController.class,};
  }
}
