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
package com.allen_sauer.gwt.dragdrop.demo.client.example.duallist;

import com.allen_sauer.gwt.dragdrop.client.DragContext;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.drop.AbstractDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.VetoDropException;

/**
 * DropController for {@link DualListExample}.
 */
public class ListBoxDropController extends AbstractDropController {
  private MouseListBox mouseListBox;

  public ListBoxDropController(MouseListBox mouseListBox) {
    super(mouseListBox);
    this.mouseListBox = mouseListBox;
  }

  public DragEndEvent onDrop(DragContext context) {
    MouseListBox from = ((ListBoxDragController) context.dragController).getCurrentDraggableListBox();
    DualListBox.copyOrmoveItems(from, mouseListBox, true, DualListBox.OPERATION_MOVE);
    return super.onDrop(context);
  }

  public void onPreviewDrop(DragContext context) throws VetoDropException {
    super.onPreviewDrop(context);
    MouseListBox from = ((ListBoxDragController) context.dragController).getCurrentDraggableListBox();
    // TODO avoid this
    if (from == mouseListBox) {
      throw new VetoDropException();
    }
  }
}
