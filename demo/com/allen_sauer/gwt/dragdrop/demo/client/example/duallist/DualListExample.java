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

import com.allen_sauer.gwt.dragdrop.demo.client.DemoDragHandler;
import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController} example.
 */
public final class DualListExample extends Example {

  private static final String CSS_DEMO_DUAL_LIST_EXAMPLE = "demo-DualListExample";

  public DualListExample(DemoDragHandler demoDragHandler) {
    addStyleName(CSS_DEMO_DUAL_LIST_EXAMPLE);
    DualListBox dualListBox = new DualListBox(10, "10em");
    dualListBox.getDragController().addDragHandler(demoDragHandler);
    dualListBox.addLeft("Apples");
    dualListBox.addLeft("Bananas");
    dualListBox.addLeft("Cucumbers");
    dualListBox.addLeft("Dates");
    dualListBox.addLeft("Enchiladas");
    setWidget(dualListBox);
  }

  public String getDescription() {
    return "Allow drag and drop between two lists.";
  }

  public Class[] getInvolvedClasses() {
    return new Class[] {
        DualListExample.class, ListBoxDragController.class, ListBoxDropController.class, MouseListBox.class, DualListBox.class,
        DualListBoxItem.class,};
  }
}
