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

import com.allen_sauer.gwt.dragdrop.client.util.DOMUtil;

public final class DropTargetInfo {

  private AbsolutePanel boundaryPanel;
  private int dropAreaClientHeight;
  private int dropAreaClientWidth;
  private final AbsolutePanel dropTarget;

  public DropTargetInfo(AbsolutePanel dropTarget) {
    this.dropTarget = dropTarget;
  }

  public final AbsolutePanel getBoundaryPanel() {
    return boundaryPanel;
  }

  public final int getDropAreaClientHeight() {
    if (dropAreaClientHeight == 0) {
      dropAreaClientHeight = DOMUtil.getClientHeight(dropTarget.getElement());
    }
    return dropAreaClientHeight;
  }

  public final int getDropAreaClientWidth() {
    if (dropAreaClientWidth == 0) {
      dropAreaClientWidth = DOMUtil.getClientWidth(dropTarget.getElement());
    }
    return dropAreaClientWidth;
  }

  public final AbsolutePanel getDropTarget() {
    return dropTarget;
  }

  public final void setBoundaryPanel(AbsolutePanel boundaryPanel) {
    this.boundaryPanel = boundaryPanel;
  }
}
