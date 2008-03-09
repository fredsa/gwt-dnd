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
package com.allen_sauer.gwt.dnd.demo.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;

public class MultiRowTabPanel extends Composite {

  private static final String CSS_DEMO_MULTI_ROW_TAB_PANEL_BOTTOM = "demo-MultiRowTabPanel-bottom";

  private static final String CSS_DEMO_MULTI_ROW_TAB_PANEL_FIRST = "demo-MultiRowTabPanel-first";

  private static final String CSS_DEMO_MULTI_ROW_TAB_PANEL_LAST = "demo-MultiRowTabPanel-last";

  private DeckPanel masterDeckPanel;

  private int rows = 0;

  private int selectedRow = -1;

  private HashMap<TabBar, Integer> tabBarIndexOffsetMap = new HashMap<TabBar, Integer>();

  private StylableVerticalPanel tabBarsVerticalPanel;

  private int tabCount;

  private final int tabsPerRow;

  public MultiRowTabPanel(int tabsPerRow) {
    this.tabsPerRow = tabsPerRow;
    VerticalPanel containerPanel = new VerticalPanel();
    initWidget(containerPanel);

    tabBarsVerticalPanel = new StylableVerticalPanel();
    tabBarsVerticalPanel.setWidth("100%");
    masterDeckPanel = new DeckPanel();
    masterDeckPanel.addStyleName(CSS_DEMO_MULTI_ROW_TAB_PANEL_BOTTOM);
    containerPanel.add(tabBarsVerticalPanel);
    containerPanel.add(masterDeckPanel);
  }

  public void add(Widget widget, Label tabLabel) {
    int row = tabCount / tabsPerRow;
    while (row >= rows) {
      addRow();
    }
    tabCount++;
    masterDeckPanel.add(widget);
    TabBar tabBar = (TabBar) tabBarsVerticalPanel.getWidget(row);
    tabBar.addTab(tabLabel);
  }

  public void addTabBarStyleName(String style) {
    tabBarsVerticalPanel.addStyleName(style);
  }

  public int getTabCount() {
    return tabCount;
  }

  public void selectTab(int index) {
    // TODO Account for tab bars having been rotated out of their original position
    int row = index / tabsPerRow;
    int tabIndex = index % tabsPerRow;
    TabBar tabBar = (TabBar) tabBarsVerticalPanel.getWidget(row);
    tabBar.selectTab(tabIndex);
  }

  private void addRow() {
    TabBar tabBar = new TabBar();
    tabBarsVerticalPanel.add(tabBar);
    tabBar.addTabListener(new TabListener() {

      public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
        return true;
      }

      public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
        int row = tabBarsVerticalPanel.getWidgetIndex((TabBar) sender);
        whenTabSelected(row, tabIndex);
      }
    });
    tabBarIndexOffsetMap.put(tabBar, new Integer(tabCount));

    rows++;
    setTabBarFirstLastStyleNames();
  }

  private void rotateSelectedRowToBottom() {
    for (int i = 0; i <= selectedRow; i++) {
      tabBarsVerticalPanel.add(tabBarsVerticalPanel.getWidget(0));
    }
    setTabBarFirstLastStyleNames();
    selectedRow = rows - 1;
  }

  private void setTabBarFirstLastStyleNames() {
    tabBarsVerticalPanel.setCellStyleName(tabBarsVerticalPanel.getWidget(0),
        CSS_DEMO_MULTI_ROW_TAB_PANEL_FIRST);
    for (int i = 1; i < rows - 1; i++) {
      tabBarsVerticalPanel.setCellStyleName(tabBarsVerticalPanel.getWidget(i), "");
    }
    tabBarsVerticalPanel.setCellStyleName(tabBarsVerticalPanel.getWidget(rows - 1),
        CSS_DEMO_MULTI_ROW_TAB_PANEL_LAST);
  }

  private void whenTabSelected(int row, int tabIndex) {
    if (tabIndex == -1) {
      return;
    }
    if (row != selectedRow) {
      selectedRow = row;
      for (int i = 0; i < rows; i++) {
        if (i != row) {
          TabBar tabBar = (TabBar) tabBarsVerticalPanel.getWidget(i);
          tabBar.selectTab(-1);
        }
      }
      rotateSelectedRowToBottom();
    }
    TabBar tabBar = (TabBar) tabBarsVerticalPanel.getWidget(selectedRow);
    Integer widgetOffset = tabBarIndexOffsetMap.get(tabBar);
    masterDeckPanel.showWidget(widgetOffset.intValue() + tabIndex);
  }
}
