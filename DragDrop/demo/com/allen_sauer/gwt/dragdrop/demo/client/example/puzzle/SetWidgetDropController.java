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
package com.allen_sauer.gwt.dragdrop.demo.client.example.puzzle;

import com.google.gwt.user.client.ui.SimplePanel;

import com.allen_sauer.gwt.dragdrop.client.DragContext;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.drop.SimpleDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.VetoDropException;

/**
 * DropController which allows a widget to be dropped on a SimplePanel drop
 * target when the drop target does not yet have a child widget.
 */
public class SetWidgetDropController extends SimpleDropController {
  private final SimplePanel dropTarget;

  public SetWidgetDropController(SimplePanel dropTarget) {
    super(dropTarget);
    this.dropTarget = dropTarget;
  }

  public DragEndEvent onDrop(DragContext context) {
    DragEndEvent dragEndEvent = super.onDrop(context);
    dropTarget.setWidget(context.draggable);
    return dragEndEvent;
  }

  public void onPreviewDrop(DragContext context) throws VetoDropException {
    super.onPreviewDrop(context);
    if (dropTarget.getWidget() != null) {
      throw new VetoDropException();
    }
  }
}
