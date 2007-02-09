/*
 * Copyright 2006 Fred Sauer
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
package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;

/**
 * A {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DropController} which
 * allows a draggable widget to be placed at valid positions (locations) on the
 * drop target, e.g. {@link com.google.gwt.user.client.ui.AbsolutePanel} or
 * {@link com.google.gwt.user.client.ui.IndexedPanel}. Which positions are
 * valid is determined by the implementing subclass.
 */
public abstract class AbstractPositioningDropController extends AbstractDropController {

  private SimplePanel postioner = new SimplePanel();

  public AbstractPositioningDropController(Panel dropTargetPanel) {
    super(dropTargetPanel);
    this.postioner.addStyleName("dragdrop-positioner");
  }

  public void drop(Widget widget, int left, int top) {
  }

  public Widget getPositionerWidget() {
    return this.postioner;
  }

  public boolean onDrop(Widget draggable, DragController dragController) {
    boolean result = super.onDrop(draggable, dragController);
    removePositioner();
    return result;
  }

  public void onEnter(Widget draggable, DragController dragController) {
    super.onEnter(draggable, dragController);
    Widget positioner = getPositionerWidget();
    // TODO calculate actual borders of positioningBox
    positioner.setPixelSize(draggable.getOffsetWidth() - 2, draggable.getOffsetHeight() - 2);
  }

  public void onLeave(Widget draggable, DragController dragController) {
    super.onLeave(draggable, dragController);
    removePositioner();
  }

  private void removePositioner() {
    Widget positioner = getPositionerWidget();
    if (positioner != null) {
      positioner.removeFromParent();
    }
  }
}
