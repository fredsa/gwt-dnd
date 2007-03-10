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
package com.allen_sauer.gwt.dragdrop.client.drop;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.util.UIUtil;

/**
 * A {@link DropController} which allows a draggable widget to be placed at
 * valid positions (locations) on the drop target,
 * e.g. {@link com.google.gwt.user.client.ui.AbsolutePanel} or
 * {@link com.google.gwt.user.client.ui.IndexedPanel}. Which positions are
 * valid is determined by the implementing subclass.
 */
public abstract class AbstractPositioningDropController extends AbstractDropController {

  private Widget positioner;

  public AbstractPositioningDropController(Panel dropTarget) {
    super(dropTarget);
  }

  /**
   * @return the widget which is used as an indicator or where the draggable would be
   *         dropped if the mouse button where to be released at the current location
   */
  public Widget getPositioner() {
    return positioner;
  }

  public void onDrop(Widget reference, Widget draggable, DragController dragController) {
    super.onDrop(reference, draggable, dragController);
    removePositioner();
  }

  public void onEnter(Widget reference, Widget draggable, DragController dragController) {
    super.onEnter(reference, draggable, dragController);
    positioner = newPositioner(reference);
  }

  public void onLeave(Widget draggable, DragController dragController) {
    super.onLeave(draggable, dragController);
    UIUtil.resetStylePositionStatic(draggable.getElement());
    removePositioner();
  }

  /**
   * Called in {@link #onEnter(Widget, Widget, DragController)} to create a new positioner widget
   * 
   * @param reference the reference widget whose size or other attributes we can copy
   * @return the newly created widget
   */
  protected Widget newPositioner(Widget reference) {
    Widget p = new SimplePanel();
    p.addStyleName("dragdrop-positioner");
    // place off screen
    RootPanel.get().add(p, -500, -500);
    p.setPixelSize(reference.getOffsetWidth() - UIUtil.getHorizontalBorders(p), reference.getOffsetHeight()
        - UIUtil.getVerticalBorders(p));
    return p;
  }

  private void removePositioner() {
    if (positioner != null) {
      positioner.removeFromParent();
    }
  }
}
