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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.IndexedDragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.temp.IndexedFlowPanel;
import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.UIUtil;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;

/**
 * A {@link DropController} for instances of {@link IndexedFlowPanel}.
 * 
 * TODO VerticalPanel performance is slow because of positioner DOM manipulation
 */
public class IndexedDropController extends AbstractPositioningDropController {

  private static final String STYLE_DRAGDROP_INDEXED_POSITIONER = "dragdrop-indexed-positioner";
  
  private IndexedPanel dropTarget;
  private int dropIndex;

  public IndexedDropController(IndexedPanel dropTarget) {
    super((Panel) dropTarget);
    this.dropTarget = dropTarget;
  }

  public DragEndEvent onDrop(Widget reference, Widget draggable, DragController dragController) {
    super.onDrop(reference, draggable, dragController);
    UIUtil.resetStylePositionStatic(draggable.getElement());
    int draggableIndex = dropTarget.getWidgetIndex(draggable);
    if (dropIndex == -1) {
      throw new RuntimeException("Should not happen after onPreviewDrop did not veto");
    }
    if ((draggableIndex != -1) && (draggableIndex < dropIndex)) {
      // adjust for removal of widget
      dropIndex--;
    }
    insert(draggable, dropIndex);
    return new IndexedDragEndEvent(draggable, (Panel) dropTarget, dropIndex);
  }

  public void onEnter(Widget reference, Widget draggable, DragController dragController) {
    super.onEnter(reference, draggable, dragController);
    UIUtil.resetStylePositionStatic(getPositioner().getElement());
  }

  public void onLeave(Widget draggable, DragController dragController) {
    super.onLeave(draggable, dragController);
  }

  public void onMove(Widget reference, Widget draggable, DragController dragController) {
    super.onMove(reference, draggable, dragController);
    Location draggableCenter = new WidgetArea(reference, null).getCenter();
    for (int i = 0; i < dropTarget.getWidgetCount(); i++) {
      Widget child = dropTarget.getWidget(i);
      Area childArea = new WidgetArea(child, null);
      if (childArea.intersects(draggableCenter)) {
        int targetIndex = i;
        if (childArea.inBottomRight(draggableCenter)) {
          // place positioner after the intersecting child
          targetIndex++;
        }
        int positionerIndex = dropTarget.getWidgetIndex(getPositioner());
        if (positionerIndex == -1) {
          insert(getPositioner(), targetIndex);
        } else if ((positionerIndex == targetIndex) || (positionerIndex == targetIndex - 1)) {
          // already in the correct location
          return;
        } else if (positionerIndex < targetIndex) {
          // adjust for removal of widget
          insert(getPositioner(), targetIndex - 1);
        } else {
          insert(getPositioner(), targetIndex);
        }
        return;
      }
    }
    // TODO remove after fix for VerticalPanel and HorizontalPanel
    getPositioner().removeFromParent();
    ((Panel) dropTarget).add(getPositioner());
  }

  public void onPreviewDrop(Widget reference, Widget draggable, DragController dragController) throws VetoDropException {
    super.onPreviewDrop(reference, draggable, dragController);
    dropIndex = dropTarget.getWidgetIndex(getPositioner());
    if (dropIndex == -1) {
      throw new VetoDropException();
    }
  }

  protected Widget newPositioner(Widget reference) {
    HTML positioner = new HTML("&#x203B;");
    positioner.addStyleName(STYLE_DRAGDROP_INDEXED_POSITIONER);
    return positioner;
  }

  // TODO remove after enhancement for issue 616
  // http://code.google.com/p/google-web-toolkit/issues/detail?id=616
  private void insert(Widget widget, int beforeIndex) {
    if (dropTarget instanceof DeckPanel) {
      ((DeckPanel) dropTarget).insert(widget, beforeIndex);
    } else if (dropTarget instanceof HorizontalPanel) {
      ((HorizontalPanel) dropTarget).insert(widget, beforeIndex);
    } else if (dropTarget instanceof VerticalPanel) {
      ((VerticalPanel) dropTarget).insert(widget, beforeIndex);
    } else if (dropTarget instanceof IndexedFlowPanel) {
      ((IndexedFlowPanel) dropTarget).insert(widget, beforeIndex);
    } else {
      throw new RuntimeException("Method insert(Widget widget, int beforeIndex) not supported by " + GWT.getTypeName(dropTarget));
    }
  }
}
