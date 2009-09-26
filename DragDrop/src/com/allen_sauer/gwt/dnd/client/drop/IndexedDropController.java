/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dnd.client.drop;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.DragClientBundle;
import com.allen_sauer.gwt.dnd.client.util.LocationWidgetComparator;

/**
 * A {@link DropController} for instances of {@link IndexedPanel}.
 * 
 * @see FlowPanelDropController TODO VerticalPanel performance is slow because of positioner DOM
 *      manipulation
 * 
 * @deprecated User {@link HorizontalPanelDropController} or {@link VerticalPanelDropController}
 *             instead.
 */
@Deprecated
public class IndexedDropController extends AbstractIndexedDropController {

  /**
   * Label for IE quirks mode workaround.
   */
  private static final Label DUMMY_LABEL_IE_QUIRKS_MODE_OFFSET_HEIGHT = new Label("x");

  /**
   * The indexed panel drop target.
   */
  private IndexedPanel dropTarget;

  /**
   * Construct an indexed panel drop controller.
   * 
   * @param dropTarget the indexed panel drop target
   */
  public IndexedDropController(IndexedPanel dropTarget) {
    super(dropTarget);
    if (!(dropTarget instanceof HorizontalPanel) && !(dropTarget instanceof VerticalPanel)) {
      throw new IllegalArgumentException(dropTarget.getClass().getName()
          + " is not currently supported by this controller");
    }
    this.dropTarget = dropTarget;
  }

  @Override
  protected LocationWidgetComparator getLocationWidgetComparator() {
    if (dropTarget instanceof HorizontalPanel) {
      return LocationWidgetComparator.RIGHT_HALF_COMPARATOR;
    } else {
      return LocationWidgetComparator.BOTTOM_HALF_COMPARATOR;
    }
  }

  // TODO remove after enhancement for issue 1112 provides InsertPanel interface
  // http://code.google.com/p/google-web-toolkit/issues/detail?id=1112
  @Override
  protected void insert(Widget widget, int beforeIndex) {
    if (dropTarget instanceof HorizontalPanel) {
      ((HorizontalPanel) dropTarget).insert(widget, beforeIndex);
    } else {
      ((VerticalPanel) dropTarget).insert(widget, beforeIndex);
    }
  }

  @Override
  protected Widget newPositioner(DragContext context) {
    // Use two widgets so that setPixelSize() consistently affects dimensions
    // excluding positioner border in quirks and strict modes
    SimplePanel outer = new SimplePanel();
    outer.addStyleName(DragClientBundle.INSTANCE.css().positioner());

    // place off screen for border calculation
    RootPanel.get().add(outer, -500, -500);

    // Ensure IE quirks mode returns valid outer.offsetHeight, and thus valid
    // DOMUtil.getVerticalBorders(outer)
    outer.setWidget(DUMMY_LABEL_IE_QUIRKS_MODE_OFFSET_HEIGHT);

    int width = 0;
    int height = 0;
    if (dropTarget instanceof HorizontalPanel) {
      for (Widget widget : context.selectedWidgets) {
        width += widget.getOffsetWidth();
        height = Math.max(height, widget.getOffsetHeight());
      }
    } else {
      for (Widget widget : context.selectedWidgets) {
        width = Math.max(width, widget.getOffsetWidth());
        height += widget.getOffsetHeight();
      }
    }

    SimplePanel inner = new SimplePanel();
    inner.setPixelSize(width - DOMUtil.getHorizontalBorders(outer), height
        - DOMUtil.getVerticalBorders(outer));

    outer.setWidget(inner);

    return outer;
  }
}
