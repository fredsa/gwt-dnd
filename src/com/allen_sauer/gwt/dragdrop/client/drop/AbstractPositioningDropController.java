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

import com.allen_sauer.gwt.dragdrop.client.DragContext;

/**
 * A {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.DropController} which
 * allows a draggable widget to be placed at valid positions (locations) on the
 * drop target, e.g. {@link com.google.gwt.user.client.ui.AbsolutePanel} or
 * {@link com.google.gwt.user.client.ui.IndexedPanel}. Which positions are
 * valid is determined by the implementing subclass.
 */
public abstract class AbstractPositioningDropController extends AbstractDropController {

  public AbstractPositioningDropController(Panel dropTargetPanel) {
    super(dropTargetPanel);
  }

  public void drop(DragContext dragAndDropController, int left, int top) {
  }

  public boolean onDrop(DragContext dragAndDropController) {
    boolean result = super.onDrop(dragAndDropController);
    dragAndDropController.getPostioningBox().removeFromParent();
    return result;
  }

  public void onEnter(DragContext dragAndDropController) {
    super.onEnter(dragAndDropController);
//    dragAndDropController.getBoundryPanel().add(dragAndDropController.getPostioningBox());
  }

  public void onLeave(DragContext dragAndDropController) {
    super.onLeave(dragAndDropController);
    dragAndDropController.getPostioningBox().removeFromParent();
  }

}
