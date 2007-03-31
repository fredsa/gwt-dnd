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

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.util.Area;
import com.allen_sauer.gwt.dragdrop.client.util.Location;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetArea;
import com.allen_sauer.gwt.dragdrop.client.util.WidgetLocation;

/**
 * A {@link DropController} for the {@link com.google.gwt.user.client.ui.Panel}
 * which contains a given draggable widget.
 */
public class BoundaryDropController extends AbsolutePositionDropController {

  private boolean allowDropping;
  private WidgetLocation referenceLocation;

  public BoundaryDropController(AbsolutePanel dropTarget, boolean allowDropping) {
    super(dropTarget);
    this.allowDropping = allowDropping;
  }

  public String getDropTargetStyleName() {
    return "dragdrop-boundary";
  }

  protected Location getConstrainedLocation(Widget reference, Widget draggable, Widget widget) {
    if (allowDropping) {
      Area referenceArea = new WidgetArea(reference, getDropTargetInfo().getBoundaryPanel());
      if (referenceLocation == null) {
        referenceLocation = new WidgetLocation(reference, getDropTargetInfo().getDropTarget());
      } else {
        referenceLocation.setWidget(reference);
      }
      referenceLocation.constrain(0, 0, getDropTargetInfo().getDropAreaClientWidth() - referenceArea.getWidth(),
          getDropTargetInfo().getDropAreaClientHeight() - referenceArea.getHeight());
      return referenceLocation;
    } else {
      return null;
    }
  }
}
