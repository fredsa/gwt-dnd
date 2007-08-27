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
package com.allen_sauer.gwt.dragdrop.demo.client.example.matryoshka;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.drop.SimpleDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.VetoDropException;

public class MatryoshkaReplaceWidgetDropController extends SimpleDropController {
  private static void makeLastChild(Widget child) {
    Widget parent = child.getParent();
    if (parent instanceof AbsolutePanel) {
      AbsolutePanel p = (AbsolutePanel) child.getParent();
      p.add(child, p.getWidgetLeft(child), p.getWidgetTop(child));
    }
  }

  private final SimplePanel dropTarget;

  public MatryoshkaReplaceWidgetDropController(SimplePanel dropTarget) {
    super(dropTarget);
    this.dropTarget = dropTarget;
  }

  public DragEndEvent onDrop(Widget reference, Widget draggable, DragController dragController) {
    DragEndEvent dragEndEvent = super.onDrop(reference, draggable, dragController);
    makeLastChild(dropTarget);
    dropTarget.setWidget(draggable);
    return dragEndEvent;
  }

  public void onEnter(Widget reference, Widget draggable, DragController dragController) {
    super.onEnter(reference, draggable, dragController); // TODO Replace auto-generated method stub
    if (dropTarget.getWidget() != null) {
      dropTarget.removeStyleName(CSS_DROP_TARGET_ENGAGE);
    }
  }

  public void onPreviewDrop(Widget reference, Widget draggable, DragController dragController) throws VetoDropException {
    super.onPreviewDrop(reference, draggable, dragController);
    if (dropTarget.getWidget() != null) {
      throw new VetoDropException();
    }
  }
}