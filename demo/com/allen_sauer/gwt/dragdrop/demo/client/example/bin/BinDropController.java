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
package com.allen_sauer.gwt.dragdrop.demo.client.example.bin;

import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragContext;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.drop.SimpleDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.VetoDropException;

import java.util.Iterator;

/**
 * Sample SimpleDropController which discards draggable widgets which are
 * dropped on it.
 */
final class BinDropController extends SimpleDropController {

  private static final String CSS_DEMO_BIN_DRAGGABLE_ENGAGE = "demo-bin-draggable-engage";

  private Bin bin;

  public BinDropController(Bin bin) {
    super(bin);
    this.bin = bin;
  }

  public DragEndEvent onDrop(DragContext context) {
    DragEndEvent event = super.onDrop(context);
    context.movableWidget.removeStyleName(CSS_DEMO_BIN_DRAGGABLE_ENGAGE);
    bin.setEngaged(false);
    for (Iterator iterator = context.selectedWidgets.iterator(); iterator.hasNext();) {
      Widget widget = (Widget) iterator.next();
      bin.eatWidget(widget);
    }
    return event;
  }

  public void onEnter(DragContext context) {
    super.onEnter(context);
    context.movableWidget.addStyleName(CSS_DEMO_BIN_DRAGGABLE_ENGAGE);
    bin.setEngaged(true);
  }

  public void onLeave(DragContext context) {
    super.onLeave(context);
    context.draggable.removeStyleName(CSS_DEMO_BIN_DRAGGABLE_ENGAGE);
    bin.setEngaged(false);
  }

  public void onPreviewDrop(DragContext context) throws VetoDropException {
    super.onPreviewDrop(context);
    if (!bin.isWidgetEater()) {
      throw new VetoDropException();
    }
  }
}
