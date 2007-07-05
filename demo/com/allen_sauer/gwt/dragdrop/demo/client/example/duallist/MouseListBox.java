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
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MouseListBox extends FocusPanel {

  private static class ItemClickListener implements ClickListener {

    public void onClick(Widget sender) {
      DualListBoxItem item = (DualListBoxItem) sender.getParent();
      item.setSelected(!item.isSelected());
    }
  }

  private static ItemClickListener itemClickListener = new ItemClickListener();
  private static final String CSS_DEMO_MOUSELISTBOX = "demo-MouseListBox";

  private VerticalPanel verticalPanel = new VerticalPanel();
  private int widgetCount = 0;

  public MouseListBox(int size) {
    addStyleName(CSS_DEMO_MOUSELISTBOX);
    super.add(verticalPanel);
    for (int i = 0; i < size; i++) {
      DualListBoxItem item = new DualListBoxItem();
      verticalPanel.add(item);
    }
  }

  public void add(String text) {
    add(new Label(text));
  }

  public void add(Widget widget) {
    DualListBoxItem item = (DualListBoxItem) verticalPanel.getWidget(widgetCount);
    item.setWrappedWidget(widget);
    item.addClickListener(itemClickListener);
    widgetCount++;
  }

  public Widget getClonedWidget(int index) {
    HTML html = new HTML();
    Widget widget = getWidget(index);
    html.setHTML(DOM.getInnerHTML(widget.getElement()));
    return html;
  }

  public int getSelectedWidgetCount() {
    int count = 0;
    for (int i = 0; i < widgetCount; i++) {
      if (isItemSelected(i)) {
        count++;
      }
    }
    return count;
  }

  public Widget getWidget(int index) {
    checkIndexInRange(index);
    DualListBoxItem item = (DualListBoxItem) verticalPanel.getWidget(index);
    return item.getWrappedWidget();
  }

  public int getWidgetCount() {
    return widgetCount;
  }

  public boolean isItemSelected(int index) {
    DualListBoxItem item = (DualListBoxItem) verticalPanel.getWidget(index);
    return item.isSelected();
  }

  public void remove(int index) {
    checkIndexInRange(index);
    DualListBoxItem item = (DualListBoxItem) verticalPanel.getWidget(index);
    verticalPanel.remove(index);
    item.removeWrappedWidget();
    item.removeClickListener(itemClickListener);
    item.setSelected(false);
    verticalPanel.add(item);
    widgetCount--;
  }

  public void setHeight(String height) {
    super.setHeight(height);
    verticalPanel.setHeight(height);
  }

  public void setItemSelected(int index, boolean selected) {
    checkIndexInRange(index);
    DualListBoxItem item = (DualListBoxItem) verticalPanel.getWidget(index);
    item.setSelected(selected);
  }

  public void setPixelSize(int width, int height) {
    super.setPixelSize(width, height);
    verticalPanel.setPixelSize(width, height);
  }

  public void setWidth(String width) {
    super.setWidth(width);
    verticalPanel.setWidth(width);
  }

  private void checkIndexInRange(int index) {
    if (index < 0 || index >= widgetCount) {
      throw new IllegalArgumentException();
    }
  }
}
