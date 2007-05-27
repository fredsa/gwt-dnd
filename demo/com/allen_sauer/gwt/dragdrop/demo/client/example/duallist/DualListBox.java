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

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DualListBox extends AbsolutePanel {

  public final static int OPERATION_COPY = 1;
  public final static int OPERATION_MOVE = 2;
  private static final int LIST_SIZE = 10;
  private static final String STYLENAME_DEMO_DUAL_LIST_CENTER = "demo-dual-list-center";

  static protected void copyOrmoveItems(MouseListBox from, MouseListBox to, boolean justSelectedItems, int operation) {
    boolean anyCopiedOrMoved = false;
    int toItemCount = to.getWidgetCount();
    int index = 0;
    if (from == to) {
      index = index * 1;
    }
    while (from.getWidgetCount() > index) {
      if (!justSelectedItems || from.isItemSelected(index)) {
        if (from == to) {
          index = index * 1;
        }
        copyOrMoveItem(from, to, index, operation);
        if (operation == OPERATION_COPY) {
          index++;
        }
        anyCopiedOrMoved = true;
      } else {
        index++;
      }
    }
    if (anyCopiedOrMoved) {
      for (int i = 0; i < toItemCount; i++) {
        to.setItemSelected(i, false);
      }
    }
  }

  static private void copyOrMoveItem(MouseListBox from, MouseListBox to, int index, int operation) {
    to.add(from.getClonedWidget(index));
    to.setItemSelected(to.getWidgetCount() - 1, true);
    switch (operation) {
      case OPERATION_COPY:
        break;
      case OPERATION_MOVE:
        from.remove(index);
        break;
      default:
        throw new IllegalArgumentException("" + operation);
    }
  }

  private MouseListBox left;
  private MouseListBox right;

  public DualListBox(int visibleItems, String width) {
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    add(horizontalPanel);
    horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.addStyleName(STYLENAME_DEMO_DUAL_LIST_CENTER);
    verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

    ListBoxDragController dragController = new ListBoxDragController(this);

    left = new MouseListBox(LIST_SIZE);
    right = new MouseListBox(10);
    dragController.makeDraggable(left);
    dragController.makeDraggable(right);

    left.setWidth(width);
    right.setWidth(width);

    horizontalPanel.add(left);
    horizontalPanel.add(verticalPanel);
    horizontalPanel.add(right);

    Button oneRight = new Button("&gt;");
    Button oneLeft = new Button("&lt;");
    Button allRight = new Button("&gt;&gt;");
    Button allLeft = new Button("&lt;&lt;");
    verticalPanel.add(oneRight);
    verticalPanel.add(oneLeft);
    verticalPanel.add(new HTML("&nbsp;"));
    verticalPanel.add(allRight);
    verticalPanel.add(allLeft);

    allRight.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        copyOrmoveItems(left, right, false, OPERATION_MOVE);
      }
    });

    allLeft.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        copyOrmoveItems(right, left, false, OPERATION_MOVE);
      }
    });

    oneRight.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        copyOrmoveItems(left, right, true, OPERATION_MOVE);
      }
    });

    oneLeft.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        copyOrmoveItems(right, left, true, OPERATION_MOVE);
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
}
