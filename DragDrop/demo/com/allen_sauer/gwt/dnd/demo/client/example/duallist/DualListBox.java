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
package com.allen_sauer.gwt.dnd.demo.client.example.duallist;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragController;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Example of two lists side by side for {@link DualListExample}.
 */
public class DualListBox extends AbsolutePanel {

  private static final String CSS_DEMO_DUAL_LIST_EXAMPLE_CENTER = "demo-DualListExample-center";

  private static final int LIST_SIZE = 10;

  private Button allLeft;

  private Button allRight;

  private ListBoxDragController dragController;

  private MouseListBox left;

  private Button oneLeft;

  private Button oneRight;

  private MouseListBox right;

  public DualListBox(int visibleItems, String width) {
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    add(horizontalPanel);
    horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.addStyleName(CSS_DEMO_DUAL_LIST_EXAMPLE_CENTER);
    verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

    dragController = new ListBoxDragController(this);
    left = new MouseListBox(dragController, LIST_SIZE);
    right = new MouseListBox(dragController, LIST_SIZE);

    left.setWidth(width);
    right.setWidth(width);

    horizontalPanel.add(left);
    horizontalPanel.add(verticalPanel);
    horizontalPanel.add(right);

    oneRight = new Button("&gt;");
    oneLeft = new Button("&lt;");
    allRight = new Button("&gt;&gt;");
    allLeft = new Button("&lt;&lt;");
    verticalPanel.add(oneRight);
    verticalPanel.add(oneLeft);
    verticalPanel.add(new HTML("&nbsp;"));
    verticalPanel.add(allRight);
    verticalPanel.add(allLeft);

    allRight.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        moveItems(left, right, false);
      }
    });

    allLeft.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        moveItems(right, left, false);
      }
    });

    oneRight.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        moveItems(left, right, true);
      }
    });

    oneLeft.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        moveItems(right, left, true);
      }
    });

    ListBoxDropController leftDropController = new ListBoxDropController(left);
    ListBoxDropController rightDropController = new ListBoxDropController(right);
    dragController.registerDropController(leftDropController);
    dragController.registerDropController(rightDropController);
  }

  public void addLeft(String string) {
    left.add(string);
  }

  /**
   * Adds an widget to the left list box.
   * 
   * @param widget the text of the item to be added
   */
  public void addLeft(Widget widget) {
    left.add(widget);
  }

  public DragController getDragController() {
    return dragController;
  }

  protected void moveItems(MouseListBox from, MouseListBox to, boolean justSelectedItems) {
    ArrayList widgetList = justSelectedItems ? dragController.getSelectedWidgets(from)
        : from.widgetList();
    for (Iterator iterator = widgetList.iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();
      // TODO let widget.removeFromParent() take care of from.remove()
      from.remove(widget);
      to.add(widget);
    }
  }
}
