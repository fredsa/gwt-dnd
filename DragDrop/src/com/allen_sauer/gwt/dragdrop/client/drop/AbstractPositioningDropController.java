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

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragContext;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.util.DOMUtil;

/**
 * A {@link DropController} which allows a draggable widget to be placed at
 * valid positions (locations) on the drop target, such as
 * {@link com.google.gwt.user.client.ui.AbsolutePanel} or
 * {@link com.google.gwt.user.client.ui.IndexedPanel}. Which positions are
 * valid is determined by the implementing subclass.
 */
public abstract class AbstractPositioningDropController extends AbstractDropController {

  private static final String CSS_DRAGDROP_POSITIONER = "dragdrop-positioner";
  private static final Label DUMMY_LABEL_IE_QUIRKS_MODE_OFFSET_HEIGHT = new Label("x");
  private Widget positioner;

  public AbstractPositioningDropController(Panel dropTarget) {
    super(dropTarget);
  }

  /**
   * Get the widget which is used as an indicator of where the draggable would be 
   * dropped if the mouse button where to be released at the current location.
   * 
   * @return the positioner widget
   */
  public Widget getPositioner() {
    return positioner;
  }

  public DragEndEvent onDrop(DragContext context) {
    DragEndEvent event = super.onDrop(context);
    positioner.removeFromParent();
    return event;
  }

  public void onEnter(DragContext context) {
    super.onEnter(context);
    positioner = newPositioner(context.draggable);
  }

  public void onLeave(DragContext context) {
    super.onLeave(context);
    positioner.removeFromParent();
  }

  /**
   * Called in {@link AbstractPositioningDropController#onEnter(DragContext)}
   * to create a new positioner widget.
   * 
   * @param reference the reference widget whose size or other attributes we can copy
   * @return the newly created widget
   */
  protected Widget newPositioner(Widget reference) {
    // Use two widgets so that setPixelSize() consistently affects dimensions
    // excluding positioner border in quirks and strict modes
    SimplePanel outer = new SimplePanel();
    outer.addStyleName(CSS_DRAGDROP_POSITIONER);

    // place off screen for border calculation
    RootPanel.get().add(outer, -500, -500);

    // Ensure IE quirks mode returns valid outer.offsetHeight, and thus valid
    // DOMUtil.getVerticalBorders(outer)
    outer.setWidget(DUMMY_LABEL_IE_QUIRKS_MODE_OFFSET_HEIGHT);

    SimplePanel inner = new SimplePanel();
    int offsetWidth = reference.getOffsetWidth() - DOMUtil.getHorizontalBorders(outer);
    int offsetHeight = reference.getOffsetHeight() - DOMUtil.getVerticalBorders(outer);
    inner.setPixelSize(offsetWidth, offsetHeight);

    outer.setWidget(inner);

    return outer;
  }
}
