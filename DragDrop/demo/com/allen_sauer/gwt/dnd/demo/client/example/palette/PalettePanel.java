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
package com.allen_sauer.gwt.dnd.demo.client.example.palette;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.PickupDragController;

/**
 * Example of a panel that immediately replaces any {@link PaletteWidget} children that removed with
 * widget copies of the original.
 */
public class PalettePanel extends VerticalPanel {

  private final PickupDragController dragController;

  public PalettePanel(PickupDragController dragController) {
    this.dragController = dragController;
    addStyleName("demo-PalettePanel");
    setSpacing(2);
    setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

    Label header = new Label("Widget Palette");
    header.addStyleName("demo-PalettePanel-header");
    add(header);
  }

  /**
   * Overloaded method that makes widgets draggable.
   * 
   * @param w the widget to be added are made draggable
   */
  public void add(PaletteWidget w) {
    dragController.makeDraggable(w);
    super.add(w);
  }

  /**
   * Removed widgets that are instances of {@link PaletteWidget} are immediately replaced with a
   * cloned copy of the original.
   * 
   * @param w the widget to remove
   * @return true if a widget was removed
   */
  @Override
  public boolean remove(Widget w) {
    int index = getWidgetIndex(w);
    if (index != -1 && w instanceof PaletteWidget) {
      PaletteWidget clone = ((PaletteWidget) w).cloneWidget();
      dragController.makeDraggable(clone);
      insert(clone, index);
    }
    return super.remove(w);
  }
}
