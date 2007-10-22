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
package com.allen_sauer.gwt.dragdrop.demo.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MultiRowTabPanel extends Composite {
  private static final String CSS_DEMO_MULTI_ROW_TAB_PANEL_BOTTOM = "demo-MultiRowTabPanel-bottom";
  private static final String CSS_DEMO_MULTI_ROW_TAB_PANEL_FIRST = "demo-MultiRowTabPanel-first";
  private static final String CSS_DEMO_MULTI_ROW_TAB_PANEL_LAST = "demo-MultiRowTabPanel-last";
  private DeckPanel masterDeckPanel;
  private final int rows;
  private int selectedRow = -1;
  private StylableVerticalPanel tabBarsVerticalPanel;

  public MultiRowTabPanel(int rows) {
    this.rows = rows;
    VerticalPanel containerPanel = new VerticalPanel();
    initWidget(containerPanel);

    tabBarsVerticalPanel = new StylableVerticalPanel();
    tabBarsVerticalPanel.setWidth("100%");
    masterDeckPanel = new DeckPanel();
    masterDeckPanel.addStyleName(CSS_DEMO_MULTI_ROW_TAB_PANEL_BOTTOM);
    containerPanel.add(tabBarsVerticalPanel);
    containerPanel.add(masterDeckPanel);

    for (int i = 0; i < rows; i++) {
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
    }
    setTabBarFirstLastStyleNames();

    for (int i = 0; i < rows; i++) {
      DeckPanel deckPanel = new DeckPanel();
      masterDeckPanel.add(deckPanel);
    }
  }

  public void add(Widget widget, String tabText, int row) {
    DeckPanel deckPanel = (DeckPanel) masterDeckPanel.getWidget(row);
    insert(widget, tabText, row, deckPanel.getWidgetCount());
  }

  public void insert(Widget widget, String tabText, int row, int beforeIndex) {
    TabBar tabBar = (TabBar) tabBarsVerticalPanel.getWidget(row);
    DeckPanel deckPanel = (DeckPanel) masterDeckPanel.getWidget(row);

    tabBar.insertTab(tabText, true, beforeIndex);
    deckPanel.insert(widget, beforeIndex);

    masterDeckPanel.showWidget(row);
  }

  public void selectTab(int row, int tabIndex) {
    TabBar tabBar = (TabBar) tabBarsVerticalPanel.getWidget(row);
    tabBar.selectTab(tabIndex);
  }

  private void moveSelectedRowToBottom() {
    tabBarsVerticalPanel.add(tabBarsVerticalPanel.getWidget(selectedRow));
    setTabBarFirstLastStyleNames();
    masterDeckPanel.add(masterDeckPanel.getWidget(selectedRow));
    selectedRow = rows - 1;
    masterDeckPanel.showWidget(selectedRow);
  }

  private void setTabBarFirstLastStyleNames() {
    tabBarsVerticalPanel.setCellStyleName(tabBarsVerticalPanel.getWidget(0), CSS_DEMO_MULTI_ROW_TAB_PANEL_FIRST);
    tabBarsVerticalPanel.setCellStyleName(tabBarsVerticalPanel.getWidget(rows - 1), CSS_DEMO_MULTI_ROW_TAB_PANEL_LAST);
  }

  private void whenTabSelected(int row, int tabIndex) {
    if (tabIndex == -1) {
      return;
    }
    DeckPanel deckPanel = (DeckPanel) masterDeckPanel.getWidget(row);
    deckPanel.showWidget(tabIndex);
    if (row != selectedRow) {
      selectedRow = row;
      for (int i = 0; i < rows; i++) {
        if (i != row) {
          TabBar tabBar = (TabBar) tabBarsVerticalPanel.getWidget(i);
          tabBar.selectTab(-1);
        }
      }
      moveSelectedRowToBottom();
    }
  }
}
