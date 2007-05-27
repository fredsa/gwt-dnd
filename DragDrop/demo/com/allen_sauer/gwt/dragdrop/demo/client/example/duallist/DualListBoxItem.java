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
package com.allen_sauer.gwt.dragdrop.demo.client.example.duallist;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class DualListBoxItem extends Composite {

  private static final String STYLENAME_DEMO_DUAL_LIST_ITEM_HAS_CONTENT = "demo-dual-list-item-has-content";
  private static final String STYLENAME_DEMO_DUAL_LIST_ITEM = "demo-dual-list-item";
  private static final String STYLENAME_DEMO_DUAL_LIST_ITEM_SELECTED = "demo-dual-list-item-selected";

  private static final String EMPTY_HTML = "&nbsp;";
  private boolean selected = false;
  private FocusPanel focusPanel = new FocusPanel();
  
  {
    initWidget(focusPanel);
    addStyleName(STYLENAME_DEMO_DUAL_LIST_ITEM);
    focusPanel.setTabIndex(-1);
  }

  public DualListBoxItem() {
    setWrappedWidget(null);
  }

  public DualListBoxItem(Widget widget) {
    setWrappedWidget(widget);
  }

  public void addClickListener(ClickListener listener) {
    focusPanel.addClickListener(listener);
  }

  public DualListBoxItem cloneItem() {
    HTML html = new HTML(DOM.getInnerHTML(getWidget().getElement()));
    DualListBoxItem clone = new DualListBoxItem(html);
    return clone;
  }

  public Widget getWrappedWidget() {
    return focusPanel.getWidget();
  }

  public boolean isSelected() {
    return this.selected;
  }

  public void removeClickListener(ClickListener listener) {
    focusPanel.removeClickListener(listener);
  }

  public void removeWrappedWidget() {
    focusPanel.setWidget(emptyHtml());
    removeStyleName(STYLENAME_DEMO_DUAL_LIST_ITEM_HAS_CONTENT);
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
    if (selected) {
      addStyleName(STYLENAME_DEMO_DUAL_LIST_ITEM_SELECTED);
    } else {
      removeStyleName(STYLENAME_DEMO_DUAL_LIST_ITEM_SELECTED);
    }
  }

  public void setWrappedWidget(Widget widget) {
    if (widget == null) {
      removeWrappedWidget();
    } else {
      focusPanel.setWidget(widget);
      addStyleName(STYLENAME_DEMO_DUAL_LIST_ITEM_HAS_CONTENT);
    }
  }

  private HTML emptyHtml() {
    return new HTML(EMPTY_HTML);
  }
}
